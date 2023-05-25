package com.fis.crm.crm_entity.DTO;

import java.util.Set;

public class CrmUserRoleDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private Set<CrmRoleDTO> roleDTOS;

    public CrmUserRoleDTO() {
    }

    public CrmUserRoleDTO(Long id, Long userId, String fullName, Set<CrmRoleDTO> roleDTOS) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.roleDTOS = roleDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<CrmRoleDTO> getRoleDTOS() {
        return roleDTOS;
    }

    public void setRoleDTOS(Set<CrmRoleDTO> roleDTOS) {
        this.roleDTOS = roleDTOS;
    }
}
