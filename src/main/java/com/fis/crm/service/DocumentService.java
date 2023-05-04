package com.fis.crm.service;

import com.fis.crm.service.dto.DocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.Document}.
 */
public interface DocumentService {

    /**
     * Save a document.
     *
     * @param documentDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentDTO save(DocumentDTO documentDTO);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentDTO> findAll(Pageable pageable);


    /**
     *
     * @return list of all documents
     */
    List<DocumentDTO> findAll();


    /**
     * Get the "id" document.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentDTO> findOne(Long id);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    List<DocumentDTO> findByParentId(Long parentId);

    List<DocumentDTO> findAllNotInParent(Long id);

    void moveDocument(Long id, Long newParentId);

}
