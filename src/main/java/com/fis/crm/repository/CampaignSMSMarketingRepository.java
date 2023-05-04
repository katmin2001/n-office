package com.fis.crm.repository;

import com.fis.crm.domain.CampaignSMSMarketing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignSMSMarketingRepository extends JpaRepository<CampaignSMSMarketing, Long> {

    List<CampaignSMSMarketing> findAllByStatusOrderByNameAsc(String status);

    @Query(value = "select c.id, c.name, c.start_date, c.end_date, c.status, c.title, c.content, c.create_datetime, c.create_user, c.update_datetime, c.update_user \n" +
        "from CAMPAIGN_SMS_MARKETING c\n" +
        "where (:id = -1 or c.id = :id)\n" +
        "and (:hasStartDate = 0 or c.start_date >= to_date(:startDate, 'dd/MM/yyyy')) \n" +
        "and (:hasEndDate = 0 or c.end_date <= to_date(:endDate, 'dd/MM/yyyy')) and (:status = -1 or c.status = :status) \n" +
        "order by c.create_datetime desc",
        countQuery = "select count(c.id) \n" +
            "from CAMPAIGN_SMS_MARKETING c\n" +
            "where (:id = -1 or c.id = :id)\n" +
            "and (:hasFromDate = 0 or c.CREATE_DATETIME >= to_date(:fromDate, 'dd/MM/yyyy')) \n" +
            "and (:hasToDate = 0 or c.CREATE_DATETIME <= to_date(:toDate, 'dd/MM/yyyy')) and (:status = -1 or c.status = :status) \n" +
            "order by c.create_datetime desc",
        nativeQuery = true)
    Page<Object[]> search(@Param("id") Long id, @Param("hasStartDate") Long hasStartDate,
                          @Param("startDate") String startDate, @Param("hasEndDate") Long hasEndDate,
                          @Param("endDate") String endDate, @Param("status") Long status, Pageable pageable);

    @Query(value = "select count(csm) from CampaignSMSMarketing csm where csm.status = '1' and csm.name = :name and (:id = -1 or csm.id <> :id)")
    Long checkExistsCampaignSMSMarketing(@Param("name") String name, @Param("id") Integer id);
}
