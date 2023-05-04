package com.fis.crm.service.dto;

import java.time.Instant;

public class CampaignSMSMarketingDTO {

    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private String status;

    private String content;

    private String title;

    private Instant createDateTime;

    private String createDateTimeView;

    private Long createUser;

    private String createUserName;

    private Instant updateDateTime;

    private String updateDateTimeView;

    private Long updateUser;

    private String updateUserName;

    private String startDateView;

    private String endDateView;

    private Long totalData;

    public CampaignSMSMarketingDTO() {
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

    public Instant getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Instant updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateDateTimeView() {
        return updateDateTimeView;
    }

    public void setUpdateDateTimeView(String updateDateTimeView) {
        this.updateDateTimeView = updateDateTimeView;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
