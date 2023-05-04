package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIG_AUTO_EMAIL")
public class ConfigAutoEmail {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIG_AUTO_EMAIL_GEN")
    @SequenceGenerator(name = "CONFIG_AUTO_EMAIL_GEN", sequenceName = "CONFIG_AUTO_EMAIL_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CAMPAIGN_EMAIL_MARKETING_ID")
    private Long campaignEmailId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TIME_TYPE")
    private String timeType;

    @Column(name = "DATE_OF_WEEK")
    private String dateOfWeek;

    @Column(name = "TIME_SEND")
    private String timeSend;

    @Column(name = "DATE_SEND")
    private Date dateSend;

    @Column(name = "CREATE_DATETIME")
    private Date createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "STATUS")
    private String status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getDateOfWeek() {
        return dateOfWeek;
    }

    public void setDateOfWeek(String dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public Date getDateSend() {
        return dateSend;
    }

    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
