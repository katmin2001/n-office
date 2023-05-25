package com.fis.crm.crm_entity.DTO;

import java.util.List;

public class RegisterUserRoleDTO {
    private Long userId;
    private List<Long> roleId;

    public RegisterUserRoleDTO() {
    }

    public RegisterUserRoleDTO(Long userId, List<Long> roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }
}
