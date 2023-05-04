package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataChatResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("authToken")
    private String authToken;

    public DataChatResponse() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
