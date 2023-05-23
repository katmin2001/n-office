package com.fis.crm.service;

import com.fis.crm.domain.CampaignTemplate;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import com.fis.crm.service.dto.CampaignTemplateListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fis.crm.service.dto.OptionSetValueDTO;

import java.util.List;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.CampaignTemplate}.
 */
public interface CampaignTemplateService {
    /**
     * Save a campaignTemplate.
     *
     * @param campaignTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignTemplateDTO save(CampaignTemplateDTO campaignTemplateDTO);

    /**
     * Get all the campaignTemplates.
     *
     * @return the list of entities.
     */
    List<CampaignTemplateDTO> findAll();

    /**
     * Get the "id" campaignTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" campaignTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<OptionSetValueDTO> getAllInfoCode();

    Boolean checkUnusedTemplate(Long id);

    CampaignTemplate checkExistedTemplate(String campaignName, Long id);

    CampaignTemplateDTO update(CampaignTemplateDTO campaignTemplateDTO);

    CampaignTemplateDTO copy(CampaignTemplateDTO campaignTemplateDTO);

	List<CampaignTemplateListDTO> getListCampaignTemplateForCombobox();
}
