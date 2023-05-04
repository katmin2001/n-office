package com.fis.crm.service.dto;

import java.util.Date;


public class RequestProductivityDetailDTO extends PageDTO {
    private String staffCode;
    private String ticketRequestCode;
    private String channelReceive;
    private String requestStatus;
    private String createDateTime;


    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getTicketRequestCode() {
        return ticketRequestCode;
    }

    public void setTicketRequestCode(String ticketRequestCode) {
        this.ticketRequestCode = ticketRequestCode;
    }

    public String getChannelReceive() {
        return channelReceive;
    }

    public void setChannelReceive(String channelReceive) {
        this.channelReceive = channelReceive;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
