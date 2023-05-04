package com.fis.crm.repository;

import com.fis.crm.domain.DocumentPostBookmarks;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the DocumentPostBookmarks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentPostBookmarksRepository extends JpaRepository<DocumentPostBookmarks, Long> {

    boolean existsByDocumentPostIdAndAndUserId(Long docPostId, Long userId);


    Optional<DocumentPostBookmarks> findByDocumentPostId(Long documentId);
}
