package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CAMPAIGN_SMS_BLACKLIST")
public class CampaignSMSBlackList {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SMS_BLACKLIST_GEN")
    @SequenceGenerator(name = "CAMPAIGN_SMS_BLACKLIST_GEN", sequenceName = "CAMPAIGN_SMS_BLACKLIST_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CAMPAIGN_SMS_ID")
    private Long campaignSMSId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_DATETIME")
    private Date createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Transient
    private String createUserName;

    @Transient
    private String campaignSMSName;

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

    public String getCampaignSMSName() {
        return campaignSMSName;
    }

    public void setCampaignSMSName(String campaignSMSName) {
        this.campaignSMSName = campaignSMSName;
    }
}
