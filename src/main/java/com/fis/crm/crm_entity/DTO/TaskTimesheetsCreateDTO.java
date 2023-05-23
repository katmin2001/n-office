package com.fis.crm.crm_entity.DTO;


import java.util.Date;

public class TaskTimesheetsCreateDTO {
    private String description;
    private Date datetimesheets;
    private Date datecreated;
    private Long creatorid;
    private Long projectid;
    private Long taskid;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatetimesheets() {
        return datetimesheets;
    }

    public void setDatetimesheets(Date datetimesheets) {
        this.datetimesheets = datetimesheets;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Long getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Long creatorid) {
        this.creatorid = creatorid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }
}
