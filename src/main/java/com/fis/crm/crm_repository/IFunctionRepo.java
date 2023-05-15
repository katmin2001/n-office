package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFunctionRepo extends JpaRepository<CrmFunction, Long> {
}
