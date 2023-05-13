package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends JpaRepository<CrmRole, Long> {
}
