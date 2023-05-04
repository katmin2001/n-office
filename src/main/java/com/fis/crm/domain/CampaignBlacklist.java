package com.fis.crm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "CAMPAIGN_BLACKLIST")
public class CampaignBlacklist implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_BLACKLIST_GEN")
    @SequenceGenerator(name = "CAMPAIGN_BLACKLIST_GEN", sequenceName = "CAMPAIGN_BLACKLIST_SEQ", allocationSize = 1)

    private Long id;

    @Column(name = "CAMPAIGN_ID")
    private Long campaignId;

    @Column(name = "PHONE_NUMBER",unique = true, length = 30)
    private String phoneNumber;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_DATETIME")
    private Instant createDate;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Transient
    private String createUserName;

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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
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

}
