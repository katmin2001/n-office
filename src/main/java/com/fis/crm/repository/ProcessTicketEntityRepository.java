package com.fis.crm.repository;

import com.fis.crm.domain.ProcessTicketEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessTicketEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessTicketEntityRepository extends JpaRepository<ProcessTicketEntity, Long> {
}
