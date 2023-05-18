package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmRoleFunction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleFuncRepo extends JpaRepository<CrmRoleFunction, Long> {
}
