package com.fis.crm.service.dto;

import com.fis.crm.domain.Role;
import com.fis.crm.domain.User;

import java.util.Date;
import java.util.List;

public class UserRoleDTO {

    private Long id;
    private User user;
    private Role role;
    private Long roleId;
    private String status;
    private String search;
    private Long createUser;
    private Date createDatetime;
    private Long updateUser;
    private Date updateDatetime;
    List<Long> lstUserId;
    List<Long> lstUserRoleId;

    public List<Long> getLstUserRoleId() {
        return lstUserRoleId;
    }

    public void setLstUserRoleId(List<Long> lstUserRoleId) {
        this.lstUserRoleId = lstUserRoleId;
    }

    public List<Long> getLstUserId() {
        return lstUserId;
    }

    public void setLstUserId(List<Long> lstUserId) {
        this.lstUserId = lstUserId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
