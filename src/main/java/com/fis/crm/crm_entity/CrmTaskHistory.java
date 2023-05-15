package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Timestamp;

import java.util.List;

@Entity
@Table(name = "CRM_TASK_HISTORY", schema = "CRM_UAT", catalog = "")
public class CrmTaskHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "taskid")
    private CrmTask taskHistory;

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


}
