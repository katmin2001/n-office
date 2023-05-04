package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignResourceError;
import com.fis.crm.service.dto.CampaignResourceErrorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignResourceErrorMapper extends EntityMapper<CampaignResourceErrorDTO, CampaignResourceError> {

    default CampaignResourceError fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignResourceError campaignResourceError = new CampaignResourceError();
        campaignResourceError.setId(id);
        return campaignResourceError;
    }

}
