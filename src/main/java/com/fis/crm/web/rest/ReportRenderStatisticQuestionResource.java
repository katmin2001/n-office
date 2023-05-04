package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignScriptQuestionService;
import com.fis.crm.service.ReportRenderStatisticQuestionService;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
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
@RequestMapping("/api/report-render-statistic-question")
public class ReportRenderStatisticQuestionResource {

    private final ReportRenderStatisticQuestionService reportRenderStatisticQuestionService;
    private final CampaignScriptQuestionService campaignScriptQuestionService;

    public ReportRenderStatisticQuestionResource(ReportRenderStatisticQuestionService reportRenderStatisticQuestionService,
                                                 CampaignScriptQuestionService campaignScriptQuestionService) {
        this.reportRenderStatisticQuestionService = reportRenderStatisticQuestionService;
        this.campaignScriptQuestionService = campaignScriptQuestionService;
    }

    @GetMapping
    public ResponseEntity<List<ReportDTO>> getDataReportRenderStatisticQuestion(@RequestParam(value = "callTimeFrom", required = false) String callTimeFrom,
                                                                                           @RequestParam(value = "callTimeTo", required = false) String callTimeTo,
                                                                                           @RequestParam(value = "campaignId") Long campaignId,
                                                                                           @RequestParam(value = "questionId", required = false) Long questionId){

        return new ResponseEntity<>(reportRenderStatisticQuestionService.getData(callTimeFrom,
            callTimeTo,
            campaignId,
            questionId), HttpStatus.OK);
    }

    @PostMapping("/export-file")
    public ResponseEntity<?> exportExcel(@RequestBody ReportDTO dto) {
        try {
            ByteArrayInputStream byteArrayInputStream =reportRenderStatisticQuestionService.exportExcel(dto);
            Resource resource = new InputStreamResource(byteArrayInputStream);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String fileName = "Bao_cao_thong_ke_cau_hoi_" + format.format(new Date());
            return ResponseEntity.ok().header("filename", fileName).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<CampaignScriptQuestionResponseDTO>> getAllQuestionByCampaignId(@RequestParam(value = "campaignId") Long campaignId){
        return new ResponseEntity<>(campaignScriptQuestionService.getAllQuestionByCampaignId(campaignId), HttpStatus.OK);
    }
}
