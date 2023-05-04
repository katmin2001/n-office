package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSRequest {
    @JsonProperty("ApiKey")
    private String apiKey;
    @JsonProperty("Content")
    private String content;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("SecretKey")
    private String secretKey;
    @JsonProperty("IsUnicode")
    private String isUnicode;
    @JsonProperty("SmsType")
    private String smsType;
    @JsonProperty("Brandname")
    private String brandname;
    @JsonProperty("Sandbox")
    private String sendBox;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("BrandName")
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public SMSRequest() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIsUnicode() {
        return isUnicode;
    }

    public void setIsUnicode(String isUnicode) {
        this.isUnicode = isUnicode;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getSendBox() {
        return sendBox;
    }

    public void setSendBox(String sendBox) {
        this.sendBox = sendBox;
    }
}
