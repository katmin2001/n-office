package com.fis.crm.web.rest;

import com.fis.crm.service.ReportRenderCallService;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ReportDTO;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/report-render-calls")
public class ReportRenderCallResource {

    private final ReportRenderCallService reportRenderCallService;

    public ReportRenderCallResource(ReportRenderCallService reportRenderCallService) {
        this.reportRenderCallService = reportRenderCallService;
    }

    @GetMapping
    public ResponseEntity<List<List<CampaignResourceTemplateDTO>>> getDataReportRenderCall(@RequestParam(value = "callTimeFrom", required = false) String callTimeFrom,
                                                                                           @RequestParam(value = "callTimeTo", required = false) String callTimeTo,
                                                                                           @RequestParam(value = "campaignId") Long campaignId,
                                                                                           @RequestParam(value = "saleStatus", required = false) String saleStatus ,
                                                                                           @RequestParam(value = "assignUserId", required = false) String assignUser){

        return new ResponseEntity<>(reportRenderCallService.getCallData(callTimeFrom,
            callTimeTo,
            campaignId,
            saleStatus,
            assignUser), HttpStatus.OK);
    }

    @PostMapping("/export-file")
    public ResponseEntity<?> exportExcel(@RequestBody ReportDTO dto) {
        try {
            ByteArrayInputStream byteArrayInputStream =reportRenderCallService.exportExcel(dto);
            Resource resource = new InputStreamResource(byteArrayInputStream);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String fileName = "Bao_cao_cuoc_goi_" + format.format(new Date());
            return ResponseEntity.ok().header("filename", fileName).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
