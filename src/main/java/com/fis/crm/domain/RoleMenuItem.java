package com.fis.crm.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ROLE_MENU_ITEM")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenuItem implements Serializable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_MENU_ITEM_GEN")
    @SequenceGenerator(name = "ROLE_MENU_ITEM_GEN", sequenceName = "ROLE_MENU_ITEM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_DATETIME")
    private Date createDatetime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "UPDATE_DATETIME")
    private Date updateDatetime;

    @Column(name = "UPDATE_USER")
    private Long updateUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ITEM_ID")
    private ConfigMenuItem configMenuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public ConfigMenuItem getConfigMenuItem() {
        return configMenuItem;
    }

    public void setConfigMenuItem(ConfigMenuItem configMenuItem) {
        this.configMenuItem = configMenuItem;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
