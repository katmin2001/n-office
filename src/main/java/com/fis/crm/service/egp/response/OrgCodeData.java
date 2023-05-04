package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgCodeData implements Serializable {

    @JsonProperty("crmId")
    private String crmId;
    @JsonProperty("egpId")
    private String egpId;
    @JsonProperty("infos")
    private List<RecInfo> recInfos = null;

    public OrgCodeData(String crmId, String egpId, List<RecInfo> recInfos) {
        this.crmId = crmId;
        this.egpId = egpId;
        this.recInfos = recInfos;
    }

    public OrgCodeData() {
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

    public List<RecInfo> getInfos() {
        return recInfos;
    }

    public void setInfos(List<RecInfo> recInfos) {
        this.recInfos = recInfos;
    }

    @Override
    public String toString() {
        return "OrgCodeData{" +
            "crmId='" + crmId + '\'' +
            ", egpId='" + egpId + '\'' +
            ", recInfos=" + recInfos +
            '}';
    }
}
