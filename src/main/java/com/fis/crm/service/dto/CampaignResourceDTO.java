package com.fis.crm.service.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.Instant;

public class CampaignResourceDTO {

    private Long id;

    private Long campaignId;

    private String resourceName;

    private Long totalNotDistinct;

    private String distinctData;

    private Long total;

    private Long called;

    private Long waitingCall;

    private Long waitingShare;

    private Instant createDateTime;

    private Long createUser;

    private String campaignName;

    private String createUserName;

    private String createDateTimeView;

    private String status;

    private Long callNotDone;

    public CampaignResourceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getTotalNotDistinct() {
        return totalNotDistinct;
    }

    public void setTotalNotDistinct(Long totalNotDistinct) {
        this.totalNotDistinct = totalNotDistinct;
    }

    public String getDistinctData() {
        return distinctData;
    }

    public void setDistinctData(String distinctData) {
        this.distinctData = distinctData;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCalled() {
        return called;
    }

    public void setCalled(Long called) {
        this.called = called;
    }

    public Long getWaitingCall() {
        return waitingCall;
    }

    public void setWaitingCall(Long waitingCall) {
        this.waitingCall = waitingCall;
    }

    public Long getWaitingShare() {
        return waitingShare;
    }

    public void setWaitingShare(Long waitingShare) {
        this.waitingShare = waitingShare;
    }

    public Instant getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Instant createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateDateTimeView() {
        return createDateTimeView;
    }

    public void setCreateDateTimeView(String createDateTimeView) {
        this.createDateTimeView = createDateTimeView;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCallNotDone() {
        return callNotDone;
    }

    public void setCallNotDone(Long callNotDone) {
        this.callNotDone = callNotDone;
    }
}
