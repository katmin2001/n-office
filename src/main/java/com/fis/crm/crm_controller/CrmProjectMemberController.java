package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmProjectMember;
import com.fis.crm.crm_entity.CrmProjectMemberP;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberMDTO;
import com.fis.crm.crm_entity.DTO.CrmProjectMemberPDTO;
import com.fis.crm.crm_service.CrmProjectMemberService;
import com.fis.crm.crm_service.CrmProjectService;
import com.fis.crm.crm_service.impl.CrmProjectMemberServiceImpl;
import com.fis.crm.crm_service.impl.CrmProjectServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-member")
public class CrmProjectMemberController {
    private final CrmProjectMemberService crmProjectMemberService;

    public CrmProjectMemberController(CrmProjectMemberServiceImpl crmProjectMemberService) {
        this.crmProjectMemberService = crmProjectMemberService;
    }

    @GetMapping("/member/{projectId}")
    public CrmProjectMemberMDTO getMembersByProjectId(@PathVariable Long projectId) {
        CrmProjectMemberMDTO res = crmProjectMemberService.getMembersByProjectId(projectId);
        return res;
    }

    @GetMapping("/project/{memberId}")
    public CrmProjectMemberPDTO getProjectsByMemberId(@PathVariable Long memberId) {
        CrmProjectMemberPDTO res = crmProjectMemberService.getProjectsByMemberId(memberId);
        return res;
    }

    @PostMapping("/addMembers/")
    public List<CrmProjectMember> addMembersToProject(@RequestBody List<CrmProjectMember> members) {
        return crmProjectMemberService.addMembersToProject(members);
    }

    @DeleteMapping("/deleteMembers/{projectId}")
    public List<CrmProjectMember> deleteMembersFromProject(@PathVariable Long projectId,@RequestBody List<Long> memberIds) {
        return crmProjectMemberService.deleteMembersFromProject(projectId, memberIds);
    }
}
