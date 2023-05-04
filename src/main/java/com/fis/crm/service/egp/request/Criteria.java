package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "feePendingDate",
    "status"
})
public class Criteria {

    @JsonProperty("feePendingDate")
    private FeePendingDate feePendingDate;

    @JsonProperty("status")
    private Status status;

    public FeePendingDate getFeePendingDate() {
        return feePendingDate;
    }

    public void setFeePendingDate(FeePendingDate feePendingDate) {
        this.feePendingDate = feePendingDate;
    }

    public Criteria(FeePendingDate feePendingDate, Status status) {
        this.feePendingDate = feePendingDate;
        this.status = status;
    }

    public Criteria() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Criteria{" +
            "feePendingDate=" + feePendingDate +
            ", status=" + status +
            '}';
    }
}
