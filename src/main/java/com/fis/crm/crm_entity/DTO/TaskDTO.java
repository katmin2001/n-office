package com.fis.crm.crm_entity.DTO;

import javax.persistence.Entity;
import java.util.Date;


public class TaskDTO {
    private Long taskid;
    private String taskname;
    private Long statuscode;
    private String statusname;
    private Long givertaskid;
    private String givertaskname;
    private Long receivertaskid;
    private String receivertaskname;
    private Date startdate;
    private Date enddate;
    private Long projecid;
    private String projectname;


    public TaskDTO() {

    }

    public TaskDTO(Long taskid, String taskname, Long statuscode, Long givertaskid, Long receivertaskid, Date startdate, Date enddate, Long projecid) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.statuscode = statuscode;
        this.givertaskid = givertaskid;
        this.receivertaskid = receivertaskid;
        this.startdate = startdate;
        this.enddate = enddate;
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


    public Long getProjecid() {
        return projecid;
    }

    public void setProjecid(Long projecid) {
        this.projecid = projecid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getGivertaskname() {
        return givertaskname;
    }

    public void setGivertaskname(String givertaskname) {
        this.givertaskname = givertaskname;
    }

    public String getReceivertaskname() {
        return receivertaskname;
    }

    public void setReceivertaskname(String receivertaskname) {
        this.receivertaskname = receivertaskname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
