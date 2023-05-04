package com.fis.crm.service;

import com.fis.crm.service.dto.DocumentPostAttachmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.DocumentPostAttachment}.
 */
public interface DocumentPostAttachmentService {

    /**
     * Save a documentPostAttachment.
     *
     * @param documentPostAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentPostAttachmentDTO save(DocumentPostAttachmentDTO documentPostAttachmentDTO);

    /**
     * Get all the documentPostAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPostAttachmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentPostAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentPostAttachmentDTO> findOne(Long id);

    List<DocumentPostAttachmentDTO> findAllByDocumentPostId(Long documentPostId);

    /**
     * Delete the "id" documentPostAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
