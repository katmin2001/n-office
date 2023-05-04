package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;
import java.util.Map;

public class CampaignEmailBlackListDTO {

    private Long id;

    private Long campaignEmailId;

    private String email;

    private String fullName;

    private String status;

    private Date createDateTime;

    private Long createUser;

    private String errorCodeConfig;

    private String importType;

    private String createUserName;

    @JsonIgnore
    private Workbook xssfWorkbook;

    private int total;

    private int success;

    private int error;

    private int duplicate;

    public Workbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(Workbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(String errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignEmailId() {
        return campaignEmailId;
    }

    public void setCampaignEmailId(Long campaignEmailId) {
        this.campaignEmailId = campaignEmailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(int duplicate) {
        this.duplicate = duplicate;
    }
}
