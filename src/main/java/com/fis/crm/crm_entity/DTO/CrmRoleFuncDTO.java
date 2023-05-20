package com.fis.crm.crm_entity.DTO;

public class CrmRoleFuncDTO {
    private Long id;
    private Long roleId;
    private Long funcId;

    public CrmRoleFuncDTO() {
    }

    public CrmRoleFuncDTO(Long id, Long roleId, Long funcId) {
        this.id = id;
        this.roleId = roleId;
        this.funcId = funcId;
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

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }
}
