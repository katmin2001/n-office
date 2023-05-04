package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "CAMPAIGN_EMAIL_MARKETING")
public class CampaignEmailMarketing {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_EMAIL_MARKETING_GEN")
    @SequenceGenerator(name = "CAMPAIGN_EMAIL_MARKETING_GEN", sequenceName = "CAMPAIGN_EMAIL_MARKETING_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "START_DATE")
    private Instant startDate;

    @Column(name = "END_DATE")
    private Instant endDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CREATE_DATETIME")
    private Instant createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "IS_USED")
    private String isUsed;

    public CampaignEmailMarketing() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
}
