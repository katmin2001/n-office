package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK", schema = "CRM_UAT", catalog = "")
public class CrmTask {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_TASK_SEQ_GEN")
    @SequenceGenerator(name = "CRM_TASK_SEQ_GEN", sequenceName = "CRM_TASK_SEQ", allocationSize = 1)
    @Id
    @Column(name = "TASKID")
    private Long taskid;
    @Column(name = "TASKNAME")
    private String taskname;

    @Column(name = "projectid")
    private Long projectid;

    @Column(name = "GIVERTASKID")
    private Long givertaskid;

    @Column(name = "RECEIVERTASKID")
    private Long receivertaskid;
    @Column(name = "STARTDATE")
    private Date startdate;
    @Column(name = "ENDDATE")
    private Date enddate;

    @Column(name = "statuscode")
    private Long statuscode;

    @Column(name = "stageid")
    private Long stageid;

    public  CrmTask() {
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

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
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

    public Long getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Long statuscode) {
        this.statuscode = statuscode;
    }

    public Long getStageid() {
        return stageid;
    }

    public void setStageid(Long stageid) {
        this.stageid = stageid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTask crmTask = (CrmTask) o;
        return Objects.equals(taskid, crmTask.taskid) && Objects.equals(taskname, crmTask.taskname) && Objects.equals(projectid, crmTask.projectid) && Objects.equals(givertaskid, crmTask.givertaskid) && Objects.equals(receivertaskid, crmTask.receivertaskid) && Objects.equals(startdate, crmTask.startdate) && Objects.equals(enddate, crmTask.enddate) && Objects.equals(statuscode, crmTask.statuscode) && Objects.equals(stageid, crmTask.stageid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskid, taskname, projectid, givertaskid, receivertaskid, startdate, enddate, statuscode, stageid);
    }
}
