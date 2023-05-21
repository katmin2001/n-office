package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmProjectMember;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberMDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberPDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CrmProjectMemberService {
    public CrmProjectMemberMDTO getMembersByProjectId(Long projectId);
    public CrmProjectMemberPDTO getProjectsByMemberId(Long memberId);
    public List<CrmProjectMember> addMembersToProject(List<CrmProjectMember> members);
    public List<CrmProjectMember> deleteMembersFromProject(Long projectId, List<Long> memberIds);
}
