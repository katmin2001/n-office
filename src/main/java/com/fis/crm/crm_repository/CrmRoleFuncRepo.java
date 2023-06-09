package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CrmRoleFuncRepo extends JpaRepository<CrmRoleFunction, Long> {

    @Query(value = "select u from CrmRoleFunction u where u.role = :role")
    public List<CrmRoleFunction> findCrmRoleFunctionsByRole(@Param("role")CrmRole role);

    @Query(value = "select u from CrmRoleFunction u where u.role.rolename = :rolename")
    public List<CrmRoleFunction> findCrmRoleFunctionsByRoleName(@Param("rolename")String rolename);

    @Query(value = "select u from CrmRoleFunction u where u.role.roleid = :roleid")
    public List<CrmRoleFunction> findCrmRoleFunctionsByRoleId(@Param("roleid")Long roleid);

    @Query(value = "select u from CrmRoleFunction u where u.function.funcid = :funcid")
    public List<CrmRoleFunction> findCrmRoleFunctionsByFunctionId(@Param("funcid")Long funcid);

    @Query(value = "select u from CrmRoleFunction u where u.function = :function")
    public List<CrmRoleFunction> findCrmRoleFunctionsByFunction(@Param("function")CrmRole function);

    @Query(value = "select u from CrmRoleFunction u where u.function = :function")
    public List<CrmRoleFunction> findCrmRoleFunctionsByFunction(@Param("function") CrmFunction function);

    @Query(value = "select u from CrmRoleFunction u where u.function = :function and u.role = :role")
    public CrmRoleFunction findCrmRoleFunctionsByFunctionaAndRole(@Param("function") CrmFunction function,
                                                            @Param("role") CrmRole role);

    @Query(value = "select u from CrmRoleFunction u where u.role.rolename = :rolename and u.function.funcname = :funcname")
    public CrmRoleFunction findCrmRoleFunctionsByRoleNameAndFunctionName(@Param("rolename") String rolename,
                                                                  @Param("funcname") String funcname);

    @Query(value = "select u from CrmRoleFunction u where u.role.roleid = :roleid")
    public List<CrmFunction> findCrmFunctionsByRoleId(@Param("roleid")Long roleid);
}
