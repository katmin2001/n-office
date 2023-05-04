package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignSMSBlackListService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignSMSBlackListDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api/campaign-sms-black-list")
public class CampaignSMSBlackListResource {

    private final Logger log = LoggerFactory.getLogger(CampaignSMSBlackListResource.class);

    final
    CampaignSMSBlackListService campaignSMSBlackListService;

    final
    ExportUtils exportUtils;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public CampaignSMSBlackListResource(CampaignSMSBlackListService campaignSMSBlackListService, ExportUtils exportUtils, UserService userService, ActionLogService actionLogService) {
        this.campaignSMSBlackListService = campaignSMSBlackListService;
        this.exportUtils = exportUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @GetMapping("search")
    public ResponseEntity<List<CampaignSMSBlackListDTO>> findByCampaignEmailIdAndEmailAndFullName
        (@RequestParam(required = false) Long campaignSMSId,
         @RequestParam(required = false) String phoneNumber,
         @RequestParam(required = false) String fullName,
         Pageable pageable) {
        log.info("REST request to findByCampaignEmailIdAndEmailAndFullName in CampaignSMSBlackListResource");
        Page<CampaignSMSBlackListDTO> page = campaignSMSBlackListService
            .findCampaignSMSBlackList(campaignSMSId,
                phoneNumber,
                fullName,
                pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("create")
    ResponseEntity<CampaignSMSBlackListDTO> create(@RequestBody CampaignSMSBlackListDTO campaignSMSBlackListDTO) {
        log.info("REST request to create in CampaignSMSBlackListResource");
        if (campaignSMSBlackListService.isExistPhoneNumber(campaignSMSBlackListDTO.getCampaignSMSId(), campaignSMSBlackListDTO.getPhoneNumber())) {
            throw new BadRequestAlertException("Số điện thoại này đã tồn tại", ENTITY_NAME, "idexists");
        }
        CampaignSMSBlackListDTO campaignSMSBlackListDTO1 = campaignSMSBlackListService.save(campaignSMSBlackListDTO);
        return ResponseEntity.ok().body(campaignSMSBlackListDTO1);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<CampaignSMSBlackListDTO> delete(@PathVariable("id") Long id) {
        log.info("REST request to delete in CampaignSMSBlackListResource");
        CampaignSMSBlackListDTO campaignSMSBlackListDTO = campaignSMSBlackListService.delete(id);
        return ResponseEntity.ok().body(campaignSMSBlackListDTO);
    }

    @PostMapping("/getSampleFile")
    public ResponseEntity<InputStreamResource> getSampleFile() {
        log.debug("REST request to down file sample CampaignSMSBlackListResource : {}");
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            Resource resource = new ClassPathResource("campaign_sms_black_list_template.xlsx");
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
                                        @RequestParam("campaignId") Long campaignSmsId) {
        log.debug("REST request to upload file sample CampaignSMSBlackListResource : {}");
        try {
            if (file.getSize() > 5120000) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(123L);
            }
            CampaignSMSBlackListDTO result = campaignSMSBlackListService.importFile(file, campaignSmsId);
//            if (result.getErrorCodeConfig().size()>0) {
//                return ResponseEntity.badRequest().body(result);
//            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> export(@RequestBody CampaignSMSBlackListDTO campaignSMSBlackListDTO,Pageable pageable) throws Exception {
        List<CampaignSMSBlackListDTO> lstDatas = campaignSMSBlackListService
            .findCampaignSMSBlackList(campaignSMSBlackListDTO.getCampaignSMSId(),
                campaignSMSBlackListDTO.getPhoneNumber(),
                campaignSMSBlackListDTO.getFullName(), pageable).getContent();

        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("campaignSMSName", "Tên chiến dịchị", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", "Số điện thoại", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("fullName", "Họ và tên", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createUserName", "Người tạo", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDateTime", "Ngày tạo", ExcelColumn.ALIGN_MENT.LEFT));

        String title = "Danh sách đen gửi sms";
        String fileName = Translator.toLocale("exportFileExcel.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, "Ngày xuất", DateUtil.dateToString(new Date(), Constants.DATE_FORMAT_DDMMYYY));

        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, String.format("Xuất excel: Danh sách đen gửi SMS"),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.campaign_sms_black_list, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

}
