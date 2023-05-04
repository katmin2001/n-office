package com.fis.crm.service.impl;

import com.fis.crm.repository.ReviewCampaignTemplateCustomRepository;
import com.fis.crm.service.ReviewCampaignTemplateService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewCampaignTemplateServiceImpl implements ReviewCampaignTemplateService {
    final
    ReviewCampaignTemplateCustomRepository reviewCampaignTemplateCustomRepository;
    private final CampaignMapper campaignMapper;

    public ReviewCampaignTemplateServiceImpl(CampaignMapper campaignMapper, ReviewCampaignTemplateCustomRepository reviewCampaignTemplateCustomRepository) {
        this.campaignMapper = campaignMapper;
        this.reviewCampaignTemplateCustomRepository = reviewCampaignTemplateCustomRepository;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId) {
        return reviewCampaignTemplateCustomRepository.getTheNextPhoneNumber(campaignId);
    }

    @Override
    public List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        return reviewCampaignTemplateCustomRepository.getQuestionsAndAnswers(campaignId, campaignResourceDetailId);
    }

    @Override
    public String saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) {
        return reviewCampaignTemplateCustomRepository.saveResult(recordCallResultDTO, campaignResourceTemplateDTO);
    }

    @Override
    public OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid) {
        return reviewCampaignTemplateCustomRepository.addOuterCall(campaignId, phoneNumber, cid);
    }

    @Override
    public List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId) {
        return reviewCampaignTemplateCustomRepository.getInformationOfOuterCall(campaignResourceDetailId, campaignId);
    }

    @Override
    public List<CampaignDTO> getAllCampaignsForTDV() {
        return campaignMapper.toDto(reviewCampaignTemplateCustomRepository.getAllCampaignsForTDV()
            .stream().collect(Collectors.toList()));
    }

    @Override
    public List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId) {
        return reviewCampaignTemplateCustomRepository.getCallInformation(CID, campaignId);
    }
}
