package com.fis.crm.web.rest;

import com.fis.crm.service.NotificationScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationScheduleResource {

    private final NotificationScheduleService notificationScheduleService;
    private Logger log = LoggerFactory.getLogger(NotificationScheduleResource.class);

    public NotificationScheduleResource(NotificationScheduleService notificationScheduleService) {
        this.notificationScheduleService = notificationScheduleService;
    }


    @GetMapping("/api/run-notifications")
    @Scheduled(fixedDelay = 20000, initialDelay = 2000)
    public ResponseEntity<String> runNotification() {
        notificationScheduleService.runNotification();
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
