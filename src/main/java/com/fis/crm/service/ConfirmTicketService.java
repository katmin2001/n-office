package com.fis.crm.service;

import com.fis.crm.service.dto.ProcessTicketDTO;
import com.fis.crm.service.dto.ConfirmTicketDTO;
import com.fis.crm.service.dto.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.TicketRequest}.
 */
public interface ConfirmTicketService {
    /**
     * Save a ticketRequest.
     *
     * @param confirmTicketDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceResult save(ConfirmTicketDTO confirmTicketDTO);

    /**
     * Get all the ticketRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfirmTicketDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketRequest.
     *
     * @param confirmTicketId the id of the entity.
     * @return the entity.
     */
    Optional<ConfirmTicketDTO> findOne(Long confirmTicketId);

    /**
     * Delete the "id" ticketRequest.
     *
     * @param confirmTicketId the id of the entity.
     */
    void delete(Long confirmTicketId);

    List<ConfirmTicketDTO> getListHistoryConfirmTickets(Long ticketId);

    List<ConfirmTicketDTO> getAllByTicketId(Long ticketId);
}
