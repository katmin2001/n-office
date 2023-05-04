package com.fis.crm.service.mapper;

import com.fis.crm.domain.CampaignEmailBatch;
import com.fis.crm.service.dto.CampaignEmailBatchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CampaignEmailBatchMapper extends EntityMapper<CampaignEmailBatchDTO, CampaignEmailBatch> {
}
