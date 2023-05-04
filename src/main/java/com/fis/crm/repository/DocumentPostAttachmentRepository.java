package com.fis.crm.repository;

import com.fis.crm.domain.DocumentPostAttachment;

import com.fis.crm.service.dto.DocumentPostAttachmentDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DocumentPostAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentPostAttachmentRepository extends JpaRepository<DocumentPostAttachment, Long> {

    List<DocumentPostAttachment> findAllByDocumentPostIdAndStatusOrderByCreateDatetimeDesc(Long documentPostId, String status);

    @Query(value = "select new DocumentPostAttachmentDTO(a, b.login) from DocumentPostAttachment a left join User b on a.createUser = b.id where a.documentPostId = :documentPostId " +
        " and a.status = :status order by a.createDatetime desc ")
    List<DocumentPostAttachmentDTO> findAllByPostAndStatus(@Param("documentPostId") Long documentPostId, @Param("status") String status);


    void deleteByDocumentPostId(Long documentPostId);

    List<DocumentPostAttachment> findAllByIdIn(List<Long> ids);
}
