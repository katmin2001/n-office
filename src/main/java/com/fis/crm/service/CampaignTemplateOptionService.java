package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignTemplateOptionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fis.crm.domain.CampaignTemplateOption}.
 */
public interface CampaignTemplateOptionService {
  /**
   * Save a campaignTemplateOption.
   *
   * @param campaignTemplateOptionDTO the entity to save.
   * @return the persisted entity.
   */
  CampaignTemplateOptionDTO save(CampaignTemplateOptionDTO campaignTemplateOptionDTO);

  /**
   * Get all the campaignTemplateOptions.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<CampaignTemplateOptionDTO> findAll(Pageable pageable);

  /**
   * Get the "id" campaignTemplateOption.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<CampaignTemplateOptionDTO> findOne(Long id);

  /**
   * Delete the "id" campaignTemplateOption.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);
}
