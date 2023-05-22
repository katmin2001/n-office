package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStatusRepo extends JpaRepository<CrmTaskStatus, Long> {

    @Query("SELECT t FROM CrmTaskStatus t WHERE t.id = :id")
    CrmTaskStatus findStatus(@Param("id") Long id);
}
