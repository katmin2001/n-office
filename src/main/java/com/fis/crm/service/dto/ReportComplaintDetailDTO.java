package com.fis.crm.service.dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportComplaintDetailDTO implements Serializable {
    private String ticketCode;
    private String ticketRequestCode;
    private String chanelType;
    private String requestType;
    private String bussinessType;
    private String phoneNumber;
    private String departmentName;
    private String priority;
    private String ticketStatus;
    private String requestStatus;
    private String createDatetime;
    private String userName;

    public ReportComplaintDetailDTO(ResultSet resultSet) throws SQLException {
        this.ticketCode = resultSet.getString(1);
        this.ticketRequestCode = resultSet.getString(2);
        this.chanelType = resultSet.getString(3);
        this.requestType = resultSet.getString(4);
        this.bussinessType = resultSet.getString(5);
        this.phoneNumber = resultSet.getString(6);
        this.departmentName = resultSet.getString(7);
        this.priority = resultSet.getString(8);
        this.ticketStatus = resultSet.getString(9);
        this.requestStatus = resultSet.getString(10);
        this.createDatetime = resultSet.getString(11);
        this.userName = resultSet.getString(12);
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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ReportComplaintDetailDTO{" +
            "ticketCode='" + ticketCode + '\'' +
            ", ticketRequestCode='" + ticketRequestCode + '\'' +
            ", chanelType='" + chanelType + '\'' +
            ", requestType='" + requestType + '\'' +
            ", bussinessType='" + bussinessType + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", departmentName='" + departmentName + '\'' +
            ", priority='" + priority + '\'' +
            ", ticketStatus='" + ticketStatus + '\'' +
            ", requestStatus='" + requestStatus + '\'' +
            ", createDatetime='" + createDatetime + '\'' +
            ", userNamer='" + userName + '\'' +
            '}';
    }
}
