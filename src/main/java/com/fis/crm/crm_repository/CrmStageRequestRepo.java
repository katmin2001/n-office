package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmStageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmStageRequestRepo extends JpaRepository<CrmStageRequest, Long> {

}
