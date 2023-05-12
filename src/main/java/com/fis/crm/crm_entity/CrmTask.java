package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CRM_TASK", schema = "CRM_UAT", catalog = "")
public class CrmTask {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "TASKID", nullable = false)
    private Long taskid;

    @Column(name = "TASKNAME")
    private String taskname;

    @Column(name = "STARTDATE")
    private Date startdate;

    @Column(name = "ENDDATE")
    private Date enddate;

    @OneToOne
    @JoinColumn(name = "statuscode")
    private CrmTaskStatus taskStatus;
    @ManyToOne
    @JoinColumn(name = "givertaskid")
    private CrmUser giverTask;
    @ManyToOne
    @JoinColumn(name = "receivertaskid")
    private CrmUser receiverTask;
    @ManyToOne
    @JoinColumn(name = "stageid")
    private CrmStage taskStage;
    @ManyToOne
    @JoinColumn(name = "projectid")
    private CrmProject projectTask;

    @ManyToMany(mappedBy = "taskHistory")
    private Set<CrmTaskHistory> taskHistories;

    public  CrmTask() {
    }

    public CrmTask(Long taskid, String taskname, Date startdate, Date enddate) {
        this.taskid = taskid;
        this.taskname = taskname;
        this.startdate = startdate;
        this.enddate = enddate;
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

    public CrmTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(CrmTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public CrmUser getGiverTask() {
        return giverTask;
    }

    public void setGiverTask(CrmUser giverTask) {
        this.giverTask = giverTask;
    }

    public CrmUser getReceiverTask() {
        return receiverTask;
    }

    public void setReceiverTask(CrmUser receiverTask) {
        this.receiverTask = receiverTask;
    }

    public CrmStage getTaskStage() {
        return taskStage;
    }

    public void setTaskStage(CrmStage taskStage) {
        this.taskStage = taskStage;
    }

    public CrmProject getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(CrmProject projectTask) {
        this.projectTask = projectTask;
    }
}
