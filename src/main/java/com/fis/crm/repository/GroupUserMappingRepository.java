package com.fis.crm.repository;

import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.GroupUserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupUserMappingRepository extends JpaRepository<GroupUserMapping, Long> {

    @Query(value = "select count(*) from GROUP_USER_MAPPING g where g.group_id = :id", nativeQuery = true)
    Long countUserInGroup(@Param("id") Long id);

    @Query(value = "select count(*) from GROUP_USER g where upper(g.name) = upper(:name)", nativeQuery = true)
    Long countGroupUserByName(@Param("name") String name);

    @Query(value = "select count(*) from campaign_resource_detail d where d.call_status=1 and d.assign_user_id= :id", nativeQuery = true)
    Long countDataNotCall(@Param("id") Long id);

    @Query(value = "select c.ID\n" +
        " from campaign c\n" +
        " where c.status = 1\n" +
        " and c.START_DATE <= sysdate\n" +
        " and c.END_DATE >= sysdate\n" +
        " and instr(',' || c.GROUP_USER || ',', ',' || :groupId || ',') > 0", nativeQuery = true)
    List<Long> getCampaignUseGroup(@Param("groupId") Long groupId);

    GroupUserMapping findByGroupIdAndUserId(Long groupId, Long userId);

    @Query(value = "select c.ID\n" +
        " from CAMPAIGN c\n" +
        " where c.ID in (select campaign_id\n" +
        "                from campaign_resource_detail d\n" +
        "                where d.assign_user_id = :id\n" +
        "                  and call_status = 1)" +
        " and instr(',' || GROUP_USER || ',', ',' || :groupId || ',') > 0"
        , nativeQuery = true)
    List<Long> getCampaignListOfUser(@Param("id") Long id, @Param("groupId") Long groupId);

}
