package com.fis.crm.crm_entity.DTO;

public class CrmRoleDTO {
    private Long roleId;
    private String roleName;

    public CrmRoleDTO() {
    }

    public CrmRoleDTO(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
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
}
