package com.fis.crm.repository;

import com.fis.crm.domain.ApDomain;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ApDomain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApDomainRepository extends JpaRepository<ApDomain, Long> {
    List<ApDomain> findAllByTypeAndStatus(String type, String status);

    ApDomain findFirstByTypeAndCode(String type, String code);
}
