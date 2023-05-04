package com.fis.crm.domain;

import org.apache.poi.ss.formula.functions.Na;

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
@Table(name = "campaign_resource_error")
public class CampaignResourceError implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_RESOURCE_ERROR_GEN")
    @SequenceGenerator(name = "CAMPAIGN_RESOURCE_ERROR_GEN", sequenceName = "CAMPAIGN_RESOURCE_ERROR_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CAMPAIN_RESOURCE_ID")
    private Long campaignResourceId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_DATETIME")
    private Instant createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    public CampaignResourceError() {
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
}
