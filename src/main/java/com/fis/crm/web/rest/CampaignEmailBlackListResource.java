package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignEmailBlackListService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignEmailBlackListDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.BusinessException;
import com.fis.crm.web.rest.errors.ErrorCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/campaign-email-blacklist")
public class CampaignEmailBlackListResource {

    final
    ExportUtils exportUtils;
    final
    CampaignEmailBlackListService campaignEmailBlackListService;
    private final ActionLogService actionLogService;
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(CampaignEmailBlackListResource.class);

    public CampaignEmailBlackListResource(CampaignEmailBlackListService campaignEmailBlackListService, ExportUtils exportUtils, ActionLogService actionLogService, UserService userService) {
        this.campaignEmailBlackListService = campaignEmailBlackListService;
        this.exportUtils = exportUtils;
        this.actionLogService = actionLogService;
        this.userService = userService;
    }

    @GetMapping("search")
    public ResponseEntity<List<CampaignEmailBlackListDTO>> findByCampaignEmailIdAndEmailAndFullName
        (@RequestParam(required = false) Long campaignEmailId,
         @RequestParam(required = false) String email,
         @RequestParam(required = false) String fullName,
         Pageable pageable) {
        log.info("REST request to findByCampaignEmailIdAndEmailAndFullName in CampaignEmailBlackListResource");
        Page<CampaignEmailBlackListDTO> page = campaignEmailBlackListService
            .findCampaignEmailBlackList(campaignEmailId,
                email,
                fullName,
                pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("create")
    ResponseEntity<CampaignEmailBlackListDTO> create(@RequestBody CampaignEmailBlackListDTO campaignEmailBlackListDTO) {
        log.info("REST request to create in CampaignEmailBlackListResource");
        if (campaignEmailBlackListService.isExistEmail(campaignEmailBlackListDTO.getCampaignEmailId(),campaignEmailBlackListDTO.getEmail())) {
            throw new BadRequestAlertException("Email này đã tồn tại", ENTITY_NAME, "idexists");
        }
        CampaignEmailBlackListDTO campaignEmailBlackListDTO1 = campaignEmailBlackListService.save(campaignEmailBlackListDTO);
        return ResponseEntity.ok().body(campaignEmailBlackListDTO1);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<CampaignEmailBlackListDTO> delete(@PathVariable("id") Long id) {
        log.info("REST request to delete in CampaignEmailBlackListResource");
        CampaignEmailBlackListDTO campaignEmailBlackListDTO1 = campaignEmailBlackListService.delete(id);
        return ResponseEntity.ok().body(campaignEmailBlackListDTO1);
    }

    @PostMapping("/getSampleFile")
    public ResponseEntity<InputStreamResource> getSampleFile() {
        log.debug("REST request to down file sample CampaignEmailBlackListResource : {}");
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            Resource resource = new ClassPathResource("campaign_email_black_list_template.xlsx");
            InputStream inputStream = resource.getInputStream();
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);

        } catch (Exception e) {
            log.error("", e.getMessage());
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("campaignId") Long campaignId) {
        log.debug("REST request to upload file sample CampaignEmailBlackListResource : {}");
        try {
            if (file.getSize() > 5120000) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(Translator.toLocale("error.file.size"));
            }
            CampaignEmailBlackListDTO result = campaignEmailBlackListService.importFile(file, campaignId);
//            if (!StringUtils.isEmpty(result.getErrorCodeConfig())) {
//                return ResponseEntity.badRequest().body(result);
//            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> exportCampaignEmailBlackList(@RequestBody CampaignEmailBlackListDTO campaignEmailBlackListDTO,Pageable pageable) throws Exception {
        List<CampaignEmailBlackListDTO> lstDatas = campaignEmailBlackListService
            .findCampaignEmailBlackList(campaignEmailBlackListDTO.getCampaignEmailId(),
                campaignEmailBlackListDTO.getEmail(),
                campaignEmailBlackListDTO.getFullName(),pageable).getContent();

        List<ExcelColumn> lstColumn = new ArrayList<>();
//        lstColumn.add(new ExcelColumn("campaignEmailId", "ID email tiếp thị", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("email", "Email", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("fullName", "Họ và tên", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createUserName", "Người tạo", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDateTime", "Ngày tạo", ExcelColumn.ALIGN_MENT.LEFT));

        String title = "Danh sách đen gửi email";
        String fileName = Translator.toLocale("exportFileExcel.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, "Ngày xuất: ", DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Danh sách đen gửi email"),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.campaign_email_blacklist, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

}
