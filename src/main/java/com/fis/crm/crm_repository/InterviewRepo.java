package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepo extends JpaRepository<CrmInterview,Long> {
}
