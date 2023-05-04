package com.fis.crm.service;

import com.fis.crm.domain.ProccessTicketAttachment;
import com.fis.crm.service.dto.ProcessTicketDTO;
import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.TicketRequest}.
 */
public interface ProcessTicketAttachmentService {
    /**
     * Save a ticketRequest.
     *
     * @param processTicketAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessTicketAttachmentDTO save(ProcessTicketAttachmentDTO processTicketAttachmentDTO);

    /**
     * Partially updates a ticketRequest.
     *
     * @param processTicketAttachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessTicketAttachmentDTO> partialUpdate(ProcessTicketAttachmentDTO processTicketAttachmentDTO);

    /**
     * Get all the ticketRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcessTicketAttachmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketRequest.
     *
     * @param processTicketAttachmentId the id of the entity.
     * @return the entity.
     */
    Optional<ProcessTicketAttachmentDTO> findOne(Long processTicketAttachmentId);

  /**
   * Delete the "id" ticketRequest.
   *
   * @param processTicketAttachmentId the id of the entity.
   */
  void delete(Long processTicketAttachmentId);

  List<ProcessTicketAttachmentDTO> findByTicketRequestId(Long ticketId);

  List<ProcessTicketAttachmentDTO> findAllByTicketRequestId(Long ticketRequestId);
}
