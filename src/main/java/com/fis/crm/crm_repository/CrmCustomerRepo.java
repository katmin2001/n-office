package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmCustomerRepo extends JpaRepository<CrmCustomer, Long> {
}
