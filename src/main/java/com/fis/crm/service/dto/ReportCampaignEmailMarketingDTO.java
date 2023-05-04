package com.fis.crm.service.dto;

public class ReportCampaignEmailMarketingDTO {

    private String campaignName;

    private String totalEmail;

    private String sent;

    private String unsent;

    private String sentSuccess;

    private String sentError;

    public ReportCampaignEmailMarketingDTO() {
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getTotalEmail() {
        return totalEmail;
    }

    public void setTotalEmail(String totalEmail) {
        this.totalEmail = totalEmail;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getUnsent() {
        return unsent;
    }

    public void setUnsent(String unsent) {
        this.unsent = unsent;
    }

    public String getSentSuccess() {
        return sentSuccess;
    }

    public void setSentSuccess(String sentSuccess) {
        this.sentSuccess = sentSuccess;
    }

    public String getSentError() {
        return sentError;
    }

    public void setSentError(String sentError) {
        this.sentError = sentError;
    }
}
