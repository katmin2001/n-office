package com.fis.crm.service.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductivityReportDTO {
    private String staffName;
    private String notProcess;
    private String processing;
    private String processed;
    private String confirming;
    private String complete;
    private String staffId;

    public ProductivityReportDTO(ResultSet resultSet) throws SQLException {
        int i = 1;
        this.staffId = resultSet.getString(i++);
        this.staffName = resultSet.getString(i++);
        this.notProcess = resultSet.getString(i++);
        this.processing = resultSet.getString(i++);
        this.processed = resultSet.getString(i++);
        this.confirming = resultSet.getString(i++);
        this.complete = resultSet.getString(i);
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getNotProcess() {
        return notProcess;
    }

    public void setNotProcess(String notProcess) {
        this.notProcess = notProcess;
    }

    public String getProcessing() {
        return processing;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getConfirming() {
        return confirming;
    }

    public void setConfirming(String confirming) {
        this.confirming = confirming;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
