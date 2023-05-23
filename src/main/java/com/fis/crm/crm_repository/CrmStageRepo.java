package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmStageRepo extends JpaRepository<CrmStage, Long> {
}
