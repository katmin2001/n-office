package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CampaignResourceRepository;
import com.fis.crm.service.*;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignResourceDTO;
import com.fis.crm.service.dto.ComboDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaign-resource")
public class CampaignResourceResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private static final String ENTITY_NAME = "campaignResource";

    private final CampaignResourceService campaignResourceService;
    private final RecordCallResultService recordCallResultService;
    private final UserService userService;
    private final ActionLogService actionLogService;
    private final CampaignService campaignService;
    private final CampaignResourceRepository campaignResourceRepository;

    public CampaignResourceResource(CampaignResourceService campaignResourceService, RecordCallResultService recordCallResultService, UserService userService, ActionLogService actionLogService, CampaignService campaignService, CampaignResourceRepository campaignResourceRepository) {
        this.campaignResourceService = campaignResourceService;
        this.recordCallResultService=recordCallResultService;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.campaignService = campaignService;
        this.campaignResourceRepository = campaignResourceRepository;
    }

    @GetMapping
    public ResponseEntity<List<CampaignResourceDTO>> getAllCampaignResource(@RequestParam(value = "campaign_id") Long campaign_id) {
        log.debug("REST request to get all CampaignResource");
        List<CampaignResourceDTO> campaignResourceDTOS = campaignResourceService
            .findAllByStatusAndCampaignId(campaign_id);
        return ResponseEntity.ok().body(campaignResourceDTOS);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CampaignResourceDTO>> onSearch(@RequestParam(value = "campaignId", required = false) String campaignId,
                                                              @RequestParam(value = "fromDate", required = false) String fromDate,
                                                              @RequestParam(value = "toDate", required = false) String toDate,
                                                              Pageable pageable) {
        Page<CampaignResourceDTO> page = campaignResourceService.search(Long.valueOf(campaignId), fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/get-campaign-combo")
    public ResponseEntity<List<ComboDTO>> getCampaignCombo() {
        log.debug("REST request to get a combo of Campaigns");
        List<ComboDTO> comboDTOList = campaignResourceService.getCampaignCombo();
        return ResponseEntity.ok().body(comboDTOList);
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "id") Long campaignId) {
        log.debug("REST request to upload file Campaign Resource");
        Long total = campaignResourceService.uploadFile(file, campaignId);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Upload: Nguồn dữ liệu chiến dịch [%s]", file.getOriginalFilename()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok().body(total);
    }

    @PostMapping("/import")
    public ResponseEntity<Long> importFile(@RequestParam(value = "file") MultipartFile file,
                                           @RequestParam(value = "campaignId") Long campaignId,
                                           @RequestParam(value = "distinct") Long distinct) {
        log.debug("REST request to import file Campaign Resource");
        Map<String, Long> resultMap = campaignResourceService.importFile(file, campaignId, distinct);
        Long total = 0L, success = 0L, error = 0L, duplicate = 0L, blackList = 0L;
        long campainResourceId=0;
        long campainScriptId=0;
        for (Map.Entry<String, Long> entry : resultMap.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("success")) {
                success = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("error")) {
                error = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("duplicate")) {
                duplicate = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("blackList")) {
                blackList = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("total")) {
                total = entry.getValue();
            }
            else if (entry.getKey().equalsIgnoreCase("campainResourceId")) {
                campainResourceId=entry.getValue();
            }
            else if (entry.getKey().equalsIgnoreCase("campainScriptId")) {
                campainScriptId=entry.getValue();
            }
        }
        if(campainResourceId>0&&campainScriptId>0) {
            recordCallResultService.buildCallingData(-1L, campaignId, campainResourceId, campainScriptId);
        }
        String body = total.toString() + "-" + success.toString() + "-" + error.toString() + "-" + duplicate.toString() + "-" + blackList.toString();
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Import: Nguồn dữ liệu chiến dịch [%s]", file.getOriginalFilename()),
            new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok().header("filename", body).body(success);
    }

    @GetMapping("/download-template/{id}")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable("id") Long id) throws IOException {
        log.debug("REST request to download template file Campaign Resource");
        String path = "../template/target/template.xlsx";
        File file = new File(path);
        ByteArrayInputStream byteArrayInputStream = campaignResourceService.getTemplate(id);
        Resource resource = new InputStreamResource(byteArrayInputStream);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
        String fileName = "MauDuLieuGoiRa_" + id + "_" + format.format(new Date());
        return ResponseEntity.ok().header("filename", fileName).body(resource);
    }

    @PostMapping("/change-status")
    public ResponseEntity<String> changeStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
        log.debug("REST request to change status Campaign Resource");
        campaignResourceService.changeStatus(Long.valueOf(id), status);
        return ResponseEntity.ok().body(status);
    }

    @GetMapping("/export-campaign-resource-detail/{id}")
    public ResponseEntity<Resource> exportCampaignResourceDetail(@PathVariable("id") Long id) {
        try {
            ByteArrayInputStream byteArrayInputStream = campaignResourceService.exportCampaignResourceDetail(id);
            Optional<com.fis.crm.domain.CampaignResource> campaignResourceOptional = campaignResourceRepository.findById(id);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String name = "";
            if (campaignResourceOptional.get().getResourceName().endsWith(".xls")) {
                name = campaignResourceOptional.get().getResourceName().substring(0, campaignResourceOptional.get().getResourceName().length() - 4);
            } else if (campaignResourceOptional.get().getResourceName().endsWith(".xlsx")) {
                name = campaignResourceOptional.get().getResourceName().substring(0, campaignResourceOptional.get().getResourceName().length() - 5);
            }
            String fileName = name + "-" + campaignResourceOptional.get().getCampaignId() + "-" + format.format(new Date());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
            return ResponseEntity.ok()
                .header("filename", fileName)
                .body(resource);
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/export-campaign-resource-error/{id}")
    public ResponseEntity<Resource> exportCampaignResourceError(@PathVariable("id") Long id) {
        try {
            ByteArrayInputStream byteArrayInputStream = campaignResourceService.exportCampaignResourceError(id);
            Optional<com.fis.crm.domain.CampaignResource> campaignResourceOptional = campaignResourceRepository.findById(id);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String name = "";
            if (campaignResourceOptional.get().getResourceName().endsWith(".xls")) {
                name = campaignResourceOptional.get().getResourceName().substring(0, campaignResourceOptional.get().getResourceName().length() - 4);
            } else if (campaignResourceOptional.get().getResourceName().endsWith(".xlsx")) {
                name = campaignResourceOptional.get().getResourceName().substring(0, campaignResourceOptional.get().getResourceName().length() - 5);
            }
            String fileName = name + "-" + Translator.toLocale("campaign-resource-error.title") + "-" + campaignResourceOptional.get().getCampaignId() + "-" + format.format(new Date());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
            return ResponseEntity.ok()
                .header("filename", fileName)
                .body(resource);
        } catch (Exception e) {
            throw e;
        }
    }

}
