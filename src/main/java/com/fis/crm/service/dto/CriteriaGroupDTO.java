package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class CriteriaGroupDTO implements Serializable {

    private Long id;

    private String name;

    private Double scores;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private Instant updateDatetime;

    private Long updateUser;

    private List<CriteriaDTO> lstCriteriaDTO;


    public CriteriaGroupDTO() {
    }

    public CriteriaGroupDTO(Long id, String name, Double scores, String status, Instant createDatetime, Long createUser, Instant updateDatetime, Long updateUser) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.updateDatetime = updateDatetime;
        this.updateUser = updateUser;
    }

    public List<CriteriaDTO> getLstCriteriaDTO() {
        return lstCriteriaDTO;
    }

    public void setLstCriteriaDTO(List<CriteriaDTO> lstCriteriaDTO) {
        this.lstCriteriaDTO = lstCriteriaDTO;
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
