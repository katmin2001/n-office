package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CampaignScriptListCbxDTO implements Serializable {

    private Long id;
    private String campaignScriptName;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignScriptName() {
        return campaignScriptName;
    }

    public void setCampaignScriptName(String campaignScriptName) {
        this.campaignScriptName = campaignScriptName;
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
        if (!(o instanceof CampaignScriptListCbxDTO)) {
            return false;
        }

        CampaignScriptListCbxDTO campaignScriptListCbxDTO = (CampaignScriptListCbxDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campaignScriptListCbxDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campaignScriptName, status);
    }

    @Override
    public String toString() {
        return "CampaignScriptListCbxDTO{" +
            "id=" + id +
            ", campaignScriptName='" + campaignScriptName + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
