package com.fis.crm.repository;

import com.fis.crm.domain.BussinessType;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BussinessType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BussinessTypeRepository extends JpaRepository<BussinessType, Long> {
    Page<BussinessType> findAllByStatus(Long status, Pageable pageable);
}
