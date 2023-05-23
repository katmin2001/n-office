package com.fis.crm.crm_entity.DTO;


import java.util.ArrayList;
import java.util.List;

public class CrmProjectMemberMDTO {
    private CrmProjectDTO project;
    private List<Crm_UserDTO> members;

    public CrmProjectDTO getProject() {
        return project;
    }

    public void setProject(CrmProjectDTO project) {
        this.project = project;
    }

    public List<Crm_UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<Crm_UserDTO> members) {
        this.members = members;
    }

    public CrmProjectMemberMDTO(CrmProjectDTO project, List<Crm_UserDTO> members) {
        this.project = project;
        this.members = members;
    }

    public CrmProjectMemberMDTO() {
        this.project = new CrmProjectDTO();
        this.members = new ArrayList<>();
    }
}
