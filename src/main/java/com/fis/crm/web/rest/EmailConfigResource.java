package com.fis.crm.web.rest;

import com.fis.crm.service.EmailConfigService;
import com.fis.crm.service.dto.CampaignBlackListDTO;
import com.fis.crm.service.dto.EmailConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmailConfigResource {
    private final EmailConfigService emailConfigService;
    private final Logger log = LoggerFactory.getLogger(EmailConfigResource.class);

    public EmailConfigResource(EmailConfigService emailConfigService) {
        this.emailConfigService = emailConfigService;
    }

    @GetMapping("/email-config")
    public ResponseEntity<List<EmailConfigDTO>> getAll() {
        log.info("Request to method findByCampaignIdAndPhoneNumber in CampaignBlackListResource");
        List<EmailConfigDTO> all = emailConfigService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/email-config/{id}")
    public ResponseEntity<EmailConfigDTO> getById(@PathVariable Long id) throws Throwable {
        log.info("Request to method findByCampaignIdAndPhoneNumber in CampaignBlackListResource");
        Optional<EmailConfigDTO> emailConfigDTO = emailConfigService.findById(id);
        return new ResponseEntity<>(emailConfigDTO.get(), HttpStatus.OK);
    }

    @PostMapping("/email-config")
    public ResponseEntity<EmailConfigDTO> add(@RequestBody EmailConfigDTO emailConfigDTO) {
        log.info("Request to method add in emailConfig");
        try {
            emailConfigService.save(emailConfigDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/email-config/{id}")
    public ResponseEntity<EmailConfigDTO> update(@PathVariable Long id, @RequestBody EmailConfigDTO emailConfigDTO) {
        log.info("Request to method add in emailConfig");
        try {
            emailConfigService.update(id, emailConfigDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }
}
