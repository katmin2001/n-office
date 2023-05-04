package com.fis.crm.service;

import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.TicketRequest}.
 */
public interface TicketRequestAttachmentService {
  /**
   * Save a ticketRequest.
   *
   * @param TicketRequestAttachmentDTO the entity to save.
   * @return the persisted entity.
   */
  TicketRequestAttachmentDTO save(TicketRequestAttachmentDTO TicketRequestAttachmentDTO);

  /**
   * Partially updates a ticketRequest.
   *
   * @param TicketRequestAttachmentDTO the entity to update partially.
   * @return the persisted entity.
   */
  Optional<TicketRequestAttachmentDTO> partialUpdate(TicketRequestAttachmentDTO TicketRequestAttachmentDTO);

  /**
   * Get all the ticketRequests.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<TicketRequestAttachmentDTO> findAll(Pageable pageable);

  /**
   * Get the "id" ticketRequest.
   *
   * @param ticketRequestAttachmentId the id of the entity.
   * @return the entity.
   */
  Optional<TicketRequestAttachmentDTO> findOne(Long ticketRequestAttachmentId);

  /**
   * Delete the "id" ticketRequest.
   *
   * @param ticketRequestAttachmentId the id of the entity.
   */
  void delete(Long ticketRequestAttachmentId);

  TicketRequestAttachmentDTO importList(MultipartFile file);

    List<TicketRequestAttachmentDTO> findByTicketId(Long ticketId);
}
