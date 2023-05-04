package com.fis.crm.service.dto;

import java.util.Objects;

public class CampaignTemplateListDTO {

    private Long id;
    private String campaignTemplateName;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignTemplateName() {
        return campaignTemplateName;
    }

    public void setCampaignTemplateName(String campaignTemplateName) {
        this.campaignTemplateName = campaignTemplateName;
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
        if (!(o instanceof CampaignTemplateListDTO)) {
            return false;
        }

        CampaignTemplateListDTO campaignTemplateListDTO = (CampaignTemplateListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campaignTemplateListDTO.campaignTemplateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campaignTemplateName, status);
    }

    @Override
    public String toString() {
        return "CampaignTemplateListDTO{" +
            "id=" + id +
            ", campaignTemplateName='" + campaignTemplateName + '\'' +
            ", status='" + status + '\'' +
            '}';
    }

}
