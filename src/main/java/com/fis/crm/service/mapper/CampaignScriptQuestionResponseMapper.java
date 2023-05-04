package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignScriptQuestion;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignScriptQuestionResponseMapper extends EntityMapper<CampaignScriptQuestionResponseDTO, CampaignScriptQuestion>{

    @Mapping(source = "campaignScript.id", target="campaignScriptId")
    @Mapping(source = "campaignScript.name", target="campaignScriptName")
    @Mapping(source = "createUser.id", target = "createUserId")
    @Mapping(source = "createUser.login", target = "createUsername")
    @Mapping(source = "updateUser.id", target = "updateUserId")
    @Mapping(source = "updateUser.login", target = "updateUsername")
    CampaignScriptQuestionResponseDTO toDto(CampaignScriptQuestion entity);
}
