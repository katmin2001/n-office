package com.fis.crm.service.mapper;


import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampaignResourceTemplateMapper extends EntityMapper<CampaignResourceTemplateDTO, CampaignResourceTemplate> {

}
