package com.fis.crm.service;

import com.fis.crm.domain.DocumentPost;
import com.fis.crm.domain.DocumentPostNotApprove;
import com.fis.crm.service.dto.DocumentPostApprove;
import com.fis.crm.service.dto.DocumentPostDTO;

import com.fis.crm.service.dto.DocumentPostDetail;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.DocumentPost}.
 */
public interface DocumentPostService {

    /**
     * Save a documentPost.
     *
     * @param documentPostDTO the entity to save.
     * @return the persisted entity.
     */
    DocumentPostDTO save(DocumentPostDTO documentPostDTO, List<MultipartFile> documentAttachs) throws Exception;

    /**
     * Get all the documentPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentPostDTO> findAll(String title, Pageable pageable);


    /**
     * Get all bai viet chua phan trang
     * @param pageable
     * @return
     */
    Page<DocumentPostNotApprove> findAllNotApprove(Pageable pageable);


    /**
     * Get the "id" documentPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentPostDTO> findOne(Long id);

    /**
     * Delete the "id" documentPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find document_post by documentId
     * @param documentId
     * @return
     */
    List<DocumentPostDTO> findByDocumentId(Long documentId);


    /**
     * Get detail of document
     * @param id
     * @return
     */
    Optional<DocumentPostDetail> getDocumentPostById(Long id, boolean isView);


    /**
     * Chuyen bai viet tu thu muc
     * @param srcDocId
     * @param desDocId
     */
    void moveDocument(Long srcDocId, Long desDocId);


    /**
     * Approve document
     * @param documentPostApprove
     */
    void approveDocument(DocumentPostApprove documentPostApprove);

    /**
     * Bookmark documentId
     * @param id
     */
    void bookmarkDocument(Long id);

    /**
     * Remove bookmark
     * @param id
     */
    void removeBookmark(Long id);

    /**
     * Get top view
     * @param pageable
     * @return
     */
    Page<DocumentPostDTO> getTopView(Pageable pageable);

    /**
     * Get list document bookmark
     * @param pageable
     * @return
     */
    Page<DocumentPostDTO> getBookmark(Pageable pageable);

    /**
     * Get list new post
     * @param pageable
     * @return
     */
    Page<DocumentPostDTO> getNewPost(Pageable pageable);

    Resource getFile(Long id);
}
