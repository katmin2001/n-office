package com.fis.crm.repository;

import com.fis.crm.domain.CampaignTemplate;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;

import java.util.List;

public interface CampaignTemplateCustomRepository {
    List<OptionSetValueDTO> getAllInfoCode();

    Boolean checkUnusedTemplate(Long id);

    CampaignTemplateDTO insertTemplate(CampaignTemplateDTO dto, Long userId);

    List<CampaignTemplateDTO> findAllAndStatus();

    CampaignTemplate findFirstByCampaignNameAndIdNot(String campaignName, Long id);
}
