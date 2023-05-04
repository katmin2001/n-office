package com.fis.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "CAMPAIGN_SMS_RESOURCE")
public class CampaignSMSResource {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SMS_RESOURCE_GEN")
    @SequenceGenerator(name = "CAMPAIGN_SMS_RESOURCE_GEN", sequenceName = "CAMPAIGN_SMS_RESOURCE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "campaign_sms_marketing_id", nullable = false)
    private Long campaignSmsMarketingId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "c1")
    private String c1;

    @Column(name = "c2")
    private String c2;

    @Column(name = "c3")
    private String c3;

    @Column(name = "c4")
    private String c4;

    @Column(name = "c5")
    private String c5;

    @Column(name = "c6")
    private String c6;

    @Column(name = "c7")
    private String c7;

    @Column(name = "c8")
    private String c8;

    @Column(name = "c9")
    private String c9;

    @Column(name = "c10")
    private String c10;

    @Column(name = "create_datetime")
    private Instant createDateTime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "send_user_id")
    private Long sendUserId;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "send_status")
    private String sendStatus;

    public CampaignSMSResource() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public String getC5() {
        return c5;
    }

    public void setC5(String c5) {
        this.c5 = c5;
    }

    public String getC6() {
        return c6;
    }

    public void setC6(String c6) {
        this.c6 = c6;
    }

    public String getC7() {
        return c7;
    }

    public void setC7(String c7) {
        this.c7 = c7;
    }

    public String getC8() {
        return c8;
    }

    public void setC8(String c8) {
        this.c8 = c8;
    }

    public String getC9() {
        return c9;
    }

    public void setC9(String c9) {
        this.c9 = c9;
    }

    public String getC10() {
        return c10;
    }

    public void setC10(String c10) {
        this.c10 = c10;
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

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
}
