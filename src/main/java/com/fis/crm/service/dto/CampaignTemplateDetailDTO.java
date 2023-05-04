package com.fis.crm.service.dto;

import com.fis.crm.domain.CampaignTemplateDetail;
import com.fis.crm.domain.CampaignTemplateOption;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link CampaignTemplateDetail} entity.
 */
public class CampaignTemplateDetailDTO implements Serializable {

    private Long id;

    private Long campaignId;

    private String type;

    private String code;

    private String name;

    private String defaultValue;

    private Integer ord;

    private String exportExcel;

    private String display;

    private String editable;

    private String optionValue;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private List<CampaignTemplateOption> optionValues;

    private String typeName;

    private String campaignName;

    private String isDefault;

    private String editTemplate;

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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getExportExcel() {
        return exportExcel;
    }

    public void setExportExcel(String exportExcel) {
        this.exportExcel = exportExcel;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public List<CampaignTemplateOption> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<CampaignTemplateOption> optionValues) {
        this.optionValues = optionValues;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getEditTemplate() {
        return editTemplate;
    }

    public void setEditTemplate(String editTemplate) {
        this.editTemplate = editTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignTemplateDetailDTO)) {
            return false;
        }

        CampaignTemplateDetailDTO campaignTemplateDetailDTO = (CampaignTemplateDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, campaignTemplateDetailDTO.id);
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignTemplateDetail2DTO{" +
            "id=" + getId() +
            ", campaignId=" + getCampaignId() +
            ", type='" + getType() + "'" +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", ord=" + getOrd() +
            ", exportExcel='" + getExportExcel() + "'" +
            ", display='" + getDisplay() + "'" +
            ", editable='" + getEditable() + "'" +
            ", optionValue='" + getOptionValue() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            "}";
    }
}
