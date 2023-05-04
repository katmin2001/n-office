package com.fis.crm.service;


import com.fis.crm.service.dto.*;

import java.util.List;

public interface ReviewCampaignTemplateService {
    List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId);

    List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId);

    String saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO);

    OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid);

    List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId);

    List<CampaignDTO> getAllCampaignsForTDV();

    List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId);
}
