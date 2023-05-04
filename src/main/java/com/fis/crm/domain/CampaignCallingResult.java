package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "CAMPAIGN_CALLING_RESULT")
public class CampaignCallingResult {
    private Long id;
    private Long campaignScriptId;
    private Long campaignScriptQuestionId;
    private String code;
    private String type;
    private Long position;
    private String display;
    private String content;
    private Long minValue;
    private Long maxValue;
    private String status;
    private Instant createDatetime;
    private Long createUser;
    private Instant updateDatetime;
    private Long updateUser;
    private Long campaignId;
    private Long campaignResourceDetailId;
    private String value;
    private String evaluateStatus;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_CALLING_RESULT_SEQ")
    @SequenceGenerator(name = "CAMPAIGN_CALLING_RESULT_SEQ", sequenceName = "CAMPAIGN_CALLING_RESULT_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CAMPAIGN_SCRIPT_ID")
    public Long getCampaignScriptId() {
        return campaignScriptId;
    }

    public void setCampaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
    }

    @Basic
    @Column(name = "CAMPAIGN_SCRIPT_QUESTION_ID")
    public Long getCampaignScriptQuestionId() {
        return campaignScriptQuestionId;
    }

    public void setCampaignScriptQuestionId(Long campaignScriptQuestionId) {
        this.campaignScriptQuestionId = campaignScriptQuestionId;
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "POSITION")
    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Basic
    @Column(name = "DISPLAY")
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "MIN_VALUE")
    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    @Basic
    @Column(name = "MAX_VALUE")
    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CREATE_DATETIME")
    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Basic
    @Column(name = "CREATE_USER")
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "UPDATE_DATETIME")
    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    @Basic
    @Column(name = "UPDATE_USER")
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @Column(name = "CAMPAIGN_ID")
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    @Basic
    @Column(name = "CAMPAIGN_RESOURCE_DETAIL_ID")
    public Long getCampaignResourceDetailId() {
        return campaignResourceDetailId;
    }

    public void setCampaignResourceDetailId(Long campaignResourceDetailId) {
        this.campaignResourceDetailId = campaignResourceDetailId;
    }

    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignCallingResult that = (CampaignCallingResult) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(campaignScriptId, that.campaignScriptId) &&
            Objects.equals(campaignScriptQuestionId, that.campaignScriptQuestionId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(type, that.type) &&
            Objects.equals(position, that.position) &&
            Objects.equals(display, that.display) &&
            Objects.equals(content, that.content) &&
            Objects.equals(minValue, that.minValue) &&
            Objects.equals(maxValue, that.maxValue) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createDatetime, that.createDatetime) &&
            Objects.equals(createUser, that.createUser) &&
            Objects.equals(updateDatetime, that.updateDatetime) &&
            Objects.equals(updateUser, that.updateUser) &&
            Objects.equals(campaignId, that.campaignId) &&
            Objects.equals(campaignResourceDetailId, that.campaignResourceDetailId) &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campaignScriptId, campaignScriptQuestionId, code, type, position, display, content, minValue, maxValue, status, createDatetime, createUser, updateDatetime, updateUser, campaignId, campaignResourceDetailId, value);
    }

    @Basic
    @Column(name = "EVALUATE_STATUS")
    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }
}
