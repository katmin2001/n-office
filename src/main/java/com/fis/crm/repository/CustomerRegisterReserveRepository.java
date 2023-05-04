package com.fis.crm.repository;

import com.fis.crm.domain.CustomerRegisterReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRegisterReserveRepository extends JpaRepository<CustomerRegisterReserve, Long> {
}
