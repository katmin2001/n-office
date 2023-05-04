package com.fis.crm.web.rest;

import com.fis.crm.service.ReviewCampaignTemplateService;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.RecordCallResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review-campaign-template")
public class ReviewCampaignTemplateResource {
    final
    ReviewCampaignTemplateService reviewCampaignTemplateService;

    public ReviewCampaignTemplateResource(ReviewCampaignTemplateService reviewCampaignTemplateService) {
        this.reviewCampaignTemplateService = reviewCampaignTemplateService;
    }

    @GetMapping("next-phone-number")
    public ResponseEntity<?> getTheNextPhoneNumber(Long campaignId) {
        return ResponseEntity.ok(reviewCampaignTemplateService.getTheNextPhoneNumber(campaignId));
    }

    @GetMapping("call-information")
    public ResponseEntity<?> getCallInformation(String CID, Long campaignId) {
        return ResponseEntity.ok(reviewCampaignTemplateService.getCallInformation(CID, campaignId));
    }

    @GetMapping("get-questions-and-answers")
    public ResponseEntity<?> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        return ResponseEntity.ok(reviewCampaignTemplateService.getQuestionsAndAnswers(campaignId, campaignResourceDetailId));
    }

    @PostMapping("save-result")
    public ResponseEntity<?> saveResult(@RequestBody RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) {
        return ResponseEntity.ok(reviewCampaignTemplateService.saveResult(recordCallResultDTO, campaignResourceTemplateDTO));
    }

    @PostMapping("add-outer-call")
    public ResponseEntity<?> addOuterCall(Long campaignId, String phoneNumber, String cid) {
        return ResponseEntity.ok(reviewCampaignTemplateService.addOuterCall(campaignId, phoneNumber, cid));
    }

    @GetMapping("get-info-outer-call")
    public ResponseEntity<?> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId) {
        return ResponseEntity.ok(reviewCampaignTemplateService.getInformationOfOuterCall(campaignResourceDetailId, campaignId));
    }

    @GetMapping("get-all-campaign-for-tdv")
    public ResponseEntity<?> getAllCampaignsForTDV() {
        return ResponseEntity.ok(reviewCampaignTemplateService.getAllCampaignsForTDV());
    }
}
