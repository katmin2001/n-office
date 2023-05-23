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
    @Query(value = "SELECT *\n" +
        "FROM CRM_TASK t\n" +
        "         LEFT JOIN CRM_PROJECT CP ON t.PROJECTID = CP.ID\n" +
        "         LEFT JOIN CRM_TASK_STATUS CTS ON CTS.STATUSCODE = t.STATUSCODE\n" +
        "         LEFT JOIN CRM_USER U ON U.USERID = t.GIVERTASKID AND U.USERID = t.RECEIVERTASKID\n" +
        "WHERE (:taskname IS NULL OR lower(t.taskname) LIKE '%' || lower(:taskname) || '%')\n" +
        "  AND (:statusname IS NULL OR lower(CTS.STATUSNAME) LIKE '%' || lower(:statusname) || '%')\n" +
        "  AND (:givertaskname IS NULL OR lower(U.fullname) LIKE '%' || lower(:givertaskname) || '%')\n" +
        "  AND (:receivertaskname IS NULL OR lower(U.fullname) LIKE '%' || lower(:receivertaskname) || '%')\n" +
        "  AND (:projectname IS NULL OR lower(CP.name) LIKE '%' || lower(:projectname) || '%')\n" +
        "  AND ((:taskProcess IS NULL)\n" +
        "  OR (lower(:taskProcess) = 'trong hạn' AND T.STARTDATE >= CURRENT_DATE)\n" +
        "  OR (lower(:taskProcess) = 'quá hạn' AND T.ENDDATE < CURRENT_DATE))",
        nativeQuery = true)
    List<CrmTask> findTaskByKeyword(@Param("taskname") String taskname,
                                    @Param("statusname") String statusname,
                                    @Param("givertaskname") String givertaskname,
                                    @Param("receivertaskname") String receivertaskname,
                                    @Param("projectname") String projectname,
                                    @Param("taskProcess") String taskProcess);
    @Query("SELECT t FROM CrmTask t WHERE t.taskid = :taskid")
    CrmTask findByTaskId(@Param("taskid") Long taskid);
}
