package com.fis.crm.repository;

import com.fis.crm.domain.DocumentPostView;

import com.fis.crm.service.dto.DocumentPostViewDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentPostView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentPostViewRepository extends JpaRepository<DocumentPostView, Long> {


    @Query(value = "select new DocumentPostViewDetail(a, b.login, b.firstName) from DocumentPostView a inner join User b on a.userId = b.id " +
        " where a.documentPostId = :postId ",
        countQuery = "select count(a) from DocumentPostView a inner join User b on a.userId = b.id " +
            " where a.documentPostId = :postId ")
    Page<DocumentPostViewDetail> searchUserView(@Param("postId") Long postId, Pageable pageable);
}
