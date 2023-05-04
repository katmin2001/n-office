package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignSMSBlackList;
import com.fis.crm.service.dto.CampaignSMSBlackListDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface  CampaignSMSBlackListMapper extends EntityMapper<CampaignSMSBlackListDTO, CampaignSMSBlackList> {
}
