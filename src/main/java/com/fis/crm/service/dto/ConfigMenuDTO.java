package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.ConfigMenu} entity.
 */
public class ConfigMenuDTO implements Serializable {

  private Long id;

  private String menuCode;

  private String menuName;

  private String domainCode;

  private Integer status;

  private String description;

  private Instant updateTime;

  private String updateUser;

  private Long orderIndex;

  private String icon;

  private String url;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMenuCode() {
    return menuCode;
  }

  public void setMenuCode(String menuCode) {
    this.menuCode = menuCode;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public String getDomainCode() {
    return domainCode;
  }

  public void setDomainCode(String domainCode) {
    this.domainCode = domainCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Instant updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public Long getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(Long orderIndex) {
    this.orderIndex = orderIndex;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConfigMenuDTO)) {
      return false;
    }

    ConfigMenuDTO configMenuDTO = (ConfigMenuDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, configMenuDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfigMenuDTO{" +
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
