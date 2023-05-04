package com.fis.crm.service.dto;

import javax.persistence.Column;
import java.time.Instant;
import java.util.Date;

public class CampaignEmailMarketingDTO {
    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String status;

    private String content;

    private String title;

    private Instant createDateTime;

    private Long createUser;

    private String createDateTimeView;

    private String createUserName;

    private String startDateView;

    private String endDateView;

    private Long totalData;

    private String isUsed;

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

    public String getCreateDateTimeView() {
        return createDateTimeView;
    }

    public void setCreateDateTimeView(String createDateTimeView) {
        this.createDateTimeView = createDateTimeView;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getStartDateView() {
        return startDateView;
    }

    public void setStartDateView(String startDateView) {
        this.startDateView = startDateView;
    }

    public String getEndDateView() {
        return endDateView;
    }

    public void setEndDateView(String endDateView) {
        this.endDateView = endDateView;
    }

    public Long getTotalData() {
        return totalData;
    }

    public void setTotalData(Long totalData) {
        this.totalData = totalData;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }
}
