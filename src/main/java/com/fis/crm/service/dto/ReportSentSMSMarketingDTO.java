package com.fis.crm.service.dto;

public class ReportSentSMSMarketingDTO {

    private String campaignName;

    private String title;

    private String phoneNumber;

    private String sentUser;

    private String sentDate;

    private String status;

    private String content;

    private String statusView;

    public ReportSentSMSMarketingDTO() {
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSentUser() {
        return sentUser;
    }

    public void setSentUser(String sentUser) {
        this.sentUser = sentUser;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }
}
