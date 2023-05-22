package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Date;
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

    @OneToOne
    @JoinColumn(name = "projectid")
    private CrmProject project;
    @OneToOne
    @JoinColumn(name = "GIVERTASKID")
    private CrmUser givertask;
    @OneToOne
    @JoinColumn(name = "RECEIVERTASKID")
    private CrmUser receivertask;
    @Column(name = "STARTDATE")
    private Date startdate;
    @Column(name = "ENDDATE")
    private Date enddate;

    @OneToOne
    @JoinColumn(name = "statuscode")
    private CrmTaskStatus status;
    @OneToOne
    @JoinColumn(name = "stageid")
    private CrmStage stage;

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

    public CrmProject getProject() {
        return project;
    }

    public void setProject(CrmProject project) {
        this.project = project;
    }

    public CrmUser getGivertask() {
        return givertask;
    }

    public void setGivertask(CrmUser givertask) {
        this.givertask = givertask;
    }

    public CrmUser getReceivertask() {
        return receivertask;
    }

    public void setReceivertask(CrmUser receivertask) {
        this.receivertask = receivertask;
    }

    public CrmTaskStatus getStatus() {
        return status;
    }

    public void setStatus(CrmTaskStatus status) {
        this.status = status;
    }

    public CrmStage getStage() {
        return stage;
    }

    public void setStage(CrmStage stage) {
        this.stage = stage;
    }
}
