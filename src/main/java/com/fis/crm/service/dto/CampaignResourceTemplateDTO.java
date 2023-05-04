package com.fis.crm.service.dto;

import java.util.List;

public class CampaignResourceTemplateDTO {
    private Long id;
    private Long campaignId;
    private Long campaignResourceId;
    private Long campaignResourceDetailId;
    private String type;
    private String code;
    private String name;
    private Integer ord;
    private String value;
    private String editable;
    private List<CampaignTemplateOptionDTO> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
    }

    public Long getCampaignResourceDetailId() {
        return campaignResourceDetailId;
    }

    public void setCampaignResourceDetailId(Long campaignResourceDetailId) {
        this.campaignResourceDetailId = campaignResourceDetailId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CampaignTemplateOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<CampaignTemplateOptionDTO> options) {
        this.options = options;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }
}
