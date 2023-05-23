package com.fis.crm.crm_entity.DTO;

public class UpdateNewFuncForRole extends CrmRoleFuncDTO{
    private Long newFuncId;

    public UpdateNewFuncForRole() {
    }

    public UpdateNewFuncForRole(Long id, Long roleId, Long funcId, Long newFuncId) {
        super(id, roleId, funcId);
        this.newFuncId = newFuncId;
    }

    public Long getNewFuncId() {
        return newFuncId;
    }

    public void setNewFuncId(Long newFuncId) {
        this.newFuncId = newFuncId;
    }
}
