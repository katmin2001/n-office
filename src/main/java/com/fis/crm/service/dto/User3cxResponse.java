package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User3cxResponse {

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
