package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "equals"
})
public class Status {

    @JsonProperty("equals")
    private String equals;

    public Status(String equals) {
        this.equals = equals;
    }

    public String getEquals() {
        return equals;
    }

    public void setEquals(String equals) {
        this.equals = equals;
    }

    public Status() {
    }

    @Override
    public String toString() {
        return "Status{" +
            "equals=" + equals +
            '}';
    }
}
