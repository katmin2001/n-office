package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class CriteriaRatingDTO implements Serializable {

    private Long id;

    private String name;

    private Double fromScores;

    private Double toScores;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private Instant updateDatetime;

    private Long updateUser;


    public CriteriaRatingDTO() {
    }

    public CriteriaRatingDTO(Long id, String name, Double fromScores, Double toScores, String status, Instant createDatetime, Long createUser, Instant updateDatetime, Long updateUser) {
        this.id = id;
        this.name = name;
        this.fromScores = fromScores;
        this.toScores = toScores;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.updateDatetime = updateDatetime;
        this.updateUser = updateUser;
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

    public void setName(String name) {
        this.name = name;
    }

    public Double getFromScores() {
        return fromScores;
    }

    public void setFromScores(Double fromScores) {
        this.fromScores = fromScores;
    }

    public Double getToScores() {
        return toScores;
    }

    public void setToScores(Double toScores) {
        this.toScores = toScores;
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

    @Override
    public String toString() {
        return "CriteriaRatingDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", fromScores=" + fromScores +
            ", toScores=" + toScores +
            ", status='" + status + '\'' +
            ", createDatetime=" + createDatetime +
            ", createUser=" + createUser +
            ", updateDatetime=" + updateDatetime +
            ", updateUser=" + updateUser +
            '}';
    }
}
