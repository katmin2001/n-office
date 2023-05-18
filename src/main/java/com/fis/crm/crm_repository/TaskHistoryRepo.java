package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepo extends JpaRepository<CrmTaskHistory, Long> {
}
