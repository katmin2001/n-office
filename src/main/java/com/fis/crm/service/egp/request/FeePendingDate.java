package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "greaterThanOrEqual",
    "lessThanOrEqual"
})
public class FeePendingDate {

    @JsonProperty("greaterThanOrEqual")
    private String greaterThanOrEqual;

    @JsonProperty("lessThanOrEqual")
    private String lessThanOrEqual;

    public FeePendingDate(String greaterThanOrEqual, String lessThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
        this.lessThanOrEqual = lessThanOrEqual;
    }

    public FeePendingDate() {
    }

    public String getGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    public void setGreaterThanOrEqual(String greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
    }

    public String getLessThanOrEqual() {
        return lessThanOrEqual;
    }

    public void setLessThanOrEqual(String lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
    }

    @Override
    public String toString() {
        return "FeePendingDate{" +
            "greaterThanOrEqual=" + greaterThanOrEqual +
            ", lessThanOrEqual=" + lessThanOrEqual +
            '}';
    }
}
