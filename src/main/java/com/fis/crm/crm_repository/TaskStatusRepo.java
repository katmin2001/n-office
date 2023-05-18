package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepo extends JpaRepository<CrmTaskStatus, Long> {
}
