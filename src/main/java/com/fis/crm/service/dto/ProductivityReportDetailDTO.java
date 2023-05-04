package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

public class ProductivityReportDetailDTO {
    private String ticketCode;
    private String ticketRequestCode;
    private String channelReceive;
    private String requestStatus;
    private String createDateTime;

    public ProductivityReportDetailDTO() {
    }

    public ProductivityReportDetailDTO(Object[] objects) {
        int i = 0;
        this.ticketCode = DataUtil.safeToString(objects[i++]);
        this.ticketRequestCode = DataUtil.safeToString(objects[i++]);
        this.channelReceive = DataUtil.safeToString(objects[i++]);
        this.requestStatus = DataUtil.safeToString(objects[i++]);
        this.createDateTime = DataUtil.safeToString(objects[i++]);
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
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
