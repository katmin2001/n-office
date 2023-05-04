package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class CriteriaDetailDTO implements Serializable {

    private Long id;

    private Long criteriaId;

    private Long criteriaGroupId;

    private String content;

    private String description;

    private Double scores;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private Instant updateDatetime;

    private Long updateUser;

    private String note;

    public CriteriaDetailDTO() {
    }

    public CriteriaDetailDTO(Long id, Long criteriaId, Long criteriaGroupId, String content, String description, Double scores, String status, Instant createDatetime, Long createUser, Instant updateDatetime, Long updateUser) {
        this.id = id;
        this.criteriaId = criteriaId;
        this.criteriaGroupId = criteriaGroupId;
        this.content = content;
        this.description = description;
        this.scores = scores;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.updateDatetime = updateDatetime;
        this.updateUser = updateUser;
    }

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
