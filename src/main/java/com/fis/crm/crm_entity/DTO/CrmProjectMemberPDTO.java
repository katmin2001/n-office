package com.fis.crm.crm_entity.DTO;

import java.util.ArrayList;
import java.util.List;

public class CrmProjectMemberPDTO {
    private CrmUserDTO member;
    private List<CrmProjectDTO> projects;

    public CrmUserDTO getMember() {
        return member;
    }

    public void setMember(CrmUserDTO member) {
        this.member = member;
    }

    public List<CrmProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<CrmProjectDTO> projects) {
        this.projects = projects;
    }

    public CrmProjectMemberPDTO(CrmUserDTO member, List<CrmProjectDTO> projects) {
        this.member = member;
        this.projects = projects;
    }

    public CrmProjectMemberPDTO() {
        this.member = new CrmUserDTO();
        this.projects = new ArrayList<>();
    }
}
