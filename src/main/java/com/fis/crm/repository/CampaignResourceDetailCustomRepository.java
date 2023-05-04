package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignResourceDetailDTO;

import java.util.List;

public interface CampaignResourceDetailCustomRepository {
    List<CampaignResourceDetailDTO> getByCampaignResourceId(Long campaignResourceId);
}
