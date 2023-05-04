package com.fis.crm.web.rest;

import com.fis.crm.service.SendAutoEmailAndSMSService;
import com.fis.crm.service.SendEmailAndSMSService;
import com.fis.crm.service.impl.SendAutoEmailAndSMSServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SendMailAndSMSResource {
    private Logger log = LoggerFactory.getLogger(SendMailAndSMSResource.class);
    private final SendEmailAndSMSService sendEmailAndSMSService;
    private final SendAutoEmailAndSMSService sendAutoEmailAndSMSService;

    public SendMailAndSMSResource(SendEmailAndSMSService sendEmailAndSMSService,
                                  SendAutoEmailAndSMSService sendAutoEmailAndSMSService) {
        this.sendEmailAndSMSService = sendEmailAndSMSService;
        this.sendAutoEmailAndSMSService = sendAutoEmailAndSMSService;
    }

    @Scheduled(fixedDelay = 20000, initialDelay = 1000)
    @GetMapping("/sendMail")
    public void sendMail() throws Exception {
        sendEmailAndSMSService.sendMail();
    }

    @Scheduled(fixedDelay = 20000, initialDelay = 5000)
    @GetMapping("/sendSMS")
    public void sendSMS() {
        sendEmailAndSMSService.sendSMS();
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 5000)
    @GetMapping("/autoSend")
    public void autoSend(){
        log.debug("Sending auto email....");
        sendAutoEmailAndSMSService.send();
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 5000)
    @GetMapping("/updateCallInfor")
    public void updateCallInfor(){
        log.debug("Update Call for....");
        sendAutoEmailAndSMSService.updateCallInfor();
    }

    @Scheduled(fixedDelay = 900000, initialDelay = 5000)
    @GetMapping("/sumDashboard")
    public void sumDashboard(){
        log.debug("Sum Dashboard....");
        sendAutoEmailAndSMSService.sumDashboard();
    }

}
