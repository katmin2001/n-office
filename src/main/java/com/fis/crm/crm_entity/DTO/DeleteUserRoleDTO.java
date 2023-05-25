package com.fis.crm.crm_entity.DTO;

import java.util.Set;

public class DeleteUserRoleDTO {
    private String userName;
    private Set<String> roleName;

    public DeleteUserRoleDTO() {
    }

    public DeleteUserRoleDTO(String userName, Set<String> roleName) {
        this.userName = userName;
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(Set<String> roleName) {
        this.roleName = roleName;
    }
}
