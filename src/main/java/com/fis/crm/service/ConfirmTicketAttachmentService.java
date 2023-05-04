package com.fis.crm.service;

import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;
import com.fis.crm.service.dto.ConfirmTicketAttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.ConfirmTicket}.
 */
public interface ConfirmTicketAttachmentService {
  /**
   * Save a ConfirmTicket.
   *
   * @param confirmTicketAttachmentDTO the entity to save.
   * @return the persisted entity.
   */
  ConfirmTicketAttachmentDTO save(ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO);

  /**
   * Partially updates a ConfirmTicket.
   *
   * @param confirmTicketAttachmentDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<ConfirmTicketAttachmentDTO> partialUpdate(ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO);

  /**
   * Get all the ConfirmTickets.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<ConfirmTicketAttachmentDTO> findAll(Pageable pageable);

  /**
   * Get the "id" ConfirmTicket.
   *
   * @param confirmTicketAttachmentId the id of the entity.
   * @return the entity.
   */
  Optional<ConfirmTicketAttachmentDTO> findOne(Long confirmTicketAttachmentId);

  /**
   * Delete the "id" ConfirmTicket.
   *
   * @param confirmTicketAttachmentId the id of the entity.
   */
  void delete(Long confirmTicketAttachmentId);

    List<ConfirmTicketAttachmentDTO> getAllByTicketId(Long ticketId);
}
