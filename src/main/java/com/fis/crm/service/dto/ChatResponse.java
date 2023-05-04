package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private DataChatResponse data;

    public ChatResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataChatResponse getData() {
        return data;
    }

    public void setData(DataChatResponse data) {
        this.data = data;
    }
}
