package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSResponse {
    @JsonProperty("CodeResult")
    private String codeResult;

    @JsonProperty("CountRegenerate")
    private String countRegenerate;

    @JsonProperty("SMSID")
    private String SMSID;

    @JsonProperty("MessageId")
    private Integer messageId;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("BrandName")
    private String brandName;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("PartnerId")
    private String partnerId;

    @JsonProperty("Telco")
    private String telco;

    public SMSResponse() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getCodeResult() {
        return codeResult;
    }

    public void setCodeResult(String codeResult) {
        this.codeResult = codeResult;
    }

    public String getCountRegenerate() {
        return countRegenerate;
    }

    public void setCountRegenerate(String countRegenerate) {
        this.countRegenerate = countRegenerate;
    }

    public String getSMSID() {
        return SMSID;
    }

    public void setSMSID(String SMSID) {
        this.SMSID = SMSID;
    }

    @Override
    public String toString() {
        return "SMSResponse{" +
            "codeResult='" + codeResult + '\'' +
            ", countRegenerate='" + countRegenerate + '\'' +
            ", SMSID='" + SMSID + '\'' +
            '}';
    }
}
