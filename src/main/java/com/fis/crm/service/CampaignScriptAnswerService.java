package com.fis.crm.service;

import com.fis.crm.web.rest.errors.CodeExisted;
import com.fis.crm.service.dto.CampaignScriptAnswerRequestDTO;
import com.fis.crm.service.dto.CampaignScriptAnswerResponseDTO;
import com.fis.crm.web.rest.errors.WrongFormat;
import io.undertow.util.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampaignScriptAnswerService {
    CampaignScriptAnswerResponseDTO save(CampaignScriptAnswerRequestDTO dto) throws BadRequestException, CodeExisted, WrongFormat;

    Page<CampaignScriptAnswerResponseDTO> findAllByCampaignScriptQuestionIdPageable(Long campaignScriptQuestionId, Pageable pageable);

    Boolean checkPosition(CampaignScriptAnswerRequestDTO dto) throws BadRequestException;

    CampaignScriptAnswerResponseDTO updateAnswer(CampaignScriptAnswerRequestDTO dto) throws BadRequestException;

    CampaignScriptAnswerResponseDTO getById(Long answerId);

    void deleteAnswer(Long id) throws Exception;

    void deleteAllAnswer(Long campaignScriptQuestionId) throws Exception;

    Integer genPosition(Long questionId);
}
