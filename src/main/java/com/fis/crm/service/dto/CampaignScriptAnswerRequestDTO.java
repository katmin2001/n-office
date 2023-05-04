package com.fis.crm.service.dto;

import javax.validation.constraints.NotNull;

public class CampaignScriptAnswerRequestDTO {
    private Long id;

//    @NotNull(message = "Param campaignScriptId is required!")
//    private Long campaignScriptId;

    private Long campaignScriptQuestionId;

    private String code;

    @NotNull(message = "Param type is required!")
    private String type;

    private Integer position;

//    @NotNull(message = "Param display is required!")
    private String display;

    @NotNull(message = "Param content is required!")
    private String content;

    private Integer min;

    private Integer max;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "CampaignScriptAnswerRequestDTO{" +
            "id=" + id +
            ", campaignScriptQuestionId=" + campaignScriptQuestionId +
            ", type='" + type + '\'' +
            ", position=" + position +
            ", display='" + display + '\'' +
            ", content='" + content + '\'' +
            ", min=" + min +
            ", max=" + max +
            ", status='" + status + '\'' +
            '}';
    }
}
