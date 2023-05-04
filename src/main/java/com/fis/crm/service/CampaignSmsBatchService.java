package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignSmsBatchDTO;
import com.fis.crm.service.dto.CampaignSmsBatchResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.CampaignSmsBatch}.
 */
public interface CampaignSmsBatchService {

    /**
     * Save a campaignSmsBatch.
     *
     * @param campaignSmsBatchDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignSmsBatchDTO save(CampaignSmsBatchDTO campaignSmsBatchDTO);

    List<CampaignSmsBatchDTO> save2(CampaignSmsBatchResDTO campaignSmsBatchResDTO);

    /**
     * Get all the campaignSmsBatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignSmsBatchDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campaignSmsBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignSmsBatchDTO> findOne(Long id);

    /**
     * Delete the "id" campaignSmsBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void saveListSmsBatch(List<CampaignSmsBatchDTO> campaignSmsBatchDTOS);

    void update(List<CampaignSmsBatchDTO> campaignSmsBatchDTOS);
}
