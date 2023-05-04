package com.fis.crm.web.rest;

import com.fis.crm.service.ReportRenderDetailOutputCallService;
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
@RequestMapping("/api/report-render-detail-output-calls")
public class ReportRenderDetailOutputCallResource {
    private final ReportRenderDetailOutputCallService reportRenderDetailOutputCallService;

    public ReportRenderDetailOutputCallResource(ReportRenderDetailOutputCallService reportRenderDetailOutputCallService) {
        this.reportRenderDetailOutputCallService = reportRenderDetailOutputCallService;
    }

    @GetMapping
    public ResponseEntity<List<List<CampaignResourceTemplateDTO>>> getDataReportRenderDetailOutputCall(@RequestParam(value = "callTimeFrom", required = false) String callTimeFrom,
                                                                                           @RequestParam(value = "callTimeTo", required = false) String callTimeTo,
                                                                                           @RequestParam(value = "campaignId") Long campaignId,
                                                                                           @RequestParam(value = "statusCall", required = false) String statusCall ,
                                                                                           @RequestParam(value = "assignUserId", required = false) String assignUserId){

        return new ResponseEntity<>(reportRenderDetailOutputCallService.getCallData(callTimeFrom,
            callTimeTo,
            campaignId,
            statusCall,
            assignUserId), HttpStatus.OK);
    }

    @PostMapping("/export-file")
    public ResponseEntity<?> exportExcel(@RequestBody ReportDTO dto) {
        try {
            ByteArrayInputStream byteArrayInputStream =reportRenderDetailOutputCallService.exportExcel(dto);
            Resource resource = new InputStreamResource(byteArrayInputStream);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String fileName = "Bao_cao_chi_tiet_ket_qua_goi_ra_" + format.format(new Date());
            return ResponseEntity.ok().header("filename", fileName).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
