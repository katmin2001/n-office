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
import java.time.Instant;

@Entity
@Table(name = "criteria_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CriteriaGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRITERIA_GROUP_GEN")
    @SequenceGenerator(name = "CRITERIA_GROUP_GEN", sequenceName = "CRITERIA_GROUP_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "scores")
    private Double scores;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_datetime")
    private Instant updateDatetime;

    @Column(name = "update_user")
    private Long updateUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScores() {
        return scores;
    }

    public void setScores(Double scores) {
        this.scores = scores;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}
