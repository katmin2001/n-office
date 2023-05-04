package com.fis.crm.service.impl;

import com.fis.crm.repository.RecordCallResultCustomRepository;
import com.fis.crm.service.RecordCallResultService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignMapper;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordCallResultServiceImpl implements RecordCallResultService {
    private final RecordCallResultCustomRepository recordCallResultCustomRepository;
    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;
    private final CampaignMapper campaignMapper;

    public RecordCallResultServiceImpl(RecordCallResultCustomRepository recordCallResultCustomRepository, CampaignResourceTemplateMapper campaignResourceTemplateMapper, CampaignMapper campaignMapper) {
        this.recordCallResultCustomRepository = recordCallResultCustomRepository;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
        this.campaignMapper = campaignMapper;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId) {
        return recordCallResultCustomRepository.getTheNextPhoneNumber(campaignId);
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNewPhoneNumber(Long campaignResourceDetailId,Long campaignId)
    {
        return recordCallResultCustomRepository.getTheNewPhoneNumber(campaignResourceDetailId,campaignId);
    }

    @Override
    public List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        return recordCallResultCustomRepository.getQuestionsAndAnswers(campaignId, campaignResourceDetailId);
    }

    @Override
    public OuterCallDTO saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) throws SQLException {
        return recordCallResultCustomRepository.saveResult(recordCallResultDTO, campaignResourceTemplateDTO);
    }

    @Override
    public OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid) {
        return recordCallResultCustomRepository.addOuterCall(campaignId, phoneNumber, cid);
    }

    @Override
    public List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId) {
        return recordCallResultCustomRepository.getInformationOfOuterCall(campaignResourceDetailId, campaignId);
    }

    @Override
    public List<CampaignDTO> getAllCampaignsForTDV() {
        return campaignMapper.toDto(recordCallResultCustomRepository.getAllCampaignsForTDV()
            .stream().collect(Collectors.toList()));
    }

    @Override
    public List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId) {
        return recordCallResultCustomRepository.getCallInformation(CID, campaignId);
    }
    @Override
    public void buildCallingData(Long userId, Long campaignId,long  campaignResourceId,long  campaignScriptId)
    {
        recordCallResultCustomRepository.buildCallingData(userId,campaignId,campaignResourceId,campaignScriptId);
    }

    @Override
    public List<List<CampaignResourceTemplateDTO>> getRecordCallsHistory(Long campaignResourceDetailId) {
        return recordCallResultCustomRepository.getRecordCallsHistory(campaignResourceDetailId);
    }
}
