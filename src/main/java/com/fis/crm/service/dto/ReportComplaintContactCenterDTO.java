package com.fis.crm.service.dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportComplaintContactCenterDTO implements Serializable {
    private String ticketCode;
    private String ticketRequestCode;
    private String chanelType;
    private String bussinessType;
    private String requestContent;
    private String requestProcess;
    private String satisfactionLevel;
    private String receiveDate;
    private String receiveUser;
    private String requestStatus;
    private String userName;

    public ReportComplaintContactCenterDTO(ResultSet resultSet) throws SQLException {
        int i = 1;
        this.ticketCode = resultSet.getString(i++);
        this.ticketRequestCode = resultSet.getString(i++);
        this.chanelType = resultSet.getString(i++);
        this.bussinessType = resultSet.getString(i++);
        this.requestContent = resultSet.getString(i++);
        this.requestProcess = resultSet.getString(i++);
        this.requestStatus = resultSet.getString(i++);
        this.satisfactionLevel = resultSet.getString(i++);
        this.receiveDate = resultSet.getString(i++);
        this.receiveUser = resultSet.getString(i++);
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

    public String getChanelType() {
        return chanelType;
    }

    public void setChanelType(String chanelType) {
        this.chanelType = chanelType;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getRequestProcess() {
        return requestProcess;
    }

    public void setRequestProcess(String requestProcess) {
        this.requestProcess = requestProcess;
    }

    public String getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public void setSatisfactionLevel(String satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
