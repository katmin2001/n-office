package com.fis.crm.crm_entity;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CRM_TASK_HISTORY", schema = "CRM_UAT", catalog = "")
public class CrmTaskHistory {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_TASK_HISTORY_SEQ_GEN")
    @SequenceGenerator(name = "CRM_TASK_HISTORY_SEQ_GEN", sequenceName = "CRM_TASK_HISTORY_SEQ", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Long id;


    @Column(name = "taskid")
    private Long taskid;
    @OneToOne
    @JoinColumn(name = "STATUSPREV")
    private CrmTaskStatus statusprev;

    @OneToOne
    @JoinColumn(name = "STATUSCURRENT")
    private CrmTaskStatus statuscurrent;

    @Column(name = "TIMECREATE")
    private Date timecreate;

    public CrmTaskHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmTaskStatus getStatusprev() {
        return statusprev;
    }

    public void setStatusprev(CrmTaskStatus statusprev) {
        this.statusprev = statusprev;
    }

    public CrmTaskStatus getStatuscurrent() {
        return statuscurrent;
    }

    public void setStatuscurrent(CrmTaskStatus statuscurrent) {
        this.statuscurrent = statuscurrent;
    }

    public Date getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(Date timecreate) {
        this.timecreate = timecreate;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }
}
