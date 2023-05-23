package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmProject;
import com.fis.crm.crm_entity.CrmProjectMember;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberMDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberPDTO;
import com.fis.crm.crm_repository.CrmProjectMemberRepo;
import com.fis.crm.crm_service.CrmProjectMemberService;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_util.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrmProjectMemberServiceImpl implements CrmProjectMemberService {
    private final CrmProjectMemberRepo projectMemberRepo;
    private final CrmProjectService projectService;
    private final CrmUserServiceImpl userService;
    private final DtoMapper dtoMapper = new DtoMapper();

    public CrmProjectMemberServiceImpl(CrmProjectMemberRepo projectMemberRepo, CrmProjectServiceImpl projectService, CrmUserServiceImpl userService) {
        this.projectMemberRepo = projectMemberRepo;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public CrmProjectMemberMDTO getMembersByProjectId(Long projectId) {
        List<CrmProjectMember> projectMembers = projectMemberRepo.findAllByProjectId(projectId);
        CrmProjectMemberMDTO result = new CrmProjectMemberMDTO();
        for (CrmProjectMember projectMember : projectMembers) {
            result.getMembers().add(dtoMapper.userDtoMapper(userService.findByCrmUserId(projectMember.getMemberId()).get()));
        }
        result.setProject(dtoMapper.projectDTOMapper(projectService.getProjectById(projectId).get()));
        return result;
    }

    @Override
    public CrmProjectMemberPDTO getProjectsByMemberId(Long memberId) {
        List<CrmProjectMember> projectMembers = projectMemberRepo.findAllByMemberId(memberId);
        CrmProjectMemberPDTO result = new CrmProjectMemberPDTO();
        for (CrmProjectMember projectMember : projectMembers) {
            result.getProjects().add(dtoMapper.projectDTOMapper(projectService.getProjectById(projectMember.getProjectId()).get()));
        }
        result.setMember(dtoMapper.userDtoMapper(userService.findByCrmUserId(memberId).get()));
        return result;
    }

    @Override
    public List<CrmProjectMember> addMembersToProject(List<CrmProjectMember> members) {
        List<CrmProjectMember> newMembers = new ArrayList<>();
        for (CrmProjectMember member : members) {
            Long projectId = member.getProjectId();
            Long memberId = member.getMemberId();
            boolean exists = projectMemberRepo.existsByProjectIdAndMemberId(projectId, memberId);
            if (exists) {
                return new ArrayList<>();
            }
        }
        return projectMemberRepo.saveAll(members);
    }

    @Override
    public List<CrmProjectMember> deleteMembersFromProject(Long projectId, List<Long> memberIds) {
        List<CrmProjectMember> deletedMembers = new ArrayList<>();

        for (Long memberId : memberIds) {
            CrmProjectMember member = projectMemberRepo.findCrmProjectMembersByProjectIdAndMemberId(projectId, memberId);

            if (member != null) {
                projectMemberRepo.delete(member);
                deletedMembers.add(member);
            }
        }

        return deletedMembers;
    }
}
