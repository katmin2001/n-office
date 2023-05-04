package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignScriptQuestion;
import com.fis.crm.service.dto.CampaignScriptQuestionRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignScriptQuestionRequestMapper extends EntityMapper<CampaignScriptQuestionRequestDTO, CampaignScriptQuestion> {
    @Mapping(source = "campaignScriptId", target = "campaignScript.id")
    @Mapping(source = "id", target = "id")
    CampaignScriptQuestion toEntity(CampaignScriptQuestionRequestDTO dto);

    void updateCampaignScriptQuestionFromDto(CampaignScriptQuestionRequestDTO dto, @MappingTarget CampaignScriptQuestion entity);
}
