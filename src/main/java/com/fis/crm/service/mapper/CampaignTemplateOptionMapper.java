package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignTemplateOptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignTemplateOption} and its DTO {@link CampaignTemplateOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignTemplateOptionMapper extends EntityMapper<CampaignTemplateOptionDTO, CampaignTemplateOption> {}
