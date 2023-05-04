package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "orgCode",
    "taxCode",
    "orgFullName",
    "orgEnName",
    "officePro",
    "reasonType",
    "createdDate",
    "businessStatus",
    "taxCodeStatus",
    "reason"
})
public class ReportContractorSuspendedContent {

    @JsonProperty("id")
    private String id;
    @JsonProperty("orgCode")
    private String orgCode;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("orgFullName")
    private String orgFullName;
    @JsonProperty("orgEnName")
    private String orgEnName;
    @JsonProperty("officePro")
    private String officePro;
    @JsonProperty("reasonType")
    private String reasonType;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("businessStatus")
    private String businessStatus;
    @JsonProperty("taxCodeStatus")
    private Object taxCodeStatus;
    @JsonProperty("reason")
    private String reason;

    public ReportContractorSuspendedContent(String id, String orgCode, String taxCode, String orgFullName, String orgEnName, String officePro, String reasonType, String createdDate, String businessStatus, Object taxCodeStatus, String reason) {
        this.id = id;
        this.orgCode = orgCode;
        this.taxCode = taxCode;
        this.orgFullName = orgFullName;
        this.orgEnName = orgEnName;
        this.officePro = officePro;
        this.reasonType = reasonType;
        this.createdDate = createdDate;
        this.businessStatus = businessStatus;
        this.taxCodeStatus = taxCodeStatus;
        this.reason = reason;
    }

    public ReportContractorSuspendedContent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrgEnName() {
        return orgEnName;
    }

    public void setOrgEnName(String orgEnName) {
        this.orgEnName = orgEnName;
    }

    public String getOfficePro() {
        return officePro;
    }

    public void setOfficePro(String officePro) {
        this.officePro = officePro;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Object getTaxCodeStatus() {
        return taxCodeStatus;
    }

    public void setTaxCodeStatus(Object taxCodeStatus) {
        this.taxCodeStatus = taxCodeStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
