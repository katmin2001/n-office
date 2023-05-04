package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A CampaignTemplate.
 */
@Entity
@Table(name = "campaign_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_TEMPLATE_SEQ")
    @SequenceGenerator(name = "CAMPAIGN_TEMPLATE_SEQ", sequenceName = "CAMPAIGN_TEMPLATE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "campaign_name")
    private String campaignName;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Date createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignTemplate id(Long id) {
        this.id = id;
        return this;
    }

    public String getCampaignName() {
        return this.campaignName;
    }

    public CampaignTemplate campaignName(String campaignName) {
        this.campaignName = campaignName;
        return this;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getStatus() {
        return this.status;
    }

    public CampaignTemplate status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public CampaignTemplate createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return this.createUser;
    }

    public CampaignTemplate createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignTemplate)) {
            return false;
        }
        return id != null && id.equals(((CampaignTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignTemplate{" +
            "id=" + getId() +
            ", campaignName='" + getCampaignName() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            "}";
    }
}
