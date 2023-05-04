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
@Table(name = "config_schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_schedule_seq" )
    @SequenceGenerator(name = "config_schedule_seq" , allocationSize = 1)
    private Long id;

    @Column(name = "REQUEST_TYPE")
    private Integer requestType;

    @Column(name = "BUSSINESS_TYPE")
    private Integer bussinessType;

    @Column(name = "PROCESS_TIME")
    private Double processTime;

    @Column(name = "PROCESS_TIME_TYPE")
    private String processTimeType;

    @Column(name = "CONFIRM_TIME")
    private Double confirmTime;

    @Column(name = "CONFIRM_TIME_TYPE")
    private String confirmTimeType;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "CREATE_DATE")
    private Instant createDate;

    @Column(name = "UPDATE_USER")
    private Long updateUser;

    @Column(name = "UPDATE_DATE")
    private Instant updateDate;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public ConfigSchedule requestType(Integer requestType) {
        this.requestType = requestType;
        return this;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getBussinessType() {
        return bussinessType;
    }

    public ConfigSchedule bussinessType(Integer bussinessType) {
        this.bussinessType = bussinessType;
        return this;
    }

    public void setBussinessType(Integer bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Double getProcessTime() {
        return processTime;
    }

    public ConfigSchedule processTime(Double processTime) {
        this.processTime = processTime;
        return this;
    }

    public void setProcessTime(Double processTime) {
        this.processTime = processTime;
    }

    public String getProcessTimeType() {
        return processTimeType;
    }

    public ConfigSchedule processTimeType(String processTimeType) {
        this.processTimeType = processTimeType;
        return this;
    }

    public void setProcessTimeType(String processTimeType) {
        this.processTimeType = processTimeType;
    }

    public Double getConfirmTime() {
        return confirmTime;
    }

    public ConfigSchedule confirmTime(Double confirmTime) {
        this.confirmTime = confirmTime;
        return this;
    }

    public void setConfirmTime(Double confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmTimeType() {
        return confirmTimeType;
    }

    public ConfigSchedule confirmTimeType(String confirmTimeType) {
        this.confirmTimeType = confirmTimeType;
        return this;
    }

    public void setConfirmTimeType(String confirmTimeType) {
        this.confirmTimeType = confirmTimeType;
    }

    public String getStatus() {
        return status;
    }

    public ConfigSchedule status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public ConfigSchedule createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public ConfigSchedule createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public ConfigSchedule updateUser(Long updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public ConfigSchedule updateDate(Instant updateDate) {
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
        if (!(o instanceof ConfigSchedule)) {
            return false;
        }
        return id != null && id.equals(((ConfigSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigSchedule{" +
            "id=" + getId() +
            ", requestType=" + getRequestType() +
            ", bussinessType=" + getBussinessType() +
            ", processTime=" + getProcessTime() +
            ", processTimeType='" + getProcessTimeType() + "'" +
            ", confirmTime=" + getConfirmTime() +
            ", confirmTimeType='" + getConfirmTimeType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createUser=" + getCreateUser() +
            ", createDate='" + getCreateDate() + "'" +
            ", updateUser=" + getUpdateUser() +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
