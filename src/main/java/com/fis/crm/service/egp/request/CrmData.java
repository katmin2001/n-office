package com.fis.crm.service.egp.request;

import java.io.Serializable;

public class CrmData implements Serializable {
    private String crmId = "1";
    private String recEmail;
    private String recPhone;
    private String orgCode;

    public CrmData(String crmId, String recEmail, String recPhone, String orgCode) {
        this.crmId = "1";
        this.recEmail = recEmail;
        this.recPhone = recPhone;
        this.orgCode = orgCode;
    }

    public CrmData() {
        this.crmId = "1";
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getRecEmail() {
        return recEmail;
    }

    public void setRecEmail(String recEmail) {
        this.recEmail = recEmail;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    @Override
    public String toString() {
        return "CrmData{" +
            "crmId='" + crmId + '\'' +
            ", recEmail='" + recEmail + '\'' +
            ", recPhone='" + recPhone + '\'' +
            ", orgCode='" + orgCode + '\'' +
            '}';
    }
}
