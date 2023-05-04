package com.fis.crm.service;

import com.fis.crm.domain.CampaignTemplateDetail;
import com.fis.crm.service.dto.CampaignTemplateDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CampaignTemplateDetail}.
 */
public interface CampaignTemplateDetailService {
  /**
   * Save a campaignTemplateDetail2.
   *
   * @param campaignTemplateDetailDTO the entity to save.
   * @return the persisted entity.
   */
  CampaignTemplateDetailDTO save(CampaignTemplateDetailDTO campaignTemplateDetailDTO);

    /**
     * Get all the campaignTemplateDetail2s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignTemplateDetailDTO> findAll(Pageable pageable);

    /**
     * Get the "id" campaignTemplateDetail2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignTemplateDetailDTO> findOne(Long id);

    /**
     * Delete the "id" campaignTemplateDetail2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CampaignTemplateDetailDTO> findAllByCampaignId(Long id);
}
