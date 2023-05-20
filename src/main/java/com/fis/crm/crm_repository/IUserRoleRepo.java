package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IUserRoleRepo extends JpaRepository<CrmUserRole, Long> {

    @Query(value = "select u from CrmUserRole  u where u.role.roleid = :roleId")
    public List<CrmUserRole> findCrmUserRoleByRoleId(@Param("roleId") Long roleId);

    @Query(value = "select u from CrmUserRole u where u.role = :role and u.user = :user")
    public CrmUserRole findCrmUserRoleByRoleAndUser(@Param("user")CrmUser user, @Param("role")CrmRole role);

    @Query(value = "select u from CrmUserRole u where u.role = :role")
    public List<CrmUserRole> findCrmUserRoleByRole(@Param("role") CrmRole role);

    @Query(value = "select u from CrmUserRole u where u.user = :user")
    public List<CrmUserRole> findCrmUserRoleByUser(@Param("user") CrmUser user);

}
