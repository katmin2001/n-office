package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CrmFunctionRepo extends JpaRepository<CrmFunction, Long> {
    @Query(value = "select u from CrmFunction u where u.funcname = :funcname")
    CrmFunction findCrmFunctionByRolename(@Param("funcname") String funcname);

    @Query(value = "select u from CrmFunction u where u.funcid = :id")
    CrmFunction findCrmFunctionByFuncId(@Param("id") Long id);

    @Query(value = "select u from CrmFunction u where u.funcname = :funcname")
    CrmFunction findCrmFunctionByFuncName(@Param("funcname") String funcname);
}
