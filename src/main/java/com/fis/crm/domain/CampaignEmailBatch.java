package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "CAMPAIGN_EMAIL_BATCH")
public class CampaignEmailBatch {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_EMAIL_BATCH_GEN")
    @SequenceGenerator(name = "CAMPAIGN_EMAIL_BATCH_GEN", sequenceName = "CAMPAIGN_EMAIL_BATCH_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CAMPAIGN_EMAIL_MARKETING_ID")
    private Long campaignEmailMarketingId;

    @Column(name = "BATCH_ID")
    private Long batchId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SEND_DATE")
    private Instant sendDate;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "CREATE_DATETIME")
    private Instant createDate;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "CC_EMAIL")
    private String ccEmail;

    @Column(name = "BCC_EMAIL")
    private String bccEmail;

    @Column(name = "CAMPAIGN_EMAIL_RESOURCE_ID")
    private Long campaignEmailResourceId;

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignEmailMarketingId() {
        return campaignEmailMarketingId;
    }

    public void setCampaignEmailMarketingId(Long campaignEmailMarketingId) {
        this.campaignEmailMarketingId = campaignEmailMarketingId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getCampaignEmailResourceId() {
        return campaignEmailResourceId;
    }

    public void setCampaignEmailResourceId(Long campaignEmailResourceId) {
        this.campaignEmailResourceId = campaignEmailResourceId;
    }
}
