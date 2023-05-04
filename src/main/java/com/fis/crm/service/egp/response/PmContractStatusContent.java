package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "role",
    "taxCode",
    "repEmail",
    "status",
    "officePro",
    "officeDis",
    "officeWar",
    "officeAdd",
    "feePendingDate",
    "lsFeeType"
})
public class PmContractStatusContent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("role")
    private Object role;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("repEmail")
    private String repEmail;
    @JsonProperty("status")
    private String status;
    @JsonProperty("officePro")
    private Object officePro;
    @JsonProperty("officeDis")
    private Object officeDis;
    @JsonProperty("officeWar")
    private Object officeWar;
    @JsonProperty("officeAdd")
    private Object officeAdd;
    @JsonProperty("feePendingDate")
    private String feePendingDate;
    @JsonProperty("lsFeeType")
    private List<String> lsFeeType = null;
    @JsonProperty("debtFee")
    private String debtFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRepEmail() {
        return repEmail;
    }

    public void setRepEmail(String repEmail) {
        this.repEmail = repEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getOfficePro() {
        return officePro;
    }

    public void setOfficePro(Object officePro) {
        this.officePro = officePro;
    }

    public Object getOfficeDis() {
        return officeDis;
    }

    public void setOfficeDis(Object officeDis) {
        this.officeDis = officeDis;
    }

    public Object getOfficeWar() {
        return officeWar;
    }

    public void setOfficeWar(Object officeWar) {
        this.officeWar = officeWar;
    }

    public Object getOfficeAdd() {
        return officeAdd;
    }

    public void setOfficeAdd(Object officeAdd) {
        this.officeAdd = officeAdd;
    }

    public String getFeePendingDate() {
        return feePendingDate;
    }

    public void setFeePendingDate(String feePendingDate) {
        this.feePendingDate = feePendingDate;
    }

    public List<String> getLsFeeType() {
        return lsFeeType;
    }

    public void setLsFeeType(List<String> lsFeeType) {
        this.lsFeeType = lsFeeType;
    }

    public String getDebtFee() {
        return debtFee;
    }

    public void setDebtFee(String debtFee) {
        this.debtFee = debtFee;
    }

    public PmContractStatusContent(String id, String code, String name, Object role, String taxCode, String repEmail, String status, Object officePro, Object officeDis, Object officeWar, Object officeAdd, String feePendingDate, List<String> lsFeeType, String debtFee) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.role = role;
        this.taxCode = taxCode;
        this.repEmail = repEmail;
        this.status = status;
        this.officePro = officePro;
        this.officeDis = officeDis;
        this.officeWar = officeWar;
        this.officeAdd = officeAdd;
        this.feePendingDate = feePendingDate;
        this.lsFeeType = lsFeeType;
        this.debtFee = debtFee;
    }

    public PmContractStatusContent() {
    }

    @Override
    public String toString() {
        return "PmContractStatusContent{" +
            "id='" + id + '\'' +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", role=" + role +
            ", taxCode='" + taxCode + '\'' +
            ", repEmail='" + repEmail + '\'' +
            ", status='" + status + '\'' +
            ", officePro=" + officePro +
            ", officeDis=" + officeDis +
            ", officeWar=" + officeWar +
            ", officeAdd=" + officeAdd +
            ", feePendingDate='" + feePendingDate + '\'' +
            ", lsFeeType=" + lsFeeType +
            ", debtFee='" + debtFee + '\'' +
            '}';
    }
}
