package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.List;

public class CampaignSMSBlackListDTO {

    private Long id;

    private Long campaignSMSId;

    private String campaignSMSName;

    private String phoneNumber;

    private String fullName;

    private String status;

    private Date createDateTime;

    private Long createUser;

    private List<String> errorCodeConfig;

    private String importType;

    private String createUserName;

    @JsonIgnore
    private Workbook xssfWorkbook;

    private int total;

    private int success;

    private int error;

    private int duplicate;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignSMSId() {
        return campaignSMSId;
    }

    public void setCampaignSMSId(Long campaignSMSId) {
        this.campaignSMSId = campaignSMSId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<String> getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(List<String> errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public Workbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(Workbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
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

    public String getCampaignSMSName() {
        return campaignSMSName;
    }

    public void setCampaignSMSName(String campaignSMSName) {
        this.campaignSMSName = campaignSMSName;
    }
}
