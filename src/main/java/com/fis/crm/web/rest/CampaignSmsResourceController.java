package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignSmsResourceService;
import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import com.fis.crm.service.dto.CampaignSMSResourceDTO;
import com.fis.crm.service.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampaignSmsResourceController {
    private final CampaignSmsResourceService campaignSmsResourceService;
    private final Logger log = LoggerFactory.getLogger(CampaignSmsResourceController.class);

    public CampaignSmsResourceController(CampaignSmsResourceService campaignSmsResourceService) {
        this.campaignSmsResourceService = campaignSmsResourceService;
    }


    @PostMapping("/campaign-sms-resource")
    public ResponseEntity<List<CampaignSMSResourceDTO>> findByCampaignIdAndPhoneNumber(@RequestParam(value = "campaignSmsMarketingId", required = false) Long campaignSmsMarketingId,
                                                                                       @RequestParam(value = "status", required = false) String status,
                                                                                       @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                                       @RequestParam(value = "toDate", required = false) String toDate,
                                                                                       Pageable pageable) {
        log.info("Request to method getall campaign-sms-resource");
        Page<CampaignSMSResourceDTO> page = campaignSmsResourceService.search(campaignSmsMarketingId, status, fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PutMapping("/campaign-sms-resource/{id}")
    public ResponseEntity<ResponseDTO> changeStatus(@PathVariable("id") Long id) {
        try {
            campaignSmsResourceService.changeStatus(id);
            return ResponseEntity.ok().body(new ResponseDTO(200L, "Xóa thành công"));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseDTO(400L, e.getMessage()));
        }
    }
}
