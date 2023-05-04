package com.fis.crm.web.rest;

import com.fis.crm.service.ReportProductivityCalloutCampaignService;
import com.fis.crm.service.dto.ReportProductivityCalloutCampaignDTO;
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
@RequestMapping("/api/report-productivity-callout-campaign")
public class ReportProductivityCalloutCampaignResource {

    private final ReportProductivityCalloutCampaignService reportProductivityCalloutCampaignService;

    public ReportProductivityCalloutCampaignResource(ReportProductivityCalloutCampaignService reportProductivityCalloutCampaignService) {
        this.reportProductivityCalloutCampaignService = reportProductivityCalloutCampaignService;
    }

    @PostMapping("search")
    public ResponseEntity<List<ReportProductivityCalloutCampaignDTO>> onSearch(@RequestParam(value = "campaign", required = false) String id,
                                                                               @RequestParam(value = "operator", required = false) String operator,
                                                                               @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                               @RequestParam(value = "toDate", required = false) String toDate,
                                                                               Pageable pageable) {
        Page<ReportProductivityCalloutCampaignDTO> page = reportProductivityCalloutCampaignService.search(id, operator, fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("export")
    public ResponseEntity<Resource> exportCampaignResourceDetail(@RequestParam(value = "campaign", required = false) String id,
                                                                 @RequestParam(value = "operator", required = false) String operator,
                                                                 @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                 @RequestParam(value = "toDate", required = false) String toDate) {
        ByteArrayInputStream byteArrayInputStream = reportProductivityCalloutCampaignService.export(id, operator, fromDate, toDate);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
        String fileName = "Bao_cao_nang_suat_chien_dich_goi_ra_" + format.format(new Date());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
            .header("filename", fileName)
            .body(resource);
    }
}
