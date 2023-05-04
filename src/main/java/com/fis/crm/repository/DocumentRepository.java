package com.fis.crm.repository;

import com.fis.crm.domain.Document;

import com.fis.crm.service.dto.DocumentDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Document entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByParentId(Long parentId);

    @Query(value = "select d.* from DOCUMENT d where d.id not in (" +
        "  select b.id from DOCUMENT b connect by prior b.ID = b.PARENT_ID start with b.id = ?1 )",
        nativeQuery = true)
    List<Document> findAllNotInParent(Long id);

    @Query(value = "select d.* from DOCUMENT d where 1=1 " +
        "                             and d.id = ?1 " +
        "                             and d.id in ( " +
        "  select b.id from DOCUMENT b connect by prior b.ID = b.PARENT_ID start with b.id = ?2)",
        nativeQuery = true)
    List<Document> getAllByByIdAndParent(Long newParentId, Long id);


    @Query(value = "select a.id, name, parent_id, status, create_datetime, create_user," +
        "       sys_connect_by_path(a.NAME, '/') path_name," +
        "       sys_connect_by_path(a.id, '/') path_id," +
        "       LEVEL" +
        " from DOCUMENT a connect by prior a.ID = a.PARENT_ID" +
        " start with a.PARENT_ID is null", nativeQuery = true)
    List<Object[]> findAllDocument();


}
