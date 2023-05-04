package com.fis.crm.service;

import com.fis.crm.web.rest.errors.CodeExisted;
import com.fis.crm.service.dto.CampaignScriptQuestionRequestDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import com.fis.crm.web.rest.errors.WrongFormat;
import io.undertow.util.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampaignScriptQuestionService {
    CampaignScriptQuestionResponseDTO save(CampaignScriptQuestionRequestDTO dto) throws BadRequestException, CodeExisted, WrongFormat;

    List<CampaignScriptQuestionResponseDTO> findAllByCampaignScriptId(Long campaignScriptId);

    Page<CampaignScriptQuestionResponseDTO> findAllByCampaignScriptIdPageable(Long campaignScriptId, Pageable pageable);

    Boolean checkExistPosition(CampaignScriptQuestionRequestDTO dto) throws BadRequestException;

    CampaignScriptQuestionResponseDTO updateQuestion(CampaignScriptQuestionRequestDTO dto) throws BadRequestException;

    void deleteQuestion(Long id, Long campaignScriptId) throws Exception;

    void deleteAllQuestion(Long campaignScriptId) throws Exception;

    Integer genPosition(Long campaignScripId);

    Integer getId(Long campaignScripId, String content);

    List<CampaignScriptQuestionResponseDTO> getAllQuestionByCampaignId(Long campaignId);

}
