package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CRM_TASK_HISTORY", schema = "CRM_UAT", catalog = "")
public class CrmTaskHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToMany
    @JoinColumn(name = "taskid")
    private Set<CrmTask> taskHistory;

    @Column(name = "STATUSPREV")
    private Long statusprev;

    @Column(name = "STATUSCURRENT")
    private Long statuscurrent;

    @Column(name = "TIMECREATE")
    private Timestamp timecreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusprev() {
        return statusprev;
    }

    public void setStatusprev(Long statusprev) {
        this.statusprev = statusprev;
    }

    public Long getStatuscurrent() {
        return statuscurrent;
    }

    public void setStatuscurrent(Long statuscurrent) {
        this.statuscurrent = statuscurrent;
    }

    public Timestamp getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(Timestamp timecreate) {
        this.timecreate = timecreate;
    }

    public Set<CrmTask> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(Set<CrmTask> taskHistory) {
        this.taskHistory = taskHistory;
    }

}
