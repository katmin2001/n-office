package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignEmailMarketing;
import com.fis.crm.service.dto.CampaignEmailMarketingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignEmailMarketingMapper extends EntityMapper<CampaignEmailMarketingDTO, CampaignEmailMarketing> {
}
