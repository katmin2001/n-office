package com.fis.crm.service;

import com.fis.crm.service.dto.ProcessTicketEntityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ProcessTicketEntity}.
 */
public interface ProcessTicketEntityService {

    /**
     * Save a processTicketEntity.
     *
     * @param processTicketEntityDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessTicketEntityDTO save(ProcessTicketEntityDTO processTicketEntityDTO);

    /**
     * Get all the processTicketEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessTicketEntityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" processTicketEntity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessTicketEntityDTO> findOne(Long id);

    /**
     * Delete the "id" processTicketEntity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
