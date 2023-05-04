package com.fis.crm.service.dto;

public class CampaignSmsBatchResDTO {

    private String listPhoneNumber;
    private String content;

    public String getListPhoneNumber() {
        return listPhoneNumber;
    }

    public void setListPhoneNumber(String listPhoneNumber) {
        this.listPhoneNumber = listPhoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
