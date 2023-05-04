package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignTemplate} and its DTO {@link CampaignTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignTemplateMapper extends EntityMapper<CampaignTemplateDTO, CampaignTemplate> {}
