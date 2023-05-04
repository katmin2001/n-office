package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A CampaignSmsBatch.
 */
@Entity
@Table(name = "campaign_sms_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignSmsBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SMS_BATCH_GEN")
    @SequenceGenerator(name = "CAMPAIGN_SMS_BATCH_GEN", sequenceName = "CAMPAIGN_SMS_BATCH_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "campaign_sms_marketing_id")
    private Long campaignSmsMarketingId;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "response")
    private String responseBatch;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    //campaignSmsResourceId
    @Column(name = "campaign_sms_resource_id")
    private Long resourceId;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long campaignSmsResourceId) {
        this.resourceId = campaignSmsResourceId;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignSmsMarketingId() {
        return campaignSmsMarketingId;
    }

    public CampaignSmsBatch campaignSmsMarketingId(Long campaignSmsMarketingId) {
        this.campaignSmsMarketingId = campaignSmsMarketingId;
        return this;
    }

    public void setCampaignSmsMarketingId(Long campaignSmsMarketingId) {
        this.campaignSmsMarketingId = campaignSmsMarketingId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public CampaignSmsBatch batchId(Long batchId) {
        this.batchId = batchId;
        return this;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CampaignSmsBatch phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public CampaignSmsBatch content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public CampaignSmsBatch status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public CampaignSmsBatch sendDate(Instant sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResponseBatch() {
        return responseBatch;
    }

    public CampaignSmsBatch responseBatch(String responseBatch) {
        this.responseBatch = responseBatch;
        return this;
    }

    public void setResponseBatch(String responseBatch) {
        this.responseBatch = responseBatch;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public CampaignSmsBatch createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public CampaignSmsBatch createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignSmsBatch)) {
            return false;
        }
        return id != null && id.equals(((CampaignSmsBatch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignSmsBatch{" +
            "id=" + getId() +
            ", campaignSmsMarketingId=" + getCampaignSmsMarketingId() +
            ", batchId=" + getBatchId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", responseBatch='" + getResponseBatch() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            "}";
    }
}
