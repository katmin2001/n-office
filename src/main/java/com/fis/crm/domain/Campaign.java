package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_GEN")
    @SequenceGenerator(name = "CAMPAIGN_GEN", sequenceName = "CAMPAIGN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "campaign_script_id")
    private Long campaignScriptId;

    @Column(name = "campaign_template_id")
    private Long campaignTemplateId;

    @Column(name = "group_user")
    private String groupUser;

    @Column(name = "display_phone")
    private String displayPhone;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Date createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_datetime")
    private Date updateDatetime;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "list_call_status")
    private String listCallStatus;

    @Column(name = "null_call_fail")
    private Integer nullCallFail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getListCallStatus() {
        return listCallStatus;
    }

    public void setListCallStatus(String listCallStatus) {
        this.listCallStatus = listCallStatus;
    }

    public Integer getNullCallFail() {
        return nullCallFail;
    }

    public void setNullCallFail(Integer nullCallFail) {
        this.nullCallFail = nullCallFail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Campaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCampaignScriptId() {
        return campaignScriptId;
    }

    public Campaign campaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
        return this;
    }

    public void setCampaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
    }

    public Long getCampaignTemplateId() {
        return campaignTemplateId;
    }

    public Campaign campaignTemplateId(Long campaignTemplateId) {
        this.campaignTemplateId = campaignTemplateId;
        return this;
    }

    public void setCampaignTemplateId(Long campaignTemplateId) {
        this.campaignTemplateId = campaignTemplateId;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public Campaign groupUser(String groupUser) {
        this.groupUser = groupUser;
        return this;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public Campaign displayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
        return this;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Campaign startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Campaign endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public Campaign status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public Campaign createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public Campaign createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public Campaign updateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public Campaign updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", campaignScriptId=" + getCampaignScriptId() +
            ", campaignTemplateId=" + getCampaignTemplateId() +
            ", groupUser='" + getGroupUser() + "'" +
            ", displayPhone='" + getDisplayPhone() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
