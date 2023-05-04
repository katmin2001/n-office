package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crmId",
    "reportRegister"
})
public class RequestRegister extends Page {

    @JsonProperty("crmId")
    private String crmId = "1";

    @JsonProperty("reportRegister")
    private ReportRegister reportRegister;

    public RequestRegister(String pageNumber, Integer pageSize, String crmId, ReportRegister reportRegister) {
        super(pageNumber, pageSize);
        this.crmId = "1";
        this.reportRegister = reportRegister;
    }

    public RequestRegister(String crmId, ReportRegister reportRegister) {
        this.crmId = "1";
        this.reportRegister = reportRegister;
    }
    public RequestRegister() {
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public ReportRegister getReportRegister() {
        return reportRegister;
    }

    public void setReportRegister(ReportRegister reportRegister) {
        this.reportRegister = reportRegister;
    }

    @Override
    public String toString() {
        return "RequestRegister{" +
            "crmId='" + crmId + '\'' +
            ", reportRegister=" + reportRegister +
            '}';
    }
}
