package com.fis.crm.repository;

import com.fis.crm.domain.CampaignResource;
import com.fis.crm.service.dto.CampaignResourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface CampaignResourceRepository extends JpaRepository<CampaignResource, Long> {

    @Query(value = "select cr.ID, " +
        "cr.CAMPAIGN_ID, " +
        "cr.RESOURCE_NAME, " +
        "cr.TOTAL_NOT_DISTINCT, " +
        "cr.DISTINCT_DATA, " +
        "cr.TOTAL, " +
        "cr.CALLED, " +
        "cr.WATING_CALL, " +
        "cr.WATING_SHARE, " +
        "cr.CREATE_DATETIME, " +
        "cr.CREATE_USER, " +
        "cr.STATUS," +
        "u.LOGIN, cr.CALL_NOT_DONE \n" +
        "from CAMPAIGN_RESOURCE cr left join JHI_USER u on cr.CREATE_USER = u.ID \n" +
        "where (:campaignId = -1 or cr.CAMPAIGN_ID = :campaignId) \n" +
        "and (:hasFromDate = 0 or cr.CREATE_DATETIME >= to_date(:fromDate, 'dd/MM/yyyy')) \n" +
        "and (:hasToDate = 0 or cr.CREATE_DATETIME <= to_date(:toDate, 'dd/MM/yyyy') + 1) \n" +
        "order by cr.CREATE_DATETIME desc",
        countQuery = "select count(cr.ID) \n" +
            "from CAMPAIGN_RESOURCE cr left join JHI_USER u on cr.CREATE_USER = u.ID \n" +
            "where (:campaignId = -1 or cr.CAMPAIGN_ID = :campaignId) \n" +
            "and (:hasFromDate = 0 or cr.CREATE_DATETIME >= to_date(:fromDate, 'dd/MM/yyyy')) \n" +
            "and (:hasToDate = 0 or cr.CREATE_DATETIME <= to_date(:toDate, 'dd/MM/yyyy') + 1) \n" +
            "order by cr.CREATE_DATETIME desc",
        nativeQuery = true)
    Page<Object[]> search(@Param("campaignId") Long campaignId, @Param("hasFromDate") Long hasFromDate,
                          @Param("fromDate") String fromDate, @Param("hasToDate") Long hasToDate,
                          @Param("toDate") String toDate, Pageable pageable);

    List<CampaignResource> findAllByStatusAndCampaignIdOrderByResourceNameAsc(String status, Long campaign_id);

    @Query(value = "select ID, NAME from CAMPAIGN where status = '1'", nativeQuery = true)
    List<Object[]> getLstCampaign();

    @Query(value = "select ID from jhi_user where LOGIN = :userName", nativeQuery = true)
    List<Object[]> getUserLogin(@Param("userName") String userName);

    @Query(value = "select LOGIN from jhi_user where ID = :id", nativeQuery = true)
    Object[] getUserName(@Param("id") Long id);

    List<CampaignResource> findByCampaignId(Long campaignId);

    List<CampaignResource> findAllByStatusOrderByResourceNameAsc(String status);

    @Query(value = "select type,code, name, ord,id,EDITABLE,OPTION_VALUE\n" +
        "from campaign_template_detail\n" +
        "where CAMPAIGN_ID = (select CAMPAIGN_TEMPLATE_ID from campaign c where c.id = :id and rownum=1)\n" +
        " and export_excel=1 and status = 1\n" +
        "order by ord", nativeQuery = true)
    List<Object[]> getDynamicTemplate(@Param("id") Long id);

    List<CampaignResource> findByStatus(String status);

}
