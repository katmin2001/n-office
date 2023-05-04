package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A CampaignTemplateOption.
 */
@Entity
@Table(name = "campaign_template_option")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignTemplateOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_TEMPLATE_OPTION_SEQ")
    @SequenceGenerator(name = "CAMPAIGN_TEMPLATE_OPTION_SEQ", sequenceName = "CAMPAIGN_TEMPLATE_OPTION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "campaign_template_detail_id")
    private Long campaignTemplateDetailId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignTemplateOption id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCampaignTemplateId() {
        return this.campaignTemplateDetailId;
    }

    public CampaignTemplateOption campaignTemplateId(Long campaignTemplateId) {
        this.campaignTemplateDetailId = campaignTemplateId;
        return this;
    }

    public void setCampaignTemplateId(Long campaignTemplateId) {
        this.campaignTemplateDetailId = campaignTemplateId;
    }

    public String getCode() {
        return this.code;
    }

    public CampaignTemplateOption code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public CampaignTemplateOption name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public CampaignTemplateOption status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignTemplateOption)) {
            return false;
        }
        return id != null && id.equals(((CampaignTemplateOption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignTemplateOption{" +
            "id=" + getId() +
            ", campaignTemplateId=" + getCampaignTemplateId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
