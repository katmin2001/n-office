package com.fis.crm.repository;

import com.fis.crm.domain.CampaignResourceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CampaignResourceDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignResourceDetailRepository extends JpaRepository<CampaignResourceDetail, Long> {

    List<CampaignResourceDetail> findByCampaignResourceId(Long campaignResourceId);

    Optional<Long> countAllByCampaignResourceIdAndGroupIdIsNull(Long campaignResourceId);

    Page<CampaignResourceDetail> findByCampaignResourceIdAndGroupIdIsNull(Long campaignResourceId, Pageable pageable);

    Page<CampaignResourceDetail> findByCampaignResourceIdAndErrorStatus(Long campaignResourceId, String errorStatus, Pageable pageable);

    Optional<Long> countAllByCampaignIdAndGroupIdAndAssignUserIdIsNull(Long campaignId, Long groupId);

    Page<CampaignResourceDetail> findByCampaignIdAndGroupIdAndAssignUserIdIsNull(Long campaignId, Long groupId, Pageable pageable);


    @Query(value = " select GROUP_ID, (select gu.name from GROUP_USER gu where gu.ID = GROUP_ID )  as GROUP_NAME,\n" +
        "        count(1)\n" +
        "        ,SUM(case when ASSIGN_USER_ID is null then 1 else 0 end) AS YET_ASSIGN\n" +
        "        ,SUM(case when ASSIGN_USER_ID is not null then 1 else 0 end) AS ASSIGNED\n" +
        "        ,SUM(case when CALL_STATUS = '2' then 1 else 0 end) AS CALLED\n" +
        "        ,SUM(case when CALL_STATUS = '1' or CALL_STATUS is null then 1 else 0 end) AS YET_CALL\n" +
        "        ,SUM(case when ERROR_STATUS = '1' then 1 else 0 end) AS NOT_DONE_CALL\n" +
        "       from CAMPAIGN_RESOURCE_DETAIL crd\n" +
        "where GROUP_ID is not null\n" +
        "and ((:campaignId =-1) or (campaign_id = :campaignId))\n" +
        "group by GROUP_ID"
        ,nativeQuery = true)
    List<Object[]> getCampaignGroupDetails(@Param("campaignId") Long campaignId);

    @Query(value = " select crd.ASSIGN_USER_ID, ju.LOGIN  as USER_NAME,\n" +
        "    count(1)\n" +
        "     ,SUM(case when crd.CALL_STATUS = '2' then 1 else 0 end) AS CALLED\n" +
        "     ,SUM(case when crd.CALL_STATUS = '1' then 1 else 0 end) AS YET_CALLED\n" +
        "     ,SUM(case when ERROR_STATUS = '1' then 1 else 0 end) AS NOT_DONE_CALL\n" +
        "from CAMPAIGN_RESOURCE_DETAIL crd, JHI_USER ju " +
        "where crd.ASSIGN_USER_ID = ju.ID " +
        "AND crd.ASSIGN_USER_ID is not null " +
        "AND crd.GROUP_ID = :groupId " +
        "AND crd.CAMPAIGN_ID = :campaignId  " +
        "AND (:search = '-1' or  lower(ju.LOGIN) like :search escape '\\') " +
        "group by crd.ASSIGN_USER_ID,  ju.LOGIN"
        ,nativeQuery = true)
    List<Object[]> getCampaignUserDetails(@Param("campaignId") Long campaignId, @Param("groupId") Long groupId, @Param("search") String search);

    Page<CampaignResourceDetail> findByCampaignIdAndGroupIdAndAssignUserIdAndCallStatus(Long campaignId, Long groupId, Long assignUserId, String callStatus, Pageable pageable);

    Page<CampaignResourceDetail> findByCampaignIdAndGroupIdAndAssignUserIdAndErrorStatus(Long campaignId, Long groupId, Long assignUserId, String errorStatus, Pageable pageable);

    Page<CampaignResourceDetail> findByGroupIdAndCallStatus(Long groupId, String callStatus, Pageable pageable);

    Page<CampaignResourceDetail> findByGroupIdAndErrorStatus(Long groupId, String errorStatus, Pageable pageable);

    Optional<Long> countAllByCampaignResourceIdAndGroupIdAndAssignUserIdAndCallStatus(Long campaignResourceId, Long groupId, Long assignUserId, String callStatus);

    Optional<Long> countAllByGroupIdAndCallStatus(Long groupId, String callStatus);

    CampaignResourceDetail findByCID(String CID);

    @Modifying
    @Query(value = "update campaign_resource_detail a set a.evaluate_status=:status where a.id = to_number(:id)", nativeQuery = true)
    int updateEvaluateStatus(@Param("id") Long id,@Param("status") String status);

    @Modifying
    @Query(value = "update campaign_resource_detail a set a.assign_user_id=null where a.id = to_number(:id)", nativeQuery = true)
    int resetAssignUser(@Param("id") Long id);

    @Modifying
    @Query(value = "update campaign_resource_detail a set a.group_id=null,a.assign_user_id=null where a.id = to_number(:id)", nativeQuery = true)
    int resetAssignUserAndGroup(@Param("id") Long id);

}
