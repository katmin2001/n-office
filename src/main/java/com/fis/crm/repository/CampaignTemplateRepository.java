package com.fis.crm.repository;

import com.fis.crm.domain.CampaignTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CampaignTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignTemplateRepository extends JpaRepository<CampaignTemplate, Long>, CampaignTemplateCustomRepository {
    @Query(value = "select id, CAMPAIGN_NAME as campaignTemplateName from campaign_template where status ='1' ", nativeQuery = true)
    List<Object[]> getListCampaignTemplateForCombobox();
}
