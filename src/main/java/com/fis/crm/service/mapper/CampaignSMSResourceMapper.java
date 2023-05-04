package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignSMSResource;
import com.fis.crm.service.dto.CampaignSMSResourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignSMSResourceMapper extends EntityMapper<CampaignSMSResourceDTO, CampaignSMSResource> {
}
