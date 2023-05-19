package com.fis.crm.crm_entity.DTO;

import java.sql.Date;


public class TaskUpdateDTO {
    private String taskname;
    private Long statuscode;
    private Long receivertaskid;
    private Date startdate;
    private Date enddate;
    private Long stageid;
    private Long projecid;

    public TaskUpdateDTO() {

    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Long getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Long statuscode) {
        this.statuscode = statuscode;
    }

    public Long getReceivertaskid() {
        return receivertaskid;
    }

    public void setReceivertaskid(Long receivertaskid) {
        this.receivertaskid = receivertaskid;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Long getStageid() {
        return stageid;
    }

    public void setStageid(Long stageid) {
        this.stageid = stageid;
    }

    public Long getProjecid() {
        return projecid;
    }

    public void setProjecid(Long projecid) {
        this.projecid = projecid;
    }
}
