package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A CampaignTemplateDetail2.
 */
@Entity
@Table(name = "campaign_template_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignTemplateDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_TEMPLATE_DETAIL_SEQ")
    @SequenceGenerator(name = "CAMPAIGN_TEMPLATE_DETAIL_SEQ", sequenceName = "CAMPAIGN_TEMPLATE_DETAIL_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "ord")
    private Integer ord;

    @Column(name = "export_excel")
    private String exportExcel;

    @Column(name = "display")
    private String display;

    @Column(name = "editable")
    private String editable;

    @Column(name = "option_value")
    private String optionValue;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "is_default")
    private String isDefault;

    @Column(name = "edit_template")
    private String editTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignTemplateDetail id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCampaignId() {
        return this.campaignId;
    }

    public CampaignTemplateDetail campaignId(Long campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getType() {
        return this.type;
    }

    public CampaignTemplateDetail type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public CampaignTemplateDetail code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public CampaignTemplateDetail name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public CampaignTemplateDetail defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public CampaignTemplateDetail ord(Integer ord) {
        this.ord = ord;
        return this;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getExportExcel() {
        return this.exportExcel;
    }

    public CampaignTemplateDetail exportExcel(String exportExcel) {
        this.exportExcel = exportExcel;
        return this;
    }

    public void setExportExcel(String exportExcel) {
        this.exportExcel = exportExcel;
    }

    public String getDisplay() {
        return this.display;
    }

    public CampaignTemplateDetail display(String display) {
        this.display = display;
        return this;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEditable() {
        return this.editable;
    }

    public CampaignTemplateDetail editable(String editable) {
        this.editable = editable;
        return this;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public CampaignTemplateDetail optionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getStatus() {
        return this.status;
    }

    public CampaignTemplateDetail status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return this.createDatetime;
    }

    public CampaignTemplateDetail createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return this.createUser;
    }

    public CampaignTemplateDetail createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getEditTemplate() {
        return editTemplate;
    }

    public void setEditTemplate(String editTemplate) {
        this.editTemplate = editTemplate;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignTemplateDetail)) {
            return false;
        }
        return id != null && id.equals(((CampaignTemplateDetail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignTemplateDetail2{" +
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
