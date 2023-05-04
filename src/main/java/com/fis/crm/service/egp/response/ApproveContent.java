package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "orgCode",
    "taxCode",
    "orgFullName",
    "repInfo",
    "status",
    "requestDate",
    "approvedDate",
    "processingTerm",
    "type",
    "requestId",
    "caStatus",
    "caApprovalDate"
})
public class ApproveContent {
    @JsonProperty("id")
    private String id;
    @JsonProperty("orgCode")
    private String orgCode;
    @JsonProperty("taxCode")
    private String taxCode;
    @JsonProperty("orgFullName")
    private String orgFullName;
    @JsonProperty("repInfo")
    private List<RepInfo> repInfo = null;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("requestDate")
    private Date requestDate;
    @JsonProperty("approvedDate")
    private Date approvedDate;
    @JsonProperty("processingTerm")
    private Integer processingTerm;
    @JsonProperty("type")
    private String type;
    @JsonProperty("requestId")
    private Object requestId;
    @JsonProperty("caStatus")
    private Object caStatus;
    @JsonProperty("caApprovalDate")
    private Object caApprovalDate;

    public ApproveContent(String id, String orgCode, String taxCode, String orgFullName, List<RepInfo> repInfo, Integer status, Date requestDate, Date approvedDate, Integer processingTerm, String type, Object requestId, Object caStatus, Object caApprovalDate) {
        this.id = id;
        this.orgCode = orgCode;
        this.taxCode = taxCode;
        this.orgFullName = orgFullName;
        this.repInfo = repInfo;
        this.status = status;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
        this.processingTerm = processingTerm;
        this.type = type;
        this.requestId = requestId;
        this.caStatus = caStatus;
        this.caApprovalDate = caApprovalDate;
    }

    public ApproveContent() {
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

    public List<RepInfo> getRepInfo() {
        return repInfo;
    }

    public void setRepInfo(List<RepInfo> repInfo) {
        this.repInfo = repInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getProcessingTerm() {
        return processingTerm;
    }

    public void setProcessingTerm(Integer processingTerm) {
        this.processingTerm = processingTerm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getRequestId() {
        return requestId;
    }

    public void setRequestId(Object requestId) {
        this.requestId = requestId;
    }

    public Object getCaStatus() {
        return caStatus;
    }

    public void setCaStatus(Object caStatus) {
        this.caStatus = caStatus;
    }

    public Object getCaApprovalDate() {
        return caApprovalDate;
    }

    public void setCaApprovalDate(Object caApprovalDate) {
        this.caApprovalDate = caApprovalDate;
    }
}
