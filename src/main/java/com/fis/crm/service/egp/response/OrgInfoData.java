package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgInfoData implements Serializable {

    @JsonProperty("crmId")
    private String crmId;
    @JsonProperty("egpId")
    private String egpId;
    @JsonProperty("orgInfo")
    private OrgInfo orgInfo = null;

    public OrgInfoData(String crmId, String egpId, OrgInfo orgInfo) {
        this.crmId = crmId;
        this.egpId = egpId;
        this.orgInfo = orgInfo;
    }

    public OrgInfoData() {
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getEgpId() {
        return egpId;
    }

    public void setEgpId(String egpId) {
        this.egpId = egpId;
    }

    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(OrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    @Override
    public String toString() {
        return "OrgInfoData{" +
            "crmId='" + crmId + '\'' +
            ", egpId='" + egpId + '\'' +
            ", orgInfo=" + orgInfo +
            '}';
    }
}
