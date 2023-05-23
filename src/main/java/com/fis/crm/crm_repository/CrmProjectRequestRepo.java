package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProjectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmProjectRequestRepo extends JpaRepository<CrmProjectRequest, Long> {

}
