package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.CrmProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmProjectMemberRepo extends JpaRepository<CrmProjectMember, Long> {
    @Query (value = "select pm from CrmProjectMember pm where pm.projectId = :projectId")
    public List<CrmProjectMember> findAllByProjectId(@Param("projectId") Long projectId);

    @Query (value = "select pm from CrmProjectMember pm where pm.memberId = :memberId")
    public List<CrmProjectMember> findAllByMemberId(@Param("memberId") Long memberId);

    public CrmProjectMember findCrmProjectMembersByProjectIdAndMemberId(Long projectId, Long memberId);

    public Boolean existsByProjectIdAndMemberId(Long projectId, Long memberId);
}
