package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import com.fis.crm.crm_entity.CrmTaskTimesheets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskTimesheetsRepo extends JpaRepository<CrmTaskTimesheets, Long> {
    @Query("SELECT t FROM CrmTaskTimesheets t WHERE t.project.id = :id")
    List<CrmTaskTimesheets> findTimesheetsByProjectId(@Param("id") Long id);

    @Query("SELECT t FROM CrmTaskTimesheets t WHERE t.user.userid = :id")
    List<CrmTaskTimesheets> findTimesheetsByUserId(@Param("id") Long id);
}
