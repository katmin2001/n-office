package com.fis.crm.service.dto;

import java.util.List;

public class FunctionItemDTO {

    private Long id;
    private Long menuId;
    private Long parentId;
    private Integer orderIndex;
    private String name;
    private List<FunctionItemDTO> children;
    private List<FunctionItemDTO> roles;
    private Long type;
    private boolean checked;
    private Long roleMenuItemId;
    private Integer status;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRoleMenuItemId() {
        return roleMenuItemId;
    }

    public void setRoleMenuItemId(Long roleMenuItemId) {
        this.roleMenuItemId = roleMenuItemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FunctionItemDTO> getChildren() {
        return children;
    }

    public void setChildren(List<FunctionItemDTO> children) {
        this.children = children;
    }

    public List<FunctionItemDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<FunctionItemDTO> roles) {
        this.roles = roles;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
