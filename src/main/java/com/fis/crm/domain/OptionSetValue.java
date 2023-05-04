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
@Table(name = "option_set_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OptionSetValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "option_set_value_seq" )
    @SequenceGenerator(name = "option_set_value_seq" , allocationSize = 1)
    private Long id;

    @Column(name = "option_set_id")
    private Long optionSetId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "english_Name")
    private String englishName;

    @Column(name = "ord")
    private Integer ord;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "CREATE_DATE")
    private Instant createDate;

    @Column(name = "UPDATE_USER")
    private Long updateUser;

    @Column(name = "UPDATE_DATE")
    private Instant updateDate;

    @Column(name = "c1")
    private String c1;

    @Column(name = "c2")
    private String c2;


    public void setOptionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptionSetId() {
        return optionSetId;
    }

    public OptionSetValue optionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
        return this;
    }

    public void setoptionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
    }

    public String getCode() {
        return code;
    }

    public OptionSetValue code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public OptionSetValue name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public OptionSetValue fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Integer getOrd() {
        return ord;
    }

    public OptionSetValue ord(Integer ord) {
        this.ord = ord;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public OptionSetValue groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setEndDate(Instant fromDate) {
        this.endDate = endDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public OptionSetValue endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }



    public String getStatus() {
        return status;
    }

    public OptionSetValue status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public OptionSetValue createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public OptionSetValue createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public OptionSetValue updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public OptionSetValue updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getC1() {
        return c1;
    }

    public OptionSetValue c1(String c1) {
        this.c1 = c1;
        return this;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public OptionSetValue c2(String c2) {
        this.c2 = c2;
        return this;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionSetValue)) {
            return false;
        }
        return id != null && id.equals(((OptionSetValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionSetValue{" +
            "id=" + getId() +
            "optionSetId="+getOptionSetId()+
            ", code=" + getCode() + "'" +
            ", name=" + getName() + "'" +
            ", ord=" + getOrd() +
            ", groupName=" + getGroupName() + "'" +
            ", fromDate=" + getFromDate() +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createUser=" + getCreateUser() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", c1=" + getC1() + "'" +
            ", c2=" + getC2() + "'" +
            "}";
    }
}
