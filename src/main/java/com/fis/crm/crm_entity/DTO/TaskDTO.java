package com.fis.crm.crm_entity.DTO;

import javax.persistence.Entity;
import java.sql.Date;


public class TaskDTO {
    private Long taskid;
    private String taskname;
    private Long statuscode;
    private Long givertaskid;
    private Long receivertaskid;
    private Date startdate;
    private Date enddate;
    private Long stageid;
    private Long projecid;


    public TaskDTO() {

    }

    public TaskDTO(Long taskid, String taskname, Long statuscode, Long givertaskid, Long receivertaskid, Date startdate, Date enddate, Long stageid, Long projecid) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.statuscode = statuscode;
        this.givertaskid = givertaskid;
        this.receivertaskid = receivertaskid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.stageid = stageid;
        this.projecid = projecid;
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

    public Long getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Long statuscode) {
        this.statuscode = statuscode;
    }

    public Long getGivertaskid() {
        return givertaskid;
    }

    public void setGivertaskid(Long givertaskid) {
        this.givertaskid = givertaskid;
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
