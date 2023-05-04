package com.fis.crm.web.rest;

import com.fis.crm.service.RecordCallResultService;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.RecordCallResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("api/record-call-result")
public class RecordCallResultResource {
    private final RecordCallResultService recordCallResultService;

    public RecordCallResultResource(RecordCallResultService recordCallResultService) {
        this.recordCallResultService = recordCallResultService;
    }

    @GetMapping("next-phone-number")
    public ResponseEntity<?> getTheNextPhoneNumber(Long campaignId) {
        return ResponseEntity.ok(recordCallResultService.getTheNextPhoneNumber(campaignId));
    }

    @GetMapping("new-phone-number")
    public ResponseEntity<?> getTheNewPhoneNumber(Long campaignResourceDetailId, Long campaignId) {
        return ResponseEntity.ok(recordCallResultService.getTheNewPhoneNumber(campaignResourceDetailId,campaignId));
    }

    @GetMapping("call-information")
    public ResponseEntity<?> getCallInformation(String CID, Long campaignId) {
        return ResponseEntity.ok(recordCallResultService.getCallInformation(CID, campaignId));
    }

    @GetMapping("get-questions-and-answers")
    public ResponseEntity<?> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        return ResponseEntity.ok(recordCallResultService.getQuestionsAndAnswers(campaignId, campaignResourceDetailId));
    }

    @PostMapping("save-result")
    public ResponseEntity<?> saveResult(@RequestBody RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) throws SQLException {
        return ResponseEntity.ok(recordCallResultService.saveResult(recordCallResultDTO, campaignResourceTemplateDTO));
    }

    @PostMapping("add-outer-call")
    public ResponseEntity<?> addOuterCall(Long campaignId, String phoneNumber, String cid) {
        return ResponseEntity.ok(recordCallResultService.addOuterCall(campaignId, phoneNumber, cid));
    }

    @GetMapping("get-info-outer-call")
    public ResponseEntity<?> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId) {
        return ResponseEntity.ok(recordCallResultService.getInformationOfOuterCall(campaignResourceDetailId, campaignId));
    }

    @GetMapping("get-all-campaign-for-tdv")
    public ResponseEntity<?> getAllCampaignsForTDV() {
        return ResponseEntity.ok(recordCallResultService.getAllCampaignsForTDV());
    }

    @GetMapping("get-history")
    public ResponseEntity<?> getRecordCallsHistory(Long campaignResourceDetailId) {
        return ResponseEntity.ok(recordCallResultService.getRecordCallsHistory(campaignResourceDetailId));
    }
}
