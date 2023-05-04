package com.fis.crm.repository;

import com.fis.crm.domain.CampaignBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignBlackListRepository extends JpaRepository<CampaignBlacklist,Long> {

    List<CampaignBlacklist> findByCampaignId(Long campaignId);

    @Query(value = "SELECT c.phone_number from CAMPAIGN_BLACKLIST c where :phoneNumber = c.PHONE_NUMBER and :campaignId = c.CAMPAIGN_ID "
    ,nativeQuery = true)
    String phoneNumber(@Param("phoneNumber") String phoneNumber, @Param("campaignId") Long campaignId);
}
