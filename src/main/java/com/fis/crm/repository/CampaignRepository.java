package com.fis.crm.repository;

import com.fis.crm.domain.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Campaign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long>, JpaSpecificationExecutor<Campaign> {

    @Query(value = " SELECT " +
        " campaign.id ," +
        " campaign.NAME ," +
        " campaign.CAMPAIGN_TEMPLATE_ID ," +
        " campaign.CAMPAIGN_SCRIPT_ID ," +
        " campaign.GROUP_USER ," +
        " campaign.DISPLAY_PHONE ," +
        " campaign.START_DATE ," +
        " campaign.END_DATE ," +
        " campaign.STATUS ," +
        " campaign.CREATE_DATETIME ," +
        " campaign.CREATE_USER ," +
        " campaign.UPDATE_DATETIME ," +
        " campaign.UPDATE_USER ," +
        " campaign_template.CAMPAIGN_NAME as campaignTemplateName ," +
        " campaign_script.NAME as campaignScriptName, " +
        " campaign.list_call_status as callStatusLst, " +
        " campaign.null_call_fail " +
        " FROM " +
        " campaign " +
        "  join campaign_script ON campaign_script.ID = campaign.CAMPAIGN_SCRIPT_ID" +
        "  JOIN campaign_template ON campaign_template.ID = campaign.CAMPAIGN_TEMPLATE_ID  where 1=1 " +
        " AND (TO_NUMBER(:CAMPAIGN_SCRIPT_ID,'999') is null or campaign.CAMPAIGN_SCRIPT_ID = TO_NUMBER(:CAMPAIGN_SCRIPT_ID, '999'))  " +
        " AND (TO_NUMBER(:CAMPAIGN_TEMPLATE_ID) is null or campaign.CAMPAIGN_TEMPLATE_ID = TO_NUMBER(:CAMPAIGN_TEMPLATE_ID)) " +
        " AND (:START_DATE is null or campaign.START_DATE >= TO_DATE(:START_DATE, 'yyyy-MM-dd'))  " +
        " AND (:END_DATE is null or campaign.END_DATE <= TO_DATE(:END_DATE, 'yyyy-MM-dd'))  " +
        " AND (:status is null or campaign.status = :status)"+
        " AND (:name is null or lower(campaign.name) like :name) order by campaign.CREATE_DATETIME desc ",
        countQuery = "select count (*)" +
            " FROM " +
            " campaign " +
            "  join campaign_script ON campaign_script.ID = campaign.CAMPAIGN_SCRIPT_ID" +
            "  JOIN campaign_template ON campaign_template.ID = campaign.CAMPAIGN_TEMPLATE_ID  where 1=1 " +
            " AND (TO_NUMBER(:CAMPAIGN_SCRIPT_ID,'999') is null or campaign.CAMPAIGN_SCRIPT_ID = TO_NUMBER(:CAMPAIGN_SCRIPT_ID, '999'))  " +
            " AND (TO_NUMBER(:CAMPAIGN_TEMPLATE_ID) is null or campaign.CAMPAIGN_TEMPLATE_ID = TO_NUMBER(:CAMPAIGN_TEMPLATE_ID)) " +
            " AND (:START_DATE is null or campaign.START_DATE >= TO_DATE(:START_DATE, 'yyyy-MM-dd'))  " +
            " AND (:END_DATE is null or campaign.END_DATE <= TO_DATE(:END_DATE, 'yyyy-MM-dd'))  " +
            " AND (:status is null or campaign.status = :status)"+
            " AND (:name is null or lower(campaign.name) like :name) order by campaign.CREATE_DATETIME desc "
        , nativeQuery = true)
    Page<Object[]> search(
                          @Param("CAMPAIGN_SCRIPT_ID") Number CAMPAIGN_SCRIPT_ID,
                          @Param("CAMPAIGN_TEMPLATE_ID") Number CAMPAIGN_TEMPLATE_ID,
                          @Param("START_DATE") String START_DATE,
                          @Param("END_DATE") String END_DATE,
                          @Param("status") String status,
                          @Param("name") String name,
                          Pageable pageable);

    @Query(value = " SELECT " +
        " os.id ," +
        " os.NAME ," +
        " os.CAMPAIGN_TEMPLATE_ID ," +
        " os.CAMPAIGN_SCRIPT_ID ," +
        " os.GROUP_USER ," +
        " os.DISPLAY_PHONE ," +
        " os.START_DATE ," +
        " os.END_DATE ," +
        " os.STATUS ," +
        " os.CREATE_DATETIME ," +
        " os.CREATE_USER ," +
        " os.UPDATE_DATETIME ," +
        " os.UPDATE_USER ," +
        " uu.CAMPAIGN_NAME as campaignTemplateName ," +
        " ju.NAME as campaignScriptName " +
        " FROM " +
        " campaign os " +
        " LEFT JOIN campaign_script ju ON ju.ID = os.CAMPAIGN_SCRIPT_ID" +
        " LEFT JOIN campaign_template uu ON uu.ID = os.CAMPAIGN_TEMPLATE_ID  order by os.CREATE_DATETIME desc ",
        countQuery = "select 1" +
            " FROM " +
            " campaign os "+
            " LEFT JOIN campaign_script ju ON ju.ID = os.CAMPAIGN_SCRIPT_ID " +
            " LEFT JOIN campaign_template uu ON uu.ID = os.CAMPAIGN_TEMPLATE_ID  " +
            " where 1=1 "
        , nativeQuery = true)
    Page<Object[]> findAllCampaign(Pageable pageable);


    @Query(value = " SELECT " +
        " campaign.id ," +
        " campaign.NAME ," +
        " campaign.CAMPAIGN_TEMPLATE_ID ," +
        " campaign.CAMPAIGN_SCRIPT_ID ," +
        " campaign.GROUP_USER ," +
        " campaign.DISPLAY_PHONE ," +
        " campaign.START_DATE ," +
        " campaign.END_DATE ," +
        " campaign.STATUS ," +
        " campaign.CREATE_DATETIME ," +
        " campaign.CREATE_USER ," +
        " campaign.UPDATE_DATETIME ," +
        " campaign.UPDATE_USER ," +
        " campaign_template.CAMPAIGN_NAME as campaignTemplateName ," +
        " campaign_script.NAME as campaignScriptName " +
        " FROM " +
        " campaign " +
        " JOIN campaign_script ON campaign_script.ID = campaign.CAMPAIGN_SCRIPT_ID" +
        " JOIN campaign_template ON campaign_template.ID = campaign.CAMPAIGN_TEMPLATE_ID  where 1=1 "
        , nativeQuery = true)
    Page<Campaign> findAll(Specification<Campaign> specification, Pageable pageable);


    @Query(value = "select GROUP_USER from CAMPAIGN where ID =:id", nativeQuery = true)
    String listGroup(@Param("id") Long id);

    Long countByName(String name);
}
