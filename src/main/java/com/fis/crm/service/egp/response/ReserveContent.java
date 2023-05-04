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
    "requestDate",
    "approvedDate",
    "processingTerm",
    "type",
    "reason",
    "docNo"
})
public class ReserveContent {
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
    @JsonProperty("requestDate")
    private Date requestDate;
    @JsonProperty("approvedDate")
    private Date approvedDate;
    @JsonProperty("processingTerm")
    private Integer processingTerm;
    @JsonProperty("type")
    private String type;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("docNo")
    private String docNo;

    public ReserveContent(String id, String orgCode, String taxCode, String orgFullName, List<RepInfo> repInfo, Date requestDate, Date approvedDate, Integer processingTerm, String type, String reason, String docNo) {
        this.id = id;
        this.orgCode = orgCode;
        this.taxCode = taxCode;
        this.orgFullName = orgFullName;
        this.repInfo = repInfo;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
        this.processingTerm = processingTerm;
        this.type = type;
        this.reason = reason;
        this.docNo = docNo;
    }

    public ReserveContent() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    @Override
    public String toString() {
        return "ReserveContent{" +
            "id='" + id + '\'' +
            ", orgCode='" + orgCode + '\'' +
            ", taxCode='" + taxCode + '\'' +
            ", orgFullName='" + orgFullName + '\'' +
            ", repInfo=" + repInfo +
            ", requestDate=" + requestDate +
            ", approvedDate=" + approvedDate +
            ", processingTerm=" + processingTerm +
            ", type='" + type + '\'' +
            ", reason='" + reason + '\'' +
            ", docNo='" + docNo + '\'' +
            '}';
    }
}
