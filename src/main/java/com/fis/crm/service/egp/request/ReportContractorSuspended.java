package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crmId",
    "contractorSuspended"
})
public class ReportContractorSuspended extends Page {

    @JsonProperty("crmId")
    private String crmId = "1";

    @JsonProperty("contractorSuspended")
    ContractorSuspended contractorSuspended;

    public ReportContractorSuspended(String pageNumber, Integer pageSize, String crmId, ContractorSuspended contractorSuspended) {
        super(pageNumber, pageSize);
        this.crmId = "1";
        this.contractorSuspended = contractorSuspended;
    }

    public ReportContractorSuspended(String crmId, ContractorSuspended contractorSuspended) {
        this.crmId = "1";
        this.contractorSuspended = contractorSuspended;
    }
    public ReportContractorSuspended() {
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public ContractorSuspended getContractorSuspended() {
        return contractorSuspended;
    }

    public void setContractorSuspended(ContractorSuspended contractorSuspended) {
        this.contractorSuspended = contractorSuspended;
    }

    @Override
    public String toString() {
        return "ReportContractorSuspended{" +
            "crmId='" + crmId + '\'' +
            ", contractorSuspended=" + contractorSuspended +
            '}';
    }
}
