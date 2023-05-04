package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "requestDate"
})
public class ReportRegister {

    @JsonProperty("requestDate")
    private FeePendingDate requestDate;

    public ReportRegister(FeePendingDate requestDate) {
        this.requestDate = requestDate;
    }

    public ReportRegister() {
    }

    public FeePendingDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(FeePendingDate requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "ReportRegister{" +
            "requestDate=" + requestDate +
            '}';
    }
}
