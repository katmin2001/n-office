package com.fis.crm.crm_entity.DTO;

public class UpdateNewRoleForUser extends CrmUserRoleDTO{
    private Long newRoleId;

    public UpdateNewRoleForUser() {
    }

    public UpdateNewRoleForUser(Long id, Long userId, Long roleId, Long newRoleId) {
        super(id, userId, roleId);
        this.newRoleId = newRoleId;
    }

    public Long getNewRoleId() {
        return newRoleId;
    }

    public void setNewRoleId(Long newRoleId) {
        this.newRoleId = newRoleId;
    }
}
