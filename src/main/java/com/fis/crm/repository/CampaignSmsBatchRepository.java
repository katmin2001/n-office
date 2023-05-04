package com.fis.crm.repository;

import com.fis.crm.domain.CampaignSmsBatch;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CampaignSmsBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignSmsBatchRepository extends JpaRepository<CampaignSmsBatch, Long> {
    List<CampaignSmsBatch> findByStatus(String status);
}
