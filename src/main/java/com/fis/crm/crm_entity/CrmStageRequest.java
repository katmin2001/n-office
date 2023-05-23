package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "CRM_STAGE")
public class CrmStageRequest {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_STAGE_SEQ_GEN")
    @SequenceGenerator(name = "CRM_STAGE_SEQ_GEN", sequenceName = "CRM_STAGE_SEQ", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "FINISH_DATE")
    private Date finishDate;
    @Column(name = "PROJECT_ID")
    private Long projectId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
