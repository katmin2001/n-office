package com.fis.crm.service.dto;

import java.time.Instant;

public class CampaignScriptAnswerResponseDTO {
    private Long id;

    private Long campaignScriptId;

    private Long campaignScriptQuestionId;

    private String questionCode;

    private String code;

    private String type;

    private Integer position;

    private String display;

    private String content;

    private Integer min;

    private Integer max;

    private String status;

    private Long createUserId;

    private Long updateUserId;

    private String createUsername;

    private String updateUsername;

    private String createFullName;

    private String updateFullName;

    private Instant createDatetime;

    private Instant updateDatetime;

    public CampaignScriptAnswerResponseDTO() {
    }

    public CampaignScriptAnswerResponseDTO(Long id, Long campaignScriptId,
                                           Long campaignScriptQuestionId, String code,
                                           String type, Integer position,
                                           String display, String content,
                                           Integer min, Integer max,
                                           String status, Long createUser,
                                           Long updateUser, Instant createDatetime,
                                           Instant updateDatetime, String createUsername, String updateUsername,
                                           String createFullName, String updateFullName,
                                           String questionCode
                                           ) {
        this.id = id;
        this.campaignScriptId = campaignScriptId;
        this.campaignScriptQuestionId = campaignScriptQuestionId;
        this.code = code;
        this.type = type;
        this.position = position;
        this.display = display;
        this.content = content;
        this.min = min;
        this.max = max;
        this.status = status;
        this.createUserId = createUser;
        this.updateUserId = updateUser;
        this.createDatetime = createDatetime;
        this.updateDatetime = updateDatetime;
        this.createUsername = createUsername;
        this.updateUsername = updateUsername;
        this.createFullName = createFullName;
        this.updateFullName = updateFullName;
        this.questionCode = questionCode;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

    public String getUpdateFullName() {
        return updateFullName;
    }

    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public Long getCampaignScriptId() {
        return campaignScriptId;
    }

    public void setCampaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignScriptQuestionId() {
        return campaignScriptQuestionId;
    }

    public void setCampaignScriptQuestionId(Long campaignScriptQuestionId) {
        this.campaignScriptQuestionId = campaignScriptQuestionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    @Override
    public String toString() {
        return "CampaignScriptAnswerResponseDTO{" +
            "id=" + id +
            ", campaignScriptId=" + campaignScriptId +
            ", campaignScriptQuestionId=" + campaignScriptQuestionId +
            ", code='" + code + '\'' +
            ", type='" + type + '\'' +
            ", position=" + position +
            ", display='" + display + '\'' +
            ", content='" + content + '\'' +
            ", min=" + min +
            ", max=" + max +
            ", status='" + status + '\'' +
            ", createUserId=" + createUserId +
            ", updateUserId=" + updateUserId +
            ", createUsername='" + createUsername + '\'' +
            ", updateUsername='" + updateUsername + '\'' +
            ", createFullName='" + createFullName + '\'' +
            ", updateFullName='" + updateFullName + '\'' +
            ", createDatetime=" + createDatetime +
            ", updateDatetime=" + updateDatetime +
            '}';
    }
}
