package com.fis.crm.repository;

import com.fis.crm.domain.DocumentPost;
import com.fis.crm.domain.Document;

import com.fis.crm.domain.DocumentPostNotApprove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DocumentPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentPostRepository extends JpaRepository<DocumentPost, Long> {
    List<DocumentPost> findByDocumentId(Long documentId);

    @Query(value = "select a from DocumentPost a where a.documentId = ?1 and a.title like ?2 escape '\\'")
    List<DocumentPost> queryCheckTitleForInsert(Long documentId, String title);

    @Query(value = "select a from DocumentPost a where a.id <> ?1 and a.documentId = ?2 and a.title like ?3 escape '\\' ")
    List<DocumentPost> queryCheckTitleForUpdate(Long id, Long documentId,  String title);

    @Modifying
    @Query(value = "update document_post set view_total = view_total + 1 where id = ?1", nativeQuery = true)
    void updateViewTotal(Long id);

    @Query(value = "select a from DocumentPost a where 1 = 1 and a.status = '1' and a.approveStatus = '2' " +
        " and (:title is null or lower(a.title) like :title escape '\\' or lower(a.content) like :title escape '\\' or lower(a.tags) like :title escape '\\') ",
        countQuery = "select count(a) from DocumentPost a where 1 = 1 and a.status = '1' and a.approveStatus = '2' " +
        " and (:title is null or lower(a.title) like :title escape '\\' or lower(a.content) like :title escape '\\' or lower(a.tags) like :title escape '\\') ")
    Page<DocumentPost> queryDocumentPost(@Param("title") String title, Pageable pageable);


    Page<DocumentPost> findAll(Pageable pageable);

    Optional<DocumentPost> findByIdAndStatus(Long id, String status);


    @Modifying(clearAutomatically = true)
    @Query(value = "update document_post set document_id = ?2 where document_id = ?1", nativeQuery = true)
    void moveDocument(Long srcDocId, Long desDocId);

    List<DocumentPost> findAllByIdIn(List<Long> ids);


    Page<DocumentPost> findAllByStatusAndApproveStatus(String status, String approveStatus, Pageable pageable);

    @Query(value = "select new DocumentPostNotApprove(a, b.name, c.login) from DocumentPost a " +
        " inner join Document b on a.documentId = b.id " +
        " inner join User c on a.createUser = c.id " +
        " where a.status = :status and a.approveStatus = :approveStatus ",
    countQuery = "select count(a) from DocumentPost a inner join Document b on a.documentId = b.id" +
        " inner join User c on a.createUser = c.id " +
        " where a.status = :status and a.approveStatus = :approveStatus ")
    Page<DocumentPostNotApprove> searchDocumentNotApprove(@Param("status") String status,@Param("approveStatus") String approveStatus, Pageable pageable);


    @Query(value = "select a from DocumentPost a inner join DocumentPostBookmarks b on a.id = b.documentPostId " +
        " where b.userId = :userId and a.status = '1' and a.approveStatus = '2' order by b.createDatetime desc",
        countQuery = "select count(a) from DocumentPost a inner join DocumentPostBookmarks b on a.id = b.documentPostId " +
            " where b.userId = :userId and a.status = '1' and a.approveStatus = '2' ")
    Page<DocumentPost> findAllBookmark(@Param("userId") Long userId, Pageable pageable);
}
