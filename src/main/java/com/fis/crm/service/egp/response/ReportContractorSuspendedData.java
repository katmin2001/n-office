package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crmId",
    "egpId",
    "page",
})
public class ReportContractorSuspendedData {

    @JsonProperty("crmId")
    private String crmId;
    @JsonProperty("egpId")
    private String egpId;

    @JsonProperty("page")
    private PageContractor<ReportContractorSuspendedContent> page;

    public ReportContractorSuspendedData(String crmId, String egpId, PageContractor<ReportContractorSuspendedContent> page) {
        this.crmId = crmId;
        this.egpId = egpId;
        this.page = page;
    }

    public ReportContractorSuspendedData() {
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

    public PageContractor<ReportContractorSuspendedContent> getPage() {
        return page;
    }

    public void setPage(PageContractor<ReportContractorSuspendedContent> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ReportContractorSuspendedData{" +
            "crmId='" + crmId + '\'' +
            ", egpId='" + egpId + '\'' +
            ", page=" + page +
            '}';
    }
}
