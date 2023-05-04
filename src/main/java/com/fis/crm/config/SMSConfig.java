package com.fis.crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "esms")
public class SMSConfig {
    private String apiKey = "4B80DDEA629CB20D25CDF5A2C0B801";
    private String secretKey = "4225B43ACE0E09AA2FA1D4174CDC66";
    private String sandbox = "1";
//    private String content = "la ma xac minh dang ky Baotrixemay cua ban";
    private String brandName = "Baotrixemay";
    private String smsType = "2";
    private String sendSmsUrl = "http://rest.esms.vn/MainService.svc/json/SendMultipleMessage_V4_post_json/";

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSandbox() {
        return sandbox;
    }

    public void setSandbox(String sandbox) {
        this.sandbox = sandbox;
    }

//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSendSmsUrl() {
        return sendSmsUrl;
    }

    public void setSendSmsUrl(String sendSmsUrl) {
        this.sendSmsUrl = sendSmsUrl;
    }
}
