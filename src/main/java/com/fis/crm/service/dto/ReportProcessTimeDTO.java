package com.fis.crm.service.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportProcessTimeDTO {
    private String ticketCode;
    private String ticketRequestCode;
    private String idCode;
    private String phoneNumber;
    private String departmentName;
    private String priority;
    private String ticketStatus;
    private String requestStatus;
    private String processDeadline;
    private String confirmDeadline;
    private String receiveDate;
    private String receiveUser;
    private String processDate;
    private String processUser;
    private String confirmDate;
    private String confirmUser;

    public ReportProcessTimeDTO(ResultSet resultSet) throws SQLException {
        int i = 1;
        this.ticketCode = resultSet.getString(i++);
        this.ticketRequestCode = resultSet.getString(i++);
        this.idCode = resultSet.getString(i++);
        this.phoneNumber = resultSet.getString(i++);
        this.departmentName = resultSet.getString(i++);
        this.priority = resultSet.getString(i++);
        this.ticketStatus = resultSet.getString(i++);
        this.requestStatus = resultSet.getString(i++);
        this.processDeadline = resultSet.getString(i++);
        this.confirmDeadline = resultSet.getString(i++);
        this.receiveDate = resultSet.getString(i++);
        this.receiveUser = resultSet.getString(i++);
        this.processDate = resultSet.getString(i++);
        this.processUser = resultSet.getString(i++);
        this.confirmDate = resultSet.getString(i++);
        this.confirmUser = resultSet.getString(i++);
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

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
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

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getProcessUser() {
        return processUser;
    }

    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }
}
