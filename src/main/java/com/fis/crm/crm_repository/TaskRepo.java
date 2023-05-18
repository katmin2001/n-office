package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.DTO.TaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<CrmTask, Long> {

    @Query("SELECT t FROM CrmTask t WHERE t.projectid = :id")
    List<CrmTask> findTasksByProjectId(@Param("id") Long id);

    @Query("SELECT t FROM CrmTask t WHERE t.stageid = :id")
    List<CrmTask> findTasksByStageId(@Param("id") Long id);

    @Query("SELECT t FROM CrmTask t WHERE t.givertaskid = :id")
    List<CrmTask> findTasksByGivertaskId(@Param("id") Long id);

    @Query("SELECT t FROM CrmTask t WHERE t.receivertaskid = :id")
    List<CrmTask> findTasksByReceivertaskId(@Param("id") Long id);

    @Query("SELECT t FROM CrmTask t WHERE t.statuscode = :id")
    List<CrmTask> findTasksByStatus(@Param("id") Long id);
}
