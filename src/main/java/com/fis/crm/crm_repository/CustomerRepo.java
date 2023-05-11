package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
//    @Query(value = "select * from crm_customer", nativeQuery = true)
//    List<Customer[]> getListCustomer();
}
