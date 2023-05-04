package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignSMSMarketing;
import com.fis.crm.service.dto.CampaignSMSMarketingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignSMSMarketingMapper extends EntityMapper<CampaignSMSMarketingDTO, CampaignSMSMarketing> {
}
