package com.fis.crm.crm_entity.DTO;

public class CrmRoleFuncDTO {
    private Long id;
    private Long roleId;
    private Long funcId;
    private String roleName;
    private String funcName;

    public CrmRoleFuncDTO() {
    }

    public CrmRoleFuncDTO(Long id, Long roleId, String roleName,  Long funcId,  String funcName) {
        this.id = id;
        this.roleId = roleId;
        this.funcId = funcId;
        this.roleName = roleName;
        this.funcName = funcName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }
}
