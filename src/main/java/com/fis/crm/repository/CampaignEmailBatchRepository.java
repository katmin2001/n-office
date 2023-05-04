package com.fis.crm.repository;

import com.fis.crm.domain.CampaignEmailBatch;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.sql.CallableStatement;
import java.util.List;

public interface CampaignEmailBatchRepository extends JpaRepository<CampaignEmailBatch, Long> {


    List<CampaignEmailBatch> findByStatus(String status);

}
