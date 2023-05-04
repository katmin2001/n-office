package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSTokenResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "SMSTokenResponseDTO{" +
            "accessToken='" + accessToken + '\'' +
            ", expiresIn='" + expiresIn + '\'' +
            ", tokenType='" + tokenType + '\'' +
            ", scope='" + scope + '\'' +
            '}';
    }
}
