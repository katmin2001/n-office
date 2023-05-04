package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignScriptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignScript} and its DTO {@link CampaignScriptDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignScriptMapper extends EntityMapper<CampaignScriptDTO, CampaignScript> {



    default CampaignScript fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignScript campaignScript = new CampaignScript();
        campaignScript.setId(id);
        return campaignScript;
    }

    @Mapping(source = "createUser.id", target = "createUserId")
    @Mapping(source = "createUser.login", target = "createUsername")
    @Mapping(source = "updateUser.id", target = "updateUserId")
    @Mapping(source = "updateUser.login", target = "updateUsername")
    CampaignScriptDTO toDto(CampaignScript entity);

}
