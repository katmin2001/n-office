package com.fis.crm.service;

import com.fis.crm.domain.Ticket;
import com.fis.crm.domain.TicketRequest;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.dto.TicketRequestDTO;

import java.util.List;
import java.util.Optional;

import com.fis.crm.service.dto.TicketRequestExtDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.fis.crm.domain.TicketRequest}.
 */
public interface TicketRequestService {
    /**
     * Save a ticketRequest.
     *
     * @param ticketRequestDTO the entity to save.
     * @return the persisted entity.
     */
    TicketRequestDTO save(TicketRequestDTO ticketRequestDTO);

    /**
     * Get all the ticketRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketRequestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketRequestDTO> findOne(Long id);

    /**
     * Delete the "id" ticketRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<TicketRequestExtDTO> getTicketRequestByTicketId(TicketDTO ticketDTO);
}
