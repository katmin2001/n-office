package com.fis.crm.crm_entity.DTO;

public class CrmUserRoleDTO {
    private Long id;
    private Long userId;
    private Long roleId;
    private String fullName;
    private String roleName;

    public CrmUserRoleDTO() {
    }

    public CrmUserRoleDTO(Long id, Long userId, Long roleId, String fullName, String roleName) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.fullName = fullName;
        this.roleName = roleName;
    }

    public CrmUserRoleDTO(Long id, Long userId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
