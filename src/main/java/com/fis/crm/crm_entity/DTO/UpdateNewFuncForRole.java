package com.fis.crm.crm_entity.DTO;

import java.util.Set;

public class UpdateNewFuncForRole extends CrmRoleFuncDTO{
    private Set<CrmFunctionDTO> newFuncName;

    public UpdateNewFuncForRole() {
    }

    public UpdateNewFuncForRole(Long id, Long roleId, String roleName, Set<CrmFunctionDTO> functionDTOS, Set<CrmFunctionDTO> newFuncName) {
        super(id, roleId, roleName, functionDTOS);
        this.newFuncName = newFuncName;
    }

    public Set<CrmFunctionDTO> getNewFuncName() {
        return newFuncName;
    }

    public void setNewFuncName(Set<CrmFunctionDTO> newFuncName) {
        this.newFuncName = newFuncName;
    }
}
