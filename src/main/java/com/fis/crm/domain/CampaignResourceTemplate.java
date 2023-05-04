package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CAMPAIGN_RESOURCE_TEMPLATE")
public class CampaignResourceTemplate {
    private Long id;
    private Long campaignId;
    private Long campaignResourceId;
    private Long campaignResourceDetailId;
    private String type;
    private String editable;
    private String code;
    private String name;
    private Integer ord;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_RESOURCE_TEMPLATE_SEQ")
    @SequenceGenerator(name = "CAMPAIGN_RESOURCE_TEMPLATE_SEQ", sequenceName = "CAMPAIGN_RESOURCE_TEMPLATE_SEQ", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Column(name = "CAMPAIGN_RESOURCE_ID")
    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
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
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "EDITABLE")
    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
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
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ORD")
    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
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
        CampaignResourceTemplate that = (CampaignResourceTemplate) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(campaignId, that.campaignId) &&
            Objects.equals(campaignResourceId, that.campaignResourceId) &&
            Objects.equals(campaignResourceDetailId, that.campaignResourceDetailId) &&
            Objects.equals(type, that.type) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(ord, that.ord) &&
            Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campaignId, campaignResourceId, campaignResourceDetailId, type, code, name, ord, value);
    }
}
