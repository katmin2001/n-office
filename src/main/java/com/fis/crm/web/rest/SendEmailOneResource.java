package com.fis.crm.web.rest;

import com.fis.crm.service.SendEmailOneService;
import com.fis.crm.service.dto.SendEmailOneDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SendEmailOneResource {

    private final SendEmailOneService sendEmailOneService;
    private final Logger log = LoggerFactory.getLogger(SendEmailOneResource.class);

    public SendEmailOneResource(SendEmailOneService sendEmailOneService) {
        this.sendEmailOneService = sendEmailOneService;
    }

    @PostMapping("/send-email-one")
    public ResponseEntity<SendEmailOneDTO> save(@RequestBody SendEmailOneDTO sendEmailOneDTO) {
        log.info("Request to method add in SendEmailOneDTO");
        sendEmailOneService.save(sendEmailOneDTO);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/send-email-one")
    public ResponseEntity<List<SendEmailOneDTO>> findAll(){
        List<SendEmailOneDTO> sendEmailOneDTOS = sendEmailOneService.findAll();
        return ResponseEntity.ok().body(sendEmailOneDTOS);
    }
}
