package com.fis.crm.service.dto;

import com.fis.crm.domain.CampaignResourceDetail;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link CampaignResourceDetail} entity.
 */
public class CampaignResourceDetailDTO implements Serializable {

    private Long id;

    private Long campaignResourceId;

    private String phoneNumber;

    private String status;

    private Date createDatetime;

    private Long createUser;

    private Long assignUserId;

    private String assignUserName;

    private Long groupId;

    private String callStatus;

    private Long campaignId;

    private String campaignResourceName;

    private String createUserName;

    private String search;

    private String evaluateStatus;

    private String CID;
    private String callTime;
    private String callingStatus;
    private String callingStatusName;
    private String saleStatus;
    private String saleStatusName;

    public String getAssignUserName() {
        return assignUserName;
    }

    public void setAssignUserName(String assignUserName) {
        this.assignUserName = assignUserName;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallingStatus() {
        return callingStatus;
    }

    public void setCallingStatus(String callingStatus) {
        this.callingStatus = callingStatus;
    }

    public String getCallingStatusName() {
        return callingStatusName;
    }

    public void setCallingStatusName(String callingStatusName) {
        this.callingStatusName = callingStatusName;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSaleStatusName() {
        return saleStatusName;
    }

    public void setSaleStatusName(String saleStatusName) {
        this.saleStatusName = saleStatusName;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Long assignUserId) {
        this.assignUserId = assignUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignResourceName() {
        return campaignResourceName;
    }

    public void setCampaignResourceName(String campaignResourceName) {
        this.campaignResourceName = campaignResourceName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
