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

    @Column(name = "TASKID")
    private Long taskid;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<CrmTask> crmTasks;

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

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTaskHistory that = (CrmTaskHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(taskid, that.taskid) && Objects.equals(statusprev, that.statusprev) && Objects.equals(statuscurrent, that.statuscurrent) && Objects.equals(timecreate, that.timecreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskid, statusprev, statuscurrent, timecreate);
    }
}
