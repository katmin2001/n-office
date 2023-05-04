package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignResourceDetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignResourceDetail} and its DTO {@link CampaignResourceDetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignResourceDetailMapper extends EntityMapper<CampaignResourceDetailDTO, CampaignResourceDetail> {



    default CampaignResourceDetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignResourceDetail campaignResourceDetail = new CampaignResourceDetail();
        campaignResourceDetail.setId(id);
        return campaignResourceDetail;
    }
}
