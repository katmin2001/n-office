package com.fis.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "campaign_resource")
public class CampaignResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_RESOURCE_GEN")
    @SequenceGenerator(name = "CAMPAIGN_RESOURCE_GEN", sequenceName = "CAMPAIGN_RESOURCE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "RESOURCE_NAME")
    private String resourceName;

    @Column(name = "TOTAL_NOT_DISTINCT")
    private Long totalNotDistinct;

    @Column(name = "DISTINCT_DATA")
    private String distinctData;

    @Column(name = "TOTAL")
    private Long total;

    @Column(name = "CALLED")
    private Long called;

    @Column(name = "WATING_CALL")
    private Long waitingCall;

    @Column(name = "WATING_SHARE")
    private Long waitingShare;

    @Column(name = "CREATE_DATETIME")
    private Instant createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CALL_NOT_DONE")
    private Long callNotDone;

    public CampaignResource() {
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
