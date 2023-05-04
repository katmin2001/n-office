package com.fis.crm.repository;

import com.fis.crm.domain.CampaignTemplateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CampaignTemplateDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignTemplateDetailRepository extends JpaRepository<CampaignTemplateDetail, Long>, CampaignTemplateDetailCustomRepository {
    List<CampaignTemplateDetail> findAllByCampaignIdAndStatus(Long id, String status);

    CampaignTemplateDetail findFirstByCodeAndStatusAndCampaignId(String code, String status, Long campaignId);

    CampaignTemplateDetail findFirstByCodeAndStatusAndCampaignIdAndIdNot(String code, String status, Long campaignId, Long id);
}
