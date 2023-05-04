package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignTemplateDetailDTO;

import java.util.List;

public interface CampaignTemplateDetailCustomRepository {
    List<CampaignTemplateDetailDTO> findAllByCampaignId(Long id);
}
