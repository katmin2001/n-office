package com.fis.crm.domain;

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
@Table(name = "criteria_detail")
public class CriteriaDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRITERIA_DETAIL_GEN")
    @SequenceGenerator(name = "CRITERIA_DETAIL_GEN", sequenceName = "CRITERIA_DETAIL_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "criteria_id")
    private Long criteriaId;

    @Column(name = "criteria_group_id")
    private Long criteriaGroupId;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private String description;

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

    public Long getCriteriaGroupId() {
        return criteriaGroupId;
    }

    public void setCriteriaGroupId(Long criteriaGroupId) {
        this.criteriaGroupId = criteriaGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Long criteriaId) {
        this.criteriaId = criteriaId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
