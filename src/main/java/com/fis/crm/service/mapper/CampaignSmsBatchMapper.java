package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignSmsBatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignSmsBatch} and its DTO {@link CampaignSmsBatchDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignSmsBatchMapper extends EntityMapper<CampaignSmsBatchDTO, CampaignSmsBatch> {



    default CampaignSmsBatch fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignSmsBatch campaignSmsBatch = new CampaignSmsBatch();
        campaignSmsBatch.setId(id);
        return campaignSmsBatch;
    }
}
