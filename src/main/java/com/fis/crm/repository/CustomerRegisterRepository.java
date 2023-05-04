package com.fis.crm.repository;

import com.fis.crm.domain.CustomerRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRegisterRepository extends JpaRepository<CustomerRegister, Long> {
}
