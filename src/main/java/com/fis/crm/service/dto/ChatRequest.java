package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatRequest {

    @JsonProperty("user")
    private String user;

    @JsonProperty("password")
    private String password;

    public ChatRequest() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
