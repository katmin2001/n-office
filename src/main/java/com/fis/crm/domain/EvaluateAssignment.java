package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A EvaluateAssignment.
 */
@Entity
@Table(name = "evaluate_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluateAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_ASSIGNMENT_GEN")
    @SequenceGenerator(name = "EVALUATE_ASSIGNMENT_GEN", sequenceName = "EVALUATE_ASSIGNMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "evaluater_id")
    private Long evaluaterId;

    @Column(name = "BUSSINESS_TYPE_ID")
    private Long businessTypeId;

    @Column(name = "total_call")
    private Long totalCall;

    @Column(name = "total_user_id")
    private Long totalUserId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "EVALUATE_STATUS")
    private String evaluateStatus;

    @Column(name = "create_datetime")
    private Date createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_datetime")
    private Date updateDatetime;

    @Column(name = "update_user")
    private Long updateUser;

    @OneToMany(
        mappedBy = "evaluateAssignment",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<EvaluateAssignmentDetail> evaluateAssignmentDetails = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public EvaluateAssignment channelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Long getEvaluaterId() {
        return evaluaterId;
    }

    public EvaluateAssignment evaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
        return this;
    }

    public void setEvaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
    }

    public Long getTotalCall() {
        return totalCall;
    }


    public void setTotalCall(Long totalCall) {
        this.totalCall = totalCall;
    }

    public Long getTotalUserId() {
        return totalUserId;
    }

    public EvaluateAssignment totalUserId(Long totalUserId) {
        this.totalUserId = totalUserId;
        return this;
    }

    public void setTotalUserId(Long totalUserId) {
        this.totalUserId = totalUserId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public EvaluateAssignment startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public EvaluateAssignment endDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public EvaluateAssignment createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public EvaluateAssignment createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public EvaluateAssignment updateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public EvaluateAssignment updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public List<EvaluateAssignmentDetail> getEvaluateAssignmentDetails() {
        return evaluateAssignmentDetails;
    }

    public void setEvaluateAssignmentDetails(List<EvaluateAssignmentDetail> evaluateAssignmentDetails) {
        this.evaluateAssignmentDetails = evaluateAssignmentDetails;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateAssignment)) {
            return false;
        }
        return id != null && id.equals(((EvaluateAssignment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateAssignment{" +
            "id=" + getId() +
            ", channelId='" + getChannelId() + "'" +
            ", evaluaterId=" + getEvaluaterId() +
            ", businessTypeId=" + getBusinessTypeId() +
            ", totalCal=" + getTotalCall() +
            ", totalUserId=" + getTotalUserId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", evaluateStatus='" + getEvaluateStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
