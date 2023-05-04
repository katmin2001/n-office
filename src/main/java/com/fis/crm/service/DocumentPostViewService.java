package com.fis.crm.service;

import com.fis.crm.service.dto.DocumentPostViewDTO;

import com.fis.crm.service.dto.DocumentPostViewDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.DocumentPostView}.
 */
public interface DocumentPostViewService {

    /**
     * Save a documentPostView.
     *
     * @param documentPostViewDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentPostViewDTO save(DocumentPostViewDTO documentPostViewDTO);

    /**
     * Get all the documentPostViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPostViewDTO> findAll(Pageable pageable);

    /**
     * Tim kiem danh sach nguoi dung xem bai viet
     * @param postId
     * @param pageable
     * @return
     */
    Page<DocumentPostViewDetail> findAllByPostId(Long postId, Pageable pageable);


    /**
     * Get the "id" documentPostView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentPostViewDTO> findOne(Long id);

    /**
     * Delete the "id" documentPostView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
