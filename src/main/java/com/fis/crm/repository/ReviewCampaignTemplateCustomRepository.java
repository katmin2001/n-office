package com.fis.crm.repository;

import com.fis.crm.domain.Campaign;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.OuterCallDTO;
import com.fis.crm.service.dto.QuestionsAndAnswersDTO;
import com.fis.crm.service.dto.RecordCallResultDTO;

import java.util.List;

public interface ReviewCampaignTemplateCustomRepository {
    List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId);

    List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId);

    String saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO);

    OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid);

    List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId);

    List<Campaign> getAllCampaignsForTDV();

    List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId);
}
