package com.fis.crm.service.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuItemDTO {

    private Long id;
    private Long menuId;
    private Long parentId;
    private Integer orderIndex;
    private String name;
    private String route;
    private String type;
    private String icon;
    private Boolean expanded;
    private List<MenuItemDTO> children;

    private Integer level;

    private List<MenuItemDTO> roles;
    private String code;
    private Long tmp;
    private boolean isCheck;
    private Integer isDefault;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Long getTmp() {
        return tmp;
    }

    public void setTmp(Long tmp) {
        this.tmp = tmp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MenuItemDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<MenuItemDTO> roles) {
        this.roles = roles;
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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public List<MenuItemDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItemDTO> children) {
        this.children = children;
    }

    public void addChild(MenuItemDTO child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
