package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fis.crm.service.egp.response.CaInfo;
import com.fis.crm.service.egp.response.FeeDebtInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgInfo {

    @JsonProperty("orgCode")
    private String orgCode;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("orgFullName")
    private String orgFullName;
    @JsonProperty("officePro")
    private String officePro;
    @JsonProperty("officeDis")
    private String officeDis;
    @JsonProperty("officeWar")
    private String officeWar;
    @JsonProperty("officeAdd")
    private String officeAdd;
    @JsonProperty("feeDebtInfos")
    private List<FeeDebtInfo> feeDebtInfos = null;
    @JsonProperty("caInfos")
    private List<CaInfo> caInfos = null;

    private List<Area> areas;

    public OrgInfo(String orgCode, String taxCode, String orgFullName, String officePro, String officeDis, String officeWar, String officeAdd, List<FeeDebtInfo> feeDebtInfos, List<CaInfo> caInfos) {
        this.orgCode = orgCode;
        this.taxCode = taxCode;
        this.orgFullName = orgFullName;
        this.officePro = officePro;
        this.officeDis = officeDis;
        this.officeWar = officeWar;
        this.officeAdd = officeAdd;
        this.feeDebtInfos = feeDebtInfos;
        this.caInfos = caInfos;
    }

    public OrgInfo() {
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getOrgFullName() {
        return orgFullName;
    }

    public void setOrgFullName(String orgFullName) {
        this.orgFullName = orgFullName;
    }

    public String getOfficePro() {
        return officePro;
    }

    public void setOfficePro(String officePro) {
        this.officePro = officePro;
    }

    public String getOfficeDis() {
        return officeDis;
    }

    public void setOfficeDis(String officeDis) {
        this.officeDis = officeDis;
    }

    public String getOfficeWar() {
        return officeWar;
    }

    public void setOfficeWar(String officeWar) {
        this.officeWar = officeWar;
    }

    public String getOfficeAdd() {
        return officeAdd;
    }

    public void setOfficeAdd(String officeAdd) {
        this.officeAdd = officeAdd;
    }

    public List<FeeDebtInfo> getFeeDebtInfos() {
        return feeDebtInfos;
    }

    public void setFeeDebtInfos(List<FeeDebtInfo> feeDebtInfos) {
        this.feeDebtInfos = feeDebtInfos;
    }

    public List<CaInfo> getCaInfos() {
        return caInfos;
    }

    public void setCaInfos(List<CaInfo> caInfos) {
        this.caInfos = caInfos;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
