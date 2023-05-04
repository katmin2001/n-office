package com.fis.crm.repository;

import com.fis.crm.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

    @Query("select gu from GroupUser gu where gu.id in (select distinct crd.groupId from CampaignResourceDetail crd where crd.campaignId = :campaignId and crd.groupId is not null)")
    Optional<List<GroupUser>> getAllGroupUsersByCampaignId(@Param("campaignId") Long campaignId);

    @Query(value = "select * from GROUP_USER where ID =:id", nativeQuery = true)
    GroupUser getGroupUser(@Param("id") Long id);


}
