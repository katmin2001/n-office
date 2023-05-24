package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CrmRoleRepo extends JpaRepository<CrmRole, Long> {
    @Query(value = "select u from CrmRole u where u.rolename = :rolename")
    CrmRole findCrmRoleByRolename(@Param("rolename") String rolename);

    @Query(value = "select u from CrmRole u where u.roleid = :id")
    CrmRole findCrmRoleByRoleid(@Param("id") Long id);
}
