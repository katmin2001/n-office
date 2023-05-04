package com.fis.crm.service.dto;

import javax.validation.constraints.NotNull;

public class CampaignScriptQuestionRequestDTO {
    private Long id;

    private String code;

//    @NotNull(message = "Param campaignScriptId is required!")
    private Long campaignScriptId;

    private Integer position;

    @NotNull(message = "Param display is required!")
    private String display;

    @NotNull(message = "Param content is required!")
    private String content;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "CampaignScriptQuestionRequestDTO{" +
            "campaignScriptId=" + campaignScriptId +
            ", position=" + position +
            ", display='" + display + '\'' +
            ", content='" + content + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
