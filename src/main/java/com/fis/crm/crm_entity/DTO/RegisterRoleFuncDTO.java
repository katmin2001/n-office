package com.fis.crm.crm_entity.DTO;

import java.util.List;

public class RegisterRoleFuncDTO {
    private String roleName;
    private List<String> funcName;

    public RegisterRoleFuncDTO() {
    }

    public RegisterRoleFuncDTO(String roleName, List<String> funcName) {
        this.roleName = roleName;
        this.funcName = funcName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getFuncName() {
        return funcName;
    }

    public void setFuncName(List<String> funcName) {
        this.funcName = funcName;
    }
}
