package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignEmailBatchService;
import com.fis.crm.service.dto.CampaignEmailBatchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampaignEmailBatchResource {
    private final CampaignEmailBatchService campaignEmailBatchService;
    private final Logger log = LoggerFactory.getLogger(CampaignEmailBatchResource.class);

    public CampaignEmailBatchResource(CampaignEmailBatchService campaignEmailBatchService) {
        this.campaignEmailBatchService = campaignEmailBatchService;
    }

    @PostMapping("/campaign-email-batch")
    public ResponseEntity<?> save(@RequestBody List<CampaignEmailBatchDTO> campaignEmailBatchDTOS) {
        log.info("Request to method save in CampaignBlacklist");
        try {
            campaignEmailBatchService.saveCampaignEmailBatch(campaignEmailBatchDTOS);
            campaignEmailBatchService.update(campaignEmailBatchDTOS);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/campaign-email-batch/one")
    public ResponseEntity<?> saveEmailOne(@RequestBody CampaignEmailBatchDTO campaignEmailBatchDTO) {
        log.info("Request to method save in CampaignBlacklist");
        campaignEmailBatchService.saveEmailOne(campaignEmailBatchDTO);
        return ResponseEntity.ok().body(null);
    }
}
