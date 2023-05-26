package com.fis.crm.crm_entity.DTO;

import java.util.Set;

public class CrmRoleFuncDTO {
    private Long id;
    private Long roleId;
    private String roleName;
    private Set<CrmFunctionDTO> functionDTOS;

    public CrmRoleFuncDTO() {
    }

    public CrmRoleFuncDTO(Long id, Long roleId, String roleName, Set<CrmFunctionDTO> functionDTOS) {
        this.id = id;
        this.roleId = roleId;
        this.roleName = roleName;
        this.functionDTOS = functionDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<CrmFunctionDTO> getFunctionDTOS() {
        return functionDTOS;
    }

    public void setFunctionDTOS(Set<CrmFunctionDTO> functionDTOS) {
        this.functionDTOS = functionDTOS;
    }
}
