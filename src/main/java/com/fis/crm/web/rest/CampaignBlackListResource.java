package com.fis.crm.web.rest;

import com.fis.crm.domain.CampaignBlacklist;
import com.fis.crm.service.CampaignBlackListService;
import com.fis.crm.service.dto.CampaignBlackListDTO;
import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampaignBlackListResource {
    private final CampaignBlackListService campaignBlackListService;
    private final Logger log = LoggerFactory.getLogger(CampaignBlackListResource.class);

    public CampaignBlackListResource(CampaignBlackListService campaignBlackListService) {
        this.campaignBlackListService = campaignBlackListService;
    }

    @GetMapping("/campaign-blacklist/getAll")
    public ResponseEntity<List<CampaignBlackListDTO>> getAll() {
        log.info("Request to method findByCampaignIdAndPhoneNumber in CampaignBlackListResource");
        List<CampaignBlackListDTO> all = campaignBlackListService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/campaign-blacklist")
    public ResponseEntity<List<CampaignBlackListDTO>> findByCampaignIdAndPhoneNumber(Long campaignId, String phoneNumber, Pageable pageable) {
        log.info("Request to method findByCampaignIdAndPhoneNumber in CampaignBlackListResource");
        Page<CampaignBlackListDTO> page = campaignBlackListService.findCampaignBlackList(campaignId, phoneNumber, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/campaign-blacklist")
    public ResponseEntity<?> add(@RequestBody CampaignBlackListDTO campaignBlackListDTO) {
        log.info("Request to method add in CampaignBlacklist");
        try {
            campaignBlackListService.save(campaignBlackListDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
       }
        return ResponseEntity.ok().body(null);
    }


    @PutMapping("/campaign-blacklist")
    public ResponseEntity<CampaignBlacklist> deleteAll(@RequestBody List<CampaignBlackListDTO> campaignBlackListDTO) {
        log.info("Request to method delete in CampaignBlacklist");
        campaignBlackListService.deleteAll(campaignBlackListDTO);
        return ResponseEntity.ok().body(null);
    }
}
