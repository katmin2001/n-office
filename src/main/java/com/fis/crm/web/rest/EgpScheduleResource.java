package com.fis.crm.web.rest;

import com.fis.crm.service.EgpScheduleService;
import com.fis.crm.service.egp.EGPResponse;
import com.fis.crm.service.egp.request.ContractorSuspended;
import com.fis.crm.service.egp.request.FeePendingDate;
import com.fis.crm.service.egp.request.ReportContractorSuspended;
import com.fis.crm.service.egp.response.PageContractor;
import com.fis.crm.service.egp.response.ReportContractorSuspendedContent;
import com.fis.crm.service.egp.response.ReportContractorSuspendedData;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
public class EgpScheduleResource {

    private final EgpScheduleService egpScheduleService;

    public EgpScheduleResource(EgpScheduleService egpScheduleService) {
        this.egpScheduleService = egpScheduleService;
    }

    @GetMapping("/api/get-report-contractor-suspended")
    @Scheduled(fixedDelay = 3600000)
    public void getReportContractorSuspended() throws Exception {
        egpScheduleService.getReportContractorSuspended();
    }

    @GetMapping("/api/get-pm-contractor-status")
    @Scheduled(fixedDelay = 3600000)
    public void getPmContractorStatus() throws Exception {
        egpScheduleService.getPmContractorStatus();
    }

    @GetMapping("/api/get-request-register")
    @Scheduled(fixedDelay = 3600000)
    public void getRequestRegister() throws Exception {
        egpScheduleService.getRequestRegister();
    }
}
