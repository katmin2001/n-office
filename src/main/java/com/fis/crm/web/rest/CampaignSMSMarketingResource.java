package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.service.CampaignSMSMarketingService;
import com.fis.crm.service.dto.CampaignSMSMarketingDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/campaign-sms-marketing")
public class    CampaignSMSMarketingResource {

    private static final String ENTITY_NAME = "campaignSMSMarketing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignSMSMarketingService campaignSMSMarketingService;

    public CampaignSMSMarketingResource(CampaignSMSMarketingService campaignSMSMarketingService) {
        this.campaignSMSMarketingService = campaignSMSMarketingService;
    }

    @PostMapping("search")
    public ResponseEntity<List<CampaignSMSMarketingDTO>> onSearch(@RequestParam(value = "name", required = false) String id,
                                                                  @RequestParam(value = "startDate", required = false) String startDate,
                                                                  @RequestParam(value = "endDate", required = false) String endDate,
                                                                  @RequestParam(value = "status", required = false) String status,
                                                                  Pageable pageable) {
        Page<CampaignSMSMarketingDTO> page = campaignSMSMarketingService.search(Long.valueOf(id), startDate, endDate, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("get-all")
    public ResponseEntity<List<CampaignSMSMarketingDTO>> getAll() {
        return ResponseEntity.ok().body(campaignSMSMarketingService.findAll());
    }

    @GetMapping("get-all-search")
    public ResponseEntity<List<CampaignSMSMarketingDTO>> getAllSearch() {
        return ResponseEntity.ok().body(campaignSMSMarketingService.findAllSearch());
    }

    @GetMapping("get-one/{id}")
    public ResponseEntity<CampaignSMSMarketingDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(campaignSMSMarketingService.findOne(id));
    }

    @GetMapping("download-template")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        String path = "../template/target/template_campaign_sms_marketing.xlsx";
        File file = new File(path);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
        Resource resource = new InputStreamResource(byteArrayInputStream);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
        String fileName = "DanhSachDuLieuSMSMarketing - " + format.format(new Date());
        return ResponseEntity.ok().header("filename", fileName).body(resource);
    }

    @PostMapping("save")
    public ResponseEntity<CampaignSMSMarketingDTO> save(@RequestBody CampaignSMSMarketingDTO dto) {
        dto = campaignSMSMarketingService.save(dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        campaignSMSMarketingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("import")
    public ResponseEntity<Long> importFile(@RequestParam(value = "file") MultipartFile file,
                                           @RequestParam(value = "id") Long id,
                                           @RequestParam(value = "duplicateFilter") Long duplicateFilter) {
        String fileName = file.getOriginalFilename();
        int validFile = com.fis.crm.commons.FileUtils.validateAttachFileExcel(file, fileName);
        if (file.getSize() > 5120000) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(123L);
        }
        if (validFile != 0) {
            return ResponseEntity.ok().body(-1L);
        } else {
            Map<String, Long> resultMap = campaignSMSMarketingService.importFile(file, id, duplicateFilter);
            Long success = 0L, error = 0L, total = 0L, blackList = 0L;
            for (Map.Entry<String, Long> entry : resultMap.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("success")) {
                    success = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("error")) {
                    error = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("total")) {
                    total = entry.getValue();
                } else if (entry.getKey().equalsIgnoreCase("blackList")) {
                    blackList = entry.getValue();
                }
            }
            String body = total.toString() + "-" + success.toString() + "-" + error.toString() + "-" + blackList.toString();
            return ResponseEntity.ok().header("filename", body).body(total);
        }

    }

    @GetMapping
    public ResponseEntity<List<CampaignSMSMarketingDTO>> findAllOrderByNameASC() {
        return ResponseEntity.ok().body(campaignSMSMarketingService.findAllOrderByNameASC());
    }
}
