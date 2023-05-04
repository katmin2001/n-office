package com.fis.crm.web.rest;

import com.fis.crm.service.ReportSentEmailMarketingService;
import com.fis.crm.service.dto.ReportSentEmailMarketingDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/report-sent-email-marketing")
public class ReportSentEmailMarketingResource {

    private final ReportSentEmailMarketingService reportSentEmailMarketingService;

    public ReportSentEmailMarketingResource(ReportSentEmailMarketingService reportSentEmailMarketingService) {
        this.reportSentEmailMarketingService = reportSentEmailMarketingService;
    }

    @PostMapping("search")
    public ResponseEntity<List<ReportSentEmailMarketingDTO>> onSearch(@RequestParam(value = "campaign", required = false) String id,
                                                                      @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                      @RequestParam(value = "toDate", required = false) String toDate,
                                                                      @RequestParam(value = "email", required = false) String email,
                                                                      @RequestParam(value = "status", required = false) String status,
                                                                      Pageable pageable) {
        Page<ReportSentEmailMarketingDTO> page = reportSentEmailMarketingService.search(id, fromDate, toDate, email, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("export")
    public ResponseEntity<Resource> exportCampaignResourceDetail(@RequestParam(value = "campaign", required = false) String id,
                                                                 @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                 @RequestParam(value = "toDate", required = false) String toDate,
                                                                 @RequestParam(value = "email", required = false) String email,
                                                                 @RequestParam(value = "status", required = false) String status) {
        ByteArrayInputStream byteArrayInputStream = reportSentEmailMarketingService.export(id, fromDate, toDate, email, status);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
        String fileName = "Bao_cao_chi_tiet_gui_Email_marketing_" + format.format(new Date());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
            .header("filename", fileName)
            .body(resource);
    }
}
