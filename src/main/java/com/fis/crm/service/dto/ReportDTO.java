package com.fis.crm.service.dto;

public class ReportDTO {
    private String callTimeFrom;
    private String callTimeTo;
    private Long campaignId;
    private Long assignUserId;
    private String statusCall;
    private Long questionId;
    private String content;// content of answers (report-render-statisic-question)
    private Integer total;// total of answers

    private String assignUser;

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getCallTimeFrom() {
        return callTimeFrom;
    }

    public void setCallTimeFrom(String callTimeFrom) {
        this.callTimeFrom = callTimeFrom;
    }

    public String getCallTimeTo() {
        return callTimeTo;
    }

    public void setCallTimeTo(String callTimeTo) {
        this.callTimeTo = callTimeTo;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Long assignUserId) {
        this.assignUserId = assignUserId;
    }

    public String getStatusCall() {
        return statusCall;
    }

    public void setStatusCall(String statusCall) {
        this.statusCall = statusCall;
    }
}
