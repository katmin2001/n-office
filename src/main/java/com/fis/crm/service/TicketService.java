package com.fis.crm.service;

import com.fis.crm.domain.Ticket;
import com.fis.crm.service.dto.RequestSearchTicketDTO;
import com.fis.crm.service.dto.ServiceResult;
import com.fis.crm.service.dto.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.Ticket}.
 */
public interface TicketService {
    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceResult save(TicketDTO ticketDTO);

    /**
     * Partially updates a ticket.
     *
     * @param ticketDTO the entity to update partially.
     * @return the persisted entity.
     */
/*    Optional<TicketDTO> partialUpdate(TicketDTO ticketDTO);*/

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticket.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketDTO> findOne(Long id);

    void updateForProcess(TicketDTO ticketDTO);
    /**
     * Delete the "id" ticket.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<TicketDTO> getListHistorySupports(TicketDTO ticketDTO, Pageable pageable);

    Page<TicketDTO> searchTicket(RequestSearchTicketDTO requestSearchTicketDTO, Pageable pageable);

    List<TicketDTO> exportExcelTicket(RequestSearchTicketDTO requestSearchTicketDTO);

    void updateTicketConfirmDate(Ticket ticket);

    void updateTicketConfirmDateAndStatus(Ticket ticket);

    ServiceResult saveInactive(TicketDTO ticketDTO);

}
