package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.CampaignTemplateOption} entity.
 */
public class CampaignTemplateOptionDTO implements Serializable {

  private Long id;

  private Long campaignTemplateId;

  private String code;

  private String name;

  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCampaignTemplateId() {
    return campaignTemplateId;
  }

  public void setCampaignTemplateId(Long campaignTemplateId) {
    this.campaignTemplateId = campaignTemplateId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CampaignTemplateOptionDTO)) {
      return false;
    }

    CampaignTemplateOptionDTO campaignTemplateOptionDTO = (CampaignTemplateOptionDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, campaignTemplateOptionDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CampaignTemplateOptionDTO{" +
            "id=" + getId() +
            ", campaignTemplateId=" + getCampaignTemplateId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
