package com.fis.crm.service.dto;

import java.time.Instant;

public class CampaignResourceErrorDTO {

    private Long id;

    private Long campaignResourceId;

    private String phoneNumber;

    private String status;

    private Instant createDateTime;

    private Long createUser;

    private String campaignResourceName;

    private String createUserName;

    public CampaignResourceErrorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Instant createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCampaignResourceName() {
        return campaignResourceName;
    }

    public void setCampaignResourceName(String campaignResourceName) {
        this.campaignResourceName = campaignResourceName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
