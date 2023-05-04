package com.fis.crm.service.dto;

import java.time.Instant;
import java.util.List;

public class CriteriaDTO {

    private Long id;

    private Long criteriaGroupId;

    private String name;

    private Double scores;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private Instant updateDatetime;

    private Long updateUser;

    private List<CriteriaDetailDTO> criteriaDetailDTOList;

    private Double restScore;

    public CriteriaDTO() {
    }

    public Double getRestScore() {
        return restScore;
    }

    public void setRestScore(Double restScore) {
        this.restScore = restScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCriteriaGroupId() {
        return criteriaGroupId;
    }

    public void setCriteriaGroupId(Long criteriaGroupId) {
        this.criteriaGroupId = criteriaGroupId;
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

    public List<CriteriaDetailDTO> getCriteriaDetailDTOList() {
        return criteriaDetailDTOList;
    }

    public void setCriteriaDetailDTOList(List<CriteriaDetailDTO> criteriaDetailDTOList) {
        this.criteriaDetailDTOList = criteriaDetailDTOList;
    }
}
