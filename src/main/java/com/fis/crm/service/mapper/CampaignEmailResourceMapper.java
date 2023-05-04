package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignEmailResource;
import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignEmailResourceMapper extends EntityMapper<CampaignEmailResourceDTO, CampaignEmailResource> {
}
