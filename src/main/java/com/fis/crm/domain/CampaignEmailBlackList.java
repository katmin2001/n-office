package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CAMPAIGN_EMAIL_BLACKLIST")
public class CampaignEmailBlackList {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_EMAIL_BLACKLIST_GEN")
    @SequenceGenerator(name = "CAMPAIGN_EMAIL_BLACKLIST_GEN", sequenceName = "CAMPAIGN_EMAIL_BLACKLIST_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CAMPAIGN_EMAIL_ID")
    private Long campaignEmailId;

    @Column(name = "EMAIL", unique = true)
    private String email;

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

    public Long getCampaignEmailId() {
        return campaignEmailId;
    }

    public void setCampaignEmailId(Long campaignEmailId) {
        this.campaignEmailId = campaignEmailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
