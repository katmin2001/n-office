package com.fis.crm.repository;

import com.fis.crm.domain.CampaignTemplateOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CampaignTemplateOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignTemplateOptionRepository extends JpaRepository<CampaignTemplateOption, Long> {
    void deleteAllByCampaignTemplateDetailId(Long id);

    List<CampaignTemplateOption> findAllByCampaignTemplateDetailId(Long id);
}
