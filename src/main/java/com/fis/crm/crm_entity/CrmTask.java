package com.fis.crm.crm_entity;

import com.fis.crm.domain.User;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK", schema = "CRM_UAT", catalog = "")
public class CrmTask {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TASKID")
    private Long taskid;

    @Column(name = "TASKNAME")
    private String taskname;

    @Column(name = "STATUSCODE")
    private Long statuscode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statuscode")
    private CrmTaskStatus crmTaskStatus;

    @Column(name = "GIVERTASKID")
    private Long givertaskid;

    @Column(name = "RECEIVERTASKID")
    private Long receivertaskid;

    @Column(name = "STARTDATE")
    private Date startdate;

    @Column(name = "ENDDATE")
    private Date enddate;

    public Long getTaskid() {
        return taskid;
    }

    public  CrmTask() {

    }
    public CrmTask(Long taskid, String taskname, Long statuscode, CrmTaskStatus crmTaskStatus, Long givertaskid, Long receivertaskid, Date startdate, Date enddate) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.statuscode = statuscode;
        this.crmTaskStatus = crmTaskStatus;
        this.givertaskid = givertaskid;
        this.receivertaskid = receivertaskid;
        this.startdate = startdate;
        this.enddate = enddate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTask crmTask = (CrmTask) o;
        return Objects.equals(taskid, crmTask.taskid) && Objects.equals(taskname, crmTask.taskname) && Objects.equals(statuscode, crmTask.statuscode) && Objects.equals(givertaskid, crmTask.givertaskid) && Objects.equals(receivertaskid, crmTask.receivertaskid) && Objects.equals(startdate, crmTask.startdate) && Objects.equals(enddate, crmTask.enddate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskid, taskname, statuscode, givertaskid, receivertaskid, startdate, enddate);
    }
}
