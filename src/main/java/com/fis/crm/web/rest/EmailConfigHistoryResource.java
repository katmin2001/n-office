package com.fis.crm.web.rest;

import com.fis.crm.service.EmailConfigHistoryService;
import com.fis.crm.service.dto.EmailConfigHistoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailConfigHistoryResource {
    private final EmailConfigHistoryService emailConfigHistoryService;
    private final Logger log = LoggerFactory.getLogger(EmailConfigHistoryResource.class);

    public EmailConfigHistoryResource(EmailConfigHistoryService emailConfigHistoryService) {
        this.emailConfigHistoryService = emailConfigHistoryService;
    }

    @GetMapping("/email-config-history")
    public ResponseEntity<List<EmailConfigHistoryDTO>> findAll() {
        List<EmailConfigHistoryDTO> emailConfigHistoryDTOS = emailConfigHistoryService.findAll();
        return ResponseEntity.ok().body(emailConfigHistoryDTOS);
    }
}
