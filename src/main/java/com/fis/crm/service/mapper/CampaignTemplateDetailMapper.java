package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.CampaignTemplateDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignTemplateDetail} and its DTO {@link CampaignTemplateDetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignTemplateDetailMapper extends EntityMapper<CampaignTemplateDetailDTO, CampaignTemplateDetail> {}
