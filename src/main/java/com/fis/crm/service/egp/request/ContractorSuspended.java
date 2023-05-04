package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "createdDate",
})
public class ContractorSuspended {

    @JsonProperty("createdDate")
    private FeePendingDate feePendingDate;

    public ContractorSuspended(FeePendingDate feePendingDate) {
        this.feePendingDate = feePendingDate;
    }

    public ContractorSuspended() {
    }

    public FeePendingDate getFeePendingDate() {
        return feePendingDate;
    }

    public void setFeePendingDate(FeePendingDate feePendingDate) {
        this.feePendingDate = feePendingDate;
    }

    @Override
    public String toString() {
        return "ContractorSuspended{" +
            "feePendingDate=" + feePendingDate +
            '}';
    }
}
