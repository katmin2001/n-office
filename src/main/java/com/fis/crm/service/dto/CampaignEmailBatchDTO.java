package com.fis.crm.service.dto;

import java.time.Instant;

public class CampaignEmailBatchDTO {
    private Long id;

    private Long campaignEmailMarketingId;

    private Long batchId;

    private String email;

    private String content;

    private String title;

    private String status;

    private Instant sendDate;

    private String response;

    private Instant createDate;

    private Long createUser;

    private String ccEmail;

    private String bccEmail;

    private Long campaignEmailResourceId;

    public Long getCampaignEmailResourceId() {
        return campaignEmailResourceId;
    }

    public void setCampaignEmailResourceId(Long campaignEmailResourceId) {
        this.campaignEmailResourceId = campaignEmailResourceId;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignEmailMarketingId() {
        return campaignEmailMarketingId;
    }

    public void setCampaignEmailMarketingId(Long campaignEmailMarketingId) {
        this.campaignEmailMarketingId = campaignEmailMarketingId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}
