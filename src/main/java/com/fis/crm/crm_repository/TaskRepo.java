package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<CrmTask, Long> {

    @Query("SELECT t FROM CrmTask t WHERE t.project.id = :id")
    List<CrmTask> findTasksByProjectId(@Param("id") Long id);
    @Query(value = "SELECT t FROM CrmTask t " +
        "WHERE (:taskname is null OR lower(t.taskname) LIKE %:taskname%) " +
        "AND (:statusname is null OR lower(t.status.name) LIKE %:statusname%) " +
        "AND (:givertaskname is null OR lower(t.givertask.username) LIKE %:givertaskname%) " +
        "AND (:receivertaskname is null OR lower(t.receivertask.username) LIKE %:receivertaskname%) " +
        "AND (:stagename is null OR lower(t.stage.name) LIKE %:stagename%) " +
        "AND (:projectname is null OR lower(t.project.name) LIKE %:projectname%) ")
    List<CrmTask> findTaskByKeyword(@Param("taskname") String taskname,
                             @Param("statusname") String statusname,
                             @Param("givertaskname") String givertaskname,
                             @Param("receivertaskname") String receivertaskname,
                             @Param("stagename") String stagename,
                             @Param("projectname") String projectname);
    @Query("SELECT t FROM CrmTask t WHERE t.taskid = :taskid")
    CrmTask findByTaskId(@Param("taskid") Long taskid);
}
