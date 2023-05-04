package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ConfigSchedule.
 */
@Entity
@Table(name = "option_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OptionSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_set_seq" )
    @SequenceGenerator(name = "option_set_seq" , allocationSize = 1)
    private Long optionSetId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "update_date")
    private Instant updateDate;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getOptionSetId() {
        return optionSetId;
    }

    public void setOptionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
    }

    public String getCode() {
        return code;
    }

    public OptionSet code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public OptionSet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public OptionSet fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }
    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public void setEndDate(Instant fromDate) {
        this.endDate = endDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public OptionSet endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OptionSet status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public OptionSet createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public OptionSet createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public OptionSet updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public OptionSet updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionSet)) {
            return false;
        }
        return optionSetId != null && optionSetId.equals(((OptionSet) o).optionSetId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionSet{" +
            "optionSetId=" + getOptionSetId() +
            ", code=" + getCode() + "'" +
            ", name=" + getName() + "'" +
            ", fromDate=" + getFromDate() +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createUser=" + getCreateUser() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
