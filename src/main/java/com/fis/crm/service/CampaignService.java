package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Interface for managing {@link com.fis.crm.domain.Campaign}.
 */
public interface CampaignService {

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignDTO save(CampaignDTO campaignDTO);

    /**
     * Get all the campaigns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignDTO> findOne(Long id);

    /**
     * Delete the "id" campaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    Page<CampaignDTO> searchCampaign(CampaignDTO campaignDTO, Pageable pageable);

    Page<CampaignDTO> findAllCampaign(CampaignDTO campaignDTO, Pageable pageable);

    Page<CampaignDTO> findAllCampaigns(CampaignDTO campaignDTO, Pageable pageable);
}
