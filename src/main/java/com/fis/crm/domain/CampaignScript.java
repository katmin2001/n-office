package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CampaignScript.
 */
@Entity
@Table(name = "campaign_script")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SCRIPT_GEN")
    @SequenceGenerator(name = "CAMPAIGN_SCRIPT_GEN", sequenceName = "CAMPAIGN_SCRIPT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "start_content")
    private String startContent;

    @Column(name = "end_content")
    private String endContent;

    //0: in use, 1: not use
    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user")
    private User createUser;

    @Column(name = "update_datetime")
    private Instant updateDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user")
    private User updateUser;

    @Column(name = "is_used")
    private String isUsed;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CampaignScript code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public CampaignScript name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartContent() {
        return startContent;
    }

    public CampaignScript startContent(String startContent) {
        this.startContent = startContent;
        return this;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }

    public String getEndContent() {
        return endContent;
    }

    public CampaignScript endContent(String endContent) {
        this.endContent = endContent;
        return this;
    }

    public void setEndContent(String endContent) {
        this.endContent = endContent;
    }

    public String getStatus() {
        return status;
    }

    public CampaignScript status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public CampaignScript createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public CampaignScript updateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignScript)) {
            return false;
        }
        return id != null && id.equals(((CampaignScript) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampaignScript{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", startContent='" + startContent + '\'' +
            ", endContent='" + endContent + '\'' +
            ", status='" + status + '\'' +
            ", createDatetime=" + createDatetime +
            ", createUser=" + createUser +
            ", updateDatetime=" + updateDatetime +
            ", updateUser=" + updateUser +
            '}';
    }
}
