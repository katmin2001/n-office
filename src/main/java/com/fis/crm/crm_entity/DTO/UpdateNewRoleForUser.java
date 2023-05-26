package com.fis.crm.crm_entity.DTO;


public class UpdateNewRoleForUser{
    private Long userId;
    private String fullName;
    private String oldRoleName;
    private String newRolename;

    public UpdateNewRoleForUser() {
    }

    public UpdateNewRoleForUser(Long userId, String fullName, String oldRoleName, String newRolename) {
        this.userId = userId;
        this.fullName = fullName;
        this.oldRoleName = oldRoleName;
        this.newRolename = newRolename;
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

    public String getOldRoleName() {
        return oldRoleName;
    }

    public void setOldRoleName(String oldRoleName) {
        this.oldRoleName = oldRoleName;
    }

    public String getNewRolename() {
        return newRolename;
    }

    public void setNewRolename(String newRolename) {
        this.newRolename = newRolename;
    }

}
