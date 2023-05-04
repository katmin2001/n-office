package com.fis.crm.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.CampaignSmsBatch} entity.
 */
public class CampaignSmsBatchDTO implements Serializable {

    private Long id;

    private Long resourceId;

    private Long campaignSmsMarketingId;

    private Long batchId;

    private String phoneNumber;

    private String content;

    private String status;

    private Instant sendDate;

    private String responseBatch;

    private Instant createDatetime;

    private Long createUser;

    //custom properties

    private Integer checkList;


    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getCheckList() {
        return checkList;
    }

    public void setCheckList(Integer checkList) {
        this.checkList = checkList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignSmsMarketingId() {
        return campaignSmsMarketingId;
    }

    public void setCampaignSmsMarketingId(Long campaignSmsMarketingId) {
        this.campaignSmsMarketingId = campaignSmsMarketingId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResponseBatch() {
        return responseBatch;
    }

    public void setResponseBatch(String responseBatch) {
        this.responseBatch = responseBatch;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignSmsBatchDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignSmsBatchDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignSmsBatchDTO{" +
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
