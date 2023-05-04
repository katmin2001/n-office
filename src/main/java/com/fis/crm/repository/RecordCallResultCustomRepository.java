package com.fis.crm.repository;

import com.fis.crm.domain.Campaign;
import com.fis.crm.service.dto.*;

import java.sql.SQLException;
import java.util.List;

public interface RecordCallResultCustomRepository {
    List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId);

    List<CampaignResourceTemplateDTO> getTheNewPhoneNumber(Long campaignResourceDetailId,Long campaignId);

    List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId);

    OuterCallDTO saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) throws SQLException;

    OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid);

    List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId);

    List<Campaign> getAllCampaignsForTDV();

    List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId);

    void buildCallingData(Long userId, Long campaignId,long  campaignResourceId,long  campaignScriptId) ;

	List<List<CampaignResourceTemplateDTO>> getRecordCallsHistory(Long campaignResourceDetailId);

    public ListExcelDynamicGroupDTO exportExcel(long campaignResourceId);
}
