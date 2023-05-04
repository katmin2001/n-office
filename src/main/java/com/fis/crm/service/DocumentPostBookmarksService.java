package com.fis.crm.service;

import com.fis.crm.service.dto.DocumentPostBookmarksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.DocumentPostBookmarks}.
 */
public interface DocumentPostBookmarksService {

    /**
     * Save a documentPostBookmarks.
     *
     * @param documentPostBookmarksDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentPostBookmarksDTO save(DocumentPostBookmarksDTO documentPostBookmarksDTO);

    /**
     * Get all the documentPostBookmarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPostBookmarksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentPostBookmarks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentPostBookmarksDTO> findOne(Long id);

    /**
     * Delete the "id" documentPostBookmarks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Check bai viet da duoc danh dau bookmarks hay chua
     * @param docPostId
     * @param userId
     * @return
     */
    boolean existsByDocumentPostIdAndAndUserId(Long docPostId, Long userId);
}
