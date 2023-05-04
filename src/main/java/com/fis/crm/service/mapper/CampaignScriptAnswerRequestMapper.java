package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignScriptAnswer;
import com.fis.crm.service.dto.CampaignScriptAnswerRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignScriptAnswerRequestMapper extends EntityMapper<CampaignScriptAnswerRequestDTO, CampaignScriptAnswer> {
}
