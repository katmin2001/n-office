package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignBlacklist;
import com.fis.crm.service.dto.CampaignBlackListDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignBlackListMapper extends EntityMapper<CampaignBlackListDTO, CampaignBlacklist> {
}
