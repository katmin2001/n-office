package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.ConfigMenuItem} entity.
 */
public class ConfigMenuItemAddDTO implements Serializable {

    private Long menuId;

    private Long roleId;

    private List<FunctionItemDTO> functionItemDTOS;

    public ConfigMenuItemAddDTO(Long menuId, Long roleId, List<FunctionItemDTO> functionItemDTOS) {
        this.menuId = menuId;
        this.roleId = roleId;
        this.functionItemDTOS = functionItemDTOS;
    }

    public ConfigMenuItemAddDTO() {
    }

    public List<FunctionItemDTO> getFunctionItemDTOS() {
        return functionItemDTOS;
    }

    public void setFunctionItemDTOS(List<FunctionItemDTO> functionItemDTOS) {
        this.functionItemDTOS = functionItemDTOS;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}
