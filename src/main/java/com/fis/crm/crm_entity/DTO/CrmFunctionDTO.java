package com.fis.crm.crm_entity.DTO;

public class CrmFunctionDTO {
    private Long funcId;
    private String funcName;

    public CrmFunctionDTO() {
    }

    public CrmFunctionDTO(Long funcId, String funcName) {
        this.funcId = funcId;
        this.funcName = funcName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }
}
