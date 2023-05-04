package com.fis.crm.service.dto;

import java.util.Date;

public class CustomerRegisterDTO {

    private Long id;

    private String recordsCode;

    private String cid;

    private String taxCode;

    private String name;

    private String requestType;

    private String requestTypeDisplay;

    private Date sendDate;

    private String sendDateDisplay;

    private String sendDateFrom;

    private String sendDateTo;

    private Date approveDate;

    private String approveDateDisplay;

    private String deadline;

    private String deadlineDisplay;

    private String ctsStatus;

    private String ctsStatusDisplay;

    private Date releaseTime;

    private String releaseTimeDisplay;

    private String reasons;

    private Integer exportType;

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getRequestTypeDisplay() {
        return requestTypeDisplay;
    }

    public void setRequestTypeDisplay(String requestTypeDisplay) {
        this.requestTypeDisplay = requestTypeDisplay;
    }

    public String getDeadlineDisplay() {
        return deadlineDisplay;
    }

    public void setDeadlineDisplay(String deadlineDisplay) {
        this.deadlineDisplay = deadlineDisplay;
    }

    public String getCtsStatusDisplay() {
        return ctsStatusDisplay;
    }

    public void setCtsStatusDisplay(String ctsStatusDisplay) {
        this.ctsStatusDisplay = ctsStatusDisplay;
    }

    public String getSendDateDisplay() {
        return sendDateDisplay;
    }

    public void setSendDateDisplay(String sendDateDisplay) {
        this.sendDateDisplay = sendDateDisplay;
    }

    public String getApproveDateDisplay() {
        return approveDateDisplay;
    }

    public void setApproveDateDisplay(String approveDateDisplay) {
        this.approveDateDisplay = approveDateDisplay;
    }

    public String getReleaseTimeDisplay() {
        return releaseTimeDisplay;
    }

    public void setReleaseTimeDisplay(String releaseTimeDisplay) {
        this.releaseTimeDisplay = releaseTimeDisplay;
    }

    public String getSendDateFrom() {
        return sendDateFrom;
    }

    public void setSendDateFrom(String sendDateFrom) {
        this.sendDateFrom = sendDateFrom;
    }

    public String getSendDateTo() {
        return sendDateTo;
    }

    public void setSendDateTo(String sendDateTo) {
        this.sendDateTo = sendDateTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordsCode() {
        return recordsCode;
    }

    public void setRecordsCode(String recordsCode) {
        this.recordsCode = recordsCode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCtsStatus() {
        return ctsStatus;
    }

    public void setCtsStatus(String ctsStatus) {
        this.ctsStatus = ctsStatus;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
