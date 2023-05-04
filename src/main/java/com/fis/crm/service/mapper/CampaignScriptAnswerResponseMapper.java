package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignScriptAnswer;
import com.fis.crm.service.dto.CampaignScriptAnswerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignScriptAnswerResponseMapper extends EntityMapper<CampaignScriptAnswerResponseDTO, CampaignScriptAnswer> {
    @Mapping(source = "question.id", target = "campaignScriptQuestionId")
    @Mapping(source = "createUser.id", target = "createUserId")
    @Mapping(source = "createUser.login", target = "createUsername")
    @Mapping(source = "updateUser.id", target = "updateUserId")
    @Mapping(source = "updateUser.login", target = "updateUsername")
    @Mapping(source = "question.code", target = "questionCode")
    CampaignScriptAnswerResponseDTO toDto (CampaignScriptAnswer entity);
}
