package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTaskTimesheets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTimesheetsRepo extends JpaRepository<CrmTaskTimesheets, Long> {
}
