package com.fis.crm.repository;

import com.fis.crm.domain.Ticket;
import com.fis.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select customer_id, name from customer", nativeQuery = true)
    List<Object[]> getListCustomersForCombobox();
}
