package com.fis.crm.crm_entity.DTO;

import java.util.ArrayList;
import java.util.List;

public class CrmProjectMemberPDTO {
    private Crm_UserDTO member;
    private List<CrmProjectDTO> projects;

    public Crm_UserDTO getMember() {
        return member;
    }

    public void setMember(Crm_UserDTO member) {
        this.member = member;
    }

    public List<CrmProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<CrmProjectDTO> projects) {
        this.projects = projects;
    }

    public CrmProjectMemberPDTO(Crm_UserDTO member, List<CrmProjectDTO> projects) {
        this.member = member;
        this.projects = projects;
    }

    public CrmProjectMemberPDTO() {
        this.member = new Crm_UserDTO();
        this.projects = new ArrayList<>();
    }
}
