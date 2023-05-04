package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignEmailBlackList;
import com.fis.crm.service.dto.CampaignEmailBlackListDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignEmailBlackListMapper extends EntityMapper<CampaignEmailBlackListDTO, CampaignEmailBlackList> {
}
