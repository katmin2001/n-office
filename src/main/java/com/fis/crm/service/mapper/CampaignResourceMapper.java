package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignResource;
import com.fis.crm.service.dto.CampaignResourceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignResourceMapper extends EntityMapper<CampaignResourceDTO, CampaignResource> {

    default CampaignResource fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignResource campaignResource = new CampaignResource();
        campaignResource.setId(id);
        return campaignResource;
    }

}
