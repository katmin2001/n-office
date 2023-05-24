package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmInterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmInterviewStatusRepo extends JpaRepository<CrmInterviewStatus, Long> {
}
