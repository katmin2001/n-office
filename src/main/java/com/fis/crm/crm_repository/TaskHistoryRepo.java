package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskHistoryRepo extends JpaRepository<CrmTaskHistory, Long> {
    @Query("SELECT t FROM CrmTaskHistory t WHERE t.taskid = :id")
    List<CrmTaskHistory> findTaskHistoryByTaskId(@Param("id") Long id);

}
