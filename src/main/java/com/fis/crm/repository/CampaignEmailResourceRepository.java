package com.fis.crm.repository;

import com.fis.crm.domain.CampaignEmailResource;
import com.fis.crm.domain.CampaignSMSResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignEmailResourceRepository extends JpaRepository<CampaignEmailResource, Long> {

    List<CampaignEmailResource> findByCampaignEmailMarketingId(Long campaignEmailMarketingId);

    List<CampaignEmailResource> findByCampaignEmailMarketingIdAndSendStatusNotLike(Long campaignEmailMarketingId, String status);

    List<CampaignEmailResource> findBySendStatusNotLike(String status);

    @Query(value = "select c.ID, " +
        "c.CAMPAIGN_EMAIL_MARKETING_ID, " +
        "c.EMAIL, " +
        "c.C1, " +
        "c.C2, " +
        "c.C3, " +
        "c.C4, " +
        "c.C5, " +
        "c.C6, " +
        "c.C7, " +
        "c.C8, " +
        "c.C9, " +
        "c.C10, " +
        "c.SEND_STATUS, " +
        "c.SEND_DATE, " +
        "c.SEND_USER_ID, " +
        "c.CREATE_DATETIME " +
        "from CAMPAIGN_EMAIL_RESOURCE c \n" +
        "where (:campaignEmailMarketingId = -1 or c.CAMPAIGN_EMAIL_MARKETING_ID = :campaignEmailMarketingId) \n" +
        "and (:status = '-1' or c.SEND_STATUS = :status) \n" +
        "and (:hasFromDate = 0 or c.SEND_DATE >= to_date(:fromDate, 'dd/MM/yyyy')) \n" +
        "and (:hasToDate = 0 or c.SEND_DATE <= to_date(:toDate, 'dd/MM/yyyy')) and c.SEND_STATUS <> '0' \n" +
        "order by c.CREATE_DATETIME desc",
        countQuery = "select count(c.ID) \n" +
            "from CAMPAIGN_EMAIL_RESOURCE c \n" +
            "where (:campaignEmailMarketingId = -1 or c.CAMPAIGN_EMAIL_MARKETING_ID = :campaignEmailMarketingId) \n" +
            "and (:status = '-1' or c.SEND_STATUS = :status) \n" +
            "and (:hasFromDate = 0 or c.SEND_DATE >= to_date(:fromDate, 'dd/MM/yyyy')) \n" +
            "and (:hasToDate = 0 or c.SEND_DATE <= to_date(:toDate, 'dd/MM/yyyy')) and c.SEND_STATUS <> '0' \n" +
            "order by c.CREATE_DATETIME desc",
        nativeQuery = true)
    Page<Object[]> search(@Param("campaignEmailMarketingId") Long campaignEmailMarketingId,
                          @Param("status") String status,
                          @Param("fromDate") String fromDate,
                          @Param("toDate") String toDate,
                          @Param("hasFromDate") Long hasFromDate,
                          @Param("hasToDate") Long hasToDate,
                          Pageable pageable);
}
