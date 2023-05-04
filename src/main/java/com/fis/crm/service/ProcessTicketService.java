package com.fis.crm.service;

import com.fis.crm.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.TicketRequest}.
 */
public interface ProcessTicketService {
    /**
     * Save a ticketRequest.
     *
     * @param processTicketDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceResult save(ProcessTicketDTO processTicketDTO);

    /**
     * Get all the ticketRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessTicketDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketRequest.
     *
     * @param processTicketId the id of the entity.
     * @return the entity.
     */
    Optional<ProcessTicketDTO> findOne(Long processTicketId);

    /**
     * Delete the "id" ticketRequest.
     *
     * @param processTicketId the id of the entity.
     */
    void delete(Long processTicketId);

    List<ProcessTicketDTO> getListHistoryProcessTickets(ProcessTicketDTO processTicketDTO);

    ServiceResult saveFRC(TicketProcessDTO ticketProcessDTO);
}
