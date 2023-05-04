package com.fis.crm.service.dto;

import java.util.Date;

public class RequestReportTimeProcessDTO extends PageDTO {
    private String idCode;
    private String processDeadline;
    private String confirmDeadline;
    private String phoneNumber;
    private String requestStatus;
    private String ticketStatus;
    private Date fromDate;
    private Date toDate;
    private String queryType;

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getProcessDeadline() {
        return processDeadline;
    }

    public void setProcessDeadline(String processDeadline) {
        this.processDeadline = processDeadline;
    }

    public String getConfirmDeadline() {
        return confirmDeadline;
    }

    public void setConfirmDeadline(String confirmDeadline) {
        this.confirmDeadline = confirmDeadline;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
