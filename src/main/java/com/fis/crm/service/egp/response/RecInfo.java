package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecInfo implements Serializable {

    @JsonProperty("recPhone")
    private String recPhone;
    @JsonProperty("recEmail")
    private String recEmail;
    @JsonProperty("orgCode")
    private String orgCode;
    @JsonProperty("recName")
    private String recName;

    public RecInfo(String recPhone, String recEmail, String orgCode, String recName) {
        this.recPhone = recPhone;
        this.recEmail = recEmail;
        this.orgCode = orgCode;
        this.recName = recName;
    }

    public RecInfo() {
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public String getRecEmail() {
        return recEmail;
    }

    public void setRecEmail(String recEmail) {
        this.recEmail = recEmail;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }
}
