package com.fis.crm.crm_entity.DTO;


import java.util.Date;

public class TaskTimesheetsDTO {
    private Long id;
    private String description;
    private Date datetimesheets;
    private Date datecreated;
    private Long creatorid;
    private String creator;
    private Long projectid;
    private String projectname;
    private Long taskid;
    private String taskname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
