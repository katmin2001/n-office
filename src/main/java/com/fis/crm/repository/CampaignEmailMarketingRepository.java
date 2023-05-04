package com.fis.crm.repository;

import com.fis.crm.domain.CampaignEmailMarketing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignEmailMarketingRepository extends JpaRepository<CampaignEmailMarketing, Long> {

    List<CampaignEmailMarketing> findAllByStatusOrderByNameAsc(String status);

    @Query(value = "select c.id, c.name, c.start_date, c.end_date, c.status, c.title, c.content, c.create_datetime, c.create_user, c.is_used \n" +
        "from CAMPAIGN_EMAIL_MARKETING c\n" +
        "where (:id = -1 or c.id = :id)\n" +
        "and (:hasFromDate = 0 or c.CREATE_DATETIME >= to_date(:fromDate, 'dd/MM/yyyy hh24:mi:ss')) \n" +
        "and (:hasToDate = 0 or c.CREATE_DATETIME <= to_date(:toDate, 'dd/MM/yyyy hh24:mi:ss')) and (:status = -1 or c.status = :status) \n" +
        "order by c.create_datetime desc",
        countQuery = "select count(c.id) \n" +
            "from CAMPAIGN_EMAIL_MARKETING c\n" +
            "where (:id = -1 or c.id = :id)\n" +
            "and (:hasFromDate = 0 or c.CREATE_DATETIME >= to_date(:fromDate, 'dd/MM/yyyy hh24:mi:ss')) \n" +
            "and (:hasToDate = 0 or c.CREATE_DATETIME <= to_date(:toDate, 'dd/MM/yyyy hh24:mi:ss')) and (:status = -1 or c.status = :status) \n" +
            "order by c.create_datetime desc",
        nativeQuery = true)
    Page<Object[]> search(@Param("id") Long id, @Param("hasFromDate") Long hasFromDate,
                          @Param("fromDate") String fromDate, @Param("hasToDate") Long hasToDate,
                          @Param("toDate") String toDate, @Param("status") Long status, Pageable pageable);

    @Query(value = "select id, name from CAMPAIGN_EMAIL_MARKETING order by NLSSORT(name, 'nls_sort = Vietnamese') ASC", nativeQuery = true)
    List<Object[]> getToComboboxSearch();

    @Query(value = "select id, name from CAMPAIGN_EMAIL_MARKETING where status = '1' order by NLSSORT(name, 'nls_sort = Vietnamese') ASC", nativeQuery = true)
    List<Object[]> getToCombobox();

    @Query(value = "select count(cem) from CampaignEmailMarketing cem where cem.status = '1' and cem.name = :name and (:id = -1 or cem.id <> :id)")
    Long checkExistsCampaignEmailMarketing(@Param("name") String name, @Param("id") Integer id);
}
