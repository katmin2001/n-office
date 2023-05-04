package com.fis.crm.service.dto;

import java.time.Instant;

public class CampaignScriptQuestionResponseDTO {
    private Long id;

    private Long campaignScriptId;

    private String campaignScriptName;

    private String code;

    private Integer position;

    private String display;

    private String content;

    private String status;

    private Instant createDatetime;

    private Instant updateDatetime;

    private Long createUserId;

    private Long updateUserId;

    private String createUsername;

    private String updateUsername;

    private String createFullName;

    private String updateFullName;

    private String startContent;

    private String endContent;

    public CampaignScriptQuestionResponseDTO() {
    }

    public CampaignScriptQuestionResponseDTO(Long id, Long campaignScriptId, String campaignScriptName, String code, Integer position, String display, String content, String status, Instant createDatetime, Instant updateDatetime, Long createUser, Long updateUser,
                                             String createUsername, String updateUsername,
                                             String createFullName, String updateFullName) {
        this.id = id;
        this.campaignScriptId = campaignScriptId;
        this.campaignScriptName = campaignScriptName;
        this.code = code;
        this.position = position;
        this.display = display;
        this.content = content;
        this.status = status;
        this.createDatetime = createDatetime;
        this.updateDatetime = updateDatetime;
        this.createUserId = createUser;
        this.updateUserId = updateUser;
        this.createUsername = createUsername;
        this.updateUsername = updateUsername;
        this.createFullName = createFullName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCampaignScriptName() {
        return campaignScriptName;
    }

    public void setCampaignScriptName(String campaignScriptName) {
        this.campaignScriptName = campaignScriptName;
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

    public String getStartContent() {
        return startContent;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }

    public String getEndContent() {
        return endContent;
    }

    public void setEndContent(String endContent) {
        this.endContent = endContent;
    }

    @Override
    public String toString() {
        return "CampaignScriptQuestionResponseDTO{" +
            "id=" + id +
            ", campaignScriptId=" + campaignScriptId +
            ", campaignScriptName='" + campaignScriptName + '\'' +
            ", code='" + code + '\'' +
            ", position=" + position +
            ", display='" + display + '\'' +
            ", content='" + content + '\'' +
            ", status='" + status + '\'' +
            ", createDatetime=" + createDatetime +
            ", updateDatetime=" + updateDatetime +
            ", createUserId=" + createUserId +
            ", updateUserId=" + updateUserId +
            ", createUsername='" + createUsername + '\'' +
            ", updateUsername='" + updateUsername + '\'' +
            ", createFullName='" + createFullName + '\'' +
            ", updateFullName='" + updateFullName + '\'' +
            '}';
    }
}
