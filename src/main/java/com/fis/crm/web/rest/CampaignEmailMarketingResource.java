package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.service.CampaignEmailMarketingService;
import com.fis.crm.service.dto.CampaignEmailMarketingDTO;
import com.fis.crm.web.rest.errors.BusinessException;
import com.fis.crm.web.rest.response.ResponseFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/campaign-email-marketing")
public class CampaignEmailMarketingResource {

    private static final String ENTITY_NAME = "campaignEmailMarketing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignEmailMarketingService campaignEmailMarketingService;

    private final ResponseFactory responseFactory;

    public CampaignEmailMarketingResource(CampaignEmailMarketingService campaignEmailMarketingService, ResponseFactory responseFactory) {
        this.campaignEmailMarketingService = campaignEmailMarketingService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("search")
    public ResponseEntity<List<CampaignEmailMarketingDTO>> onSearch(@RequestParam(value = "name", required = false) String id,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate,
                                                                    @RequestParam(value = "status", required = false) String status,
                                                                    Pageable pageable) {
        Page<CampaignEmailMarketingDTO> page = campaignEmailMarketingService.search(Long.valueOf(id), fromDate, toDate, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("get-all")
    public ResponseEntity<List<CampaignEmailMarketingDTO>> getAll() {
        return ResponseEntity.ok().body(campaignEmailMarketingService.findAll());
    }

    @GetMapping("get-all-search")
    public ResponseEntity<List<CampaignEmailMarketingDTO>> getAllSearch() {
        return ResponseEntity.ok().body(campaignEmailMarketingService.findAllSearch());
    }

    @GetMapping
    public ResponseEntity<List<CampaignEmailMarketingDTO>> findAllOrderByNameASC() {
        return ResponseEntity.ok().body(campaignEmailMarketingService.findAllOrderByNameASC());
    }

    @GetMapping("get-one/{id}")
    public ResponseEntity<CampaignEmailMarketingDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(campaignEmailMarketingService.findOne(id));
    }

    @GetMapping("download-template")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        String path = "../template/target/template_campaign_email_marketing.xlsx";
        File file = new File(path);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
        Resource resource = new InputStreamResource(byteArrayInputStream);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
        String fileName = "MauImportChienDichEmailMarketing_" + format.format(new Date());
        return ResponseEntity.ok().header("filename", fileName).body(resource);
    }

    @PostMapping("save")
    public ResponseEntity<CampaignEmailMarketingDTO> save(@RequestBody CampaignEmailMarketingDTO dto) {
        dto = campaignEmailMarketingService.save(dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        campaignEmailMarketingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("import")
    public ResponseEntity<Object> importFile(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "id") Long id,
                                             @RequestParam(value = "duplicateFilter") Long duplicateFilter) {
        try {
            Map<String, Long> resultMap = campaignEmailMarketingService.importFile(file, id, duplicateFilter);
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
            return responseFactory.success(body, String.class);
        } catch (Exception e) {
            throw e;
        }
    }
}
