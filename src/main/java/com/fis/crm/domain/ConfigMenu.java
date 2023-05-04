package com.fis.crm.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConfigMenu.
 */
@Entity
@Table(name = "config_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigMenu implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIG_MENU_GEN")
  @SequenceGenerator(name = "CONFIG_MENU_GEN", sequenceName = "CONFIG_MENU_ID_SEQ", allocationSize = 1)
  private Long id;

  @Column(name = "menu_code")
  private String menuCode;

  @Column(name = "menu_name")
  private String menuName;

  @Column(name = "domain_code")
  private String domainCode;

  @Column(name = "status")
  private Integer status;

  @Column(name = "description")
  private String description;

  @Column(name = "update_time")
  private Instant updateTime;

  @Column(name = "update_user")
  private String updateUser;

  @Column(name = "order_index")
  private Integer orderIndex;

  @Column(name = "icon")
  private String icon;

  @Column(name = "url")
  private String url;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ConfigMenu id(Long id) {
    this.id = id;
    return this;
  }

  public String getMenuCode() {
    return this.menuCode;
  }

  public ConfigMenu menuCode(String menuCode) {
    this.menuCode = menuCode;
    return this;
  }

  public void setMenuCode(String menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return this.menuName;
  }

  public ConfigMenu menuName(String menuName) {
    this.menuName = menuName;
    return this;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public String getDomainCode() {
    return this.domainCode;
  }

  public ConfigMenu domainCode(String domainCode) {
    this.domainCode = domainCode;
    return this;
  }

  public void setDomainCode(String domainCode) {
    this.domainCode = domainCode;
  }

  public Integer getStatus() {
    return this.status;
  }

  public ConfigMenu status(Integer status) {
    this.status = status;
    return this;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getDescription() {
    return this.description;
  }

  public ConfigMenu description(String description) {
    this.description = description;
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getUpdateTime() {
    return this.updateTime;
  }

  public ConfigMenu updateTime(Instant updateTime) {
    this.updateTime = updateTime;
    return this;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateUser() {
    return this.updateUser;
  }

  public ConfigMenu updateUser(String updateUser) {
    this.updateUser = updateUser;
    return this;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public Integer getOrderIndex() {
    return this.orderIndex;
  }

  public ConfigMenu orderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
    return this;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

  public String getIcon() {
    return this.icon;
  }

  public ConfigMenu icon(String icon) {
    this.icon = icon;
    return this;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getUrl() {
    return this.url;
  }

  public ConfigMenu url(String url) {
    this.url = url;
    return this;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConfigMenu)) {
      return false;
    }
    return id != null && id.equals(((ConfigMenu) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfigMenu{" +
            "id=" + getId() +
            ", menuCode='" + getMenuCode() + "'" +
            ", menuName='" + getMenuName() + "'" +
            ", domainCode='" + getDomainCode() + "'" +
            ", status=" + getStatus() +
            ", description='" + getDescription() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", orderIndex=" + getOrderIndex() +
            ", icon='" + getIcon() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
