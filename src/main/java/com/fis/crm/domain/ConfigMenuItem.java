package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A ConfigMenuItem.
 */
@Entity
@Table(name = "config_menu_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigMenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIG_MENU_ITEM_GEN")
    @SequenceGenerator(name = "CONFIG_MENU_ITEM_GEN", sequenceName = "CONFIG_MENU_ITEM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_item_code")
    private String menuItemCode;

    @Column(name = "menu_item_name")
    private String menuItemName;

    @Column(name = "is_default")
    private Integer isDefault;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "status")
    private Integer status;

    @Column(name = "description")
    private String description;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "icon")
    private String icon;

    @Column(name = "url")
    private String url;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "type")
    private Long type;

    @OneToMany(mappedBy = "configMenuItem")
    List<RoleMenuItem> roleMenuItemList;

    public ConfigMenuItem(Long id) {
        this.id = id;
    }

    public ConfigMenuItem() {
    }

    public ConfigMenuItem(Long id, Long menuId, String menuItemCode, String menuItemName, Integer isDefault, Integer orderIndex, Integer status, String description, Date updateTime, String updateUser, String icon, String url, Long parentId, Long type, List<RoleMenuItem> roleMenuItemList) {
        this.id = id;
        this.menuId = menuId;
        this.menuItemCode = menuItemCode;
        this.menuItemName = menuItemName;
        this.isDefault = isDefault;
        this.orderIndex = orderIndex;
        this.status = status;
        this.description = description;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.icon = icon;
        this.url = url;
        this.parentId = parentId;
        this.type = type;
        this.roleMenuItemList = roleMenuItemList;
    }

    public List<RoleMenuItem> getRoleMenuItemList() {
        return roleMenuItemList;
    }

    public void setRoleMenuItemList(List<RoleMenuItem> roleMenuItemList) {
        this.roleMenuItemList = roleMenuItemList;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConfigMenuItem id(Long id) {
        this.id = id;
        return this;
    }

    public Long getMenuId() {
        return this.menuId;
    }

    public ConfigMenuItem menuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuItemCode() {
        return this.menuItemCode;
    }

    public ConfigMenuItem menuItemCode(String menuItemCode) {
        this.menuItemCode = menuItemCode;
        return this;
    }

    public void setMenuItemCode(String menuItemCode) {
        this.menuItemCode = menuItemCode;
    }

    public String getMenuItemName() {
        return this.menuItemName;
    }

    public ConfigMenuItem menuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
        return this;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public Integer getIsDefault() {
        return this.isDefault;
    }

    public ConfigMenuItem isDefault(Integer isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public ConfigMenuItem orderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getStatus() {
        return this.status;
    }

    public ConfigMenuItem status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public ConfigMenuItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public ConfigMenuItem updateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public ConfigMenuItem updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getIcon() {
        return this.icon;
    }

    public ConfigMenuItem icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return this.url;
    }

    public ConfigMenuItem url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public ConfigMenuItem parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigMenuItem)) {
            return false;
        }
        return id != null && id.equals(((ConfigMenuItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigMenuItem{" +
            "id=" + getId() +
            ", menuId=" + getMenuId() +
            ", menuItemCode='" + getMenuItemCode() + "'" +
            ", menuItemName='" + getMenuItemName() + "'" +
            ", isDefault=" + getIsDefault() +
            ", orderIndex=" + getOrderIndex() +
            ", status=" + getStatus() +
            ", description='" + getDescription() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            ", parentId=" + getParentId() +
            "}";
    }
}
