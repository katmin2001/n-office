package com.fis.crm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A CampaignResourceDetail.
 */
@Entity
@Table(name = "campaign_resource_detail")
public class CampaignResourceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_RESOURCE_DETAIL_GEN")
    @SequenceGenerator(name = "CAMPAIGN_RESOURCE_DETAIL_GEN", sequenceName = "CAMPAIGN_RESOURCE_DETAIL_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "campaign_resource_id")
    private Long campaignResourceId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Date createDateTime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "assign_user_id")
    private Long assignUserId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "call_status")
    private String callStatus;

    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "CID")
    private String CID;

    @Column(name = "EVALUATE_STATUS")
    private String evaluateStatus;

    @Column(name = "CALL_TIME")
    private String callTime;

    @Column(name = "CALLING_STATUS")
    private String callingStatus;

    @Column(name = "SALE_STATUS")
    private String saleStatus;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "START_CALL")
    private String startCall;

    @Column(name = "CALL_FILE")
    private String callFile;

    @Column(name = "ERROR_STATUS")
    private String errorStatus;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartCall() {
        return startCall;
    }

    public void setStartCall(String startCall) {
        this.startCall = startCall;
    }

    public String getCallFile() {
        return callFile;
    }

    public void setCallFile(String callFile) {
        this.callFile = callFile;
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

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public CampaignResourceDetail campaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
        return this;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CampaignResourceDetail phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public CampaignResourceDetail status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public CampaignResourceDetail createDatetime(Date createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public CampaignResourceDetail createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getAssignUserId() {
        return assignUserId;
    }

    public CampaignResourceDetail assignUserId(Long assignUserId) {
        this.assignUserId = assignUserId;
        return this;
    }

    public void setAssignUserId(Long assignUserId) {
        this.assignUserId = assignUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public CampaignResourceDetail groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public CampaignResourceDetail callStatus(String callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public CampaignResourceDetail campaignId(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignResourceDetail)) {
            return false;
        }
        return id != null && id.equals(((CampaignResourceDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignResourceDetai1{" +
            "id=" + getId() +
            ", campaignResourceId=" + getCampaignResourceId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDateTime='" + getCreateDateTime() + "'" +
            ", createUser=" + getCreateUser() +
            ", assignUserId=" + getAssignUserId() +
            ", groupId=" + getGroupId() +
            ", callStatus='" + getCallStatus() + "'" +
            ", campaignId=" + getCampaignId() +
            "}";
    }
}
