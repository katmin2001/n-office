package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK_TIMESHEETS", schema = "CRM_UAT", catalog = "")
public class CrmTaskTimesheets {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_TASK_TIMESHEETS_SEQ_GEN")
    @SequenceGenerator(name = "CRM_TASK_TIMESHEETS_SEQ_GEN", sequenceName = "CRM_TASK_TIMESHEETS_SEQ", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATETIMESHEETS")
    private Date datetimesheets;

    @Column(name = "datecreated")
    private Date datecreated;

    @OneToOne
    @JoinColumn(name = "creator")
    private CrmUser user;

    @OneToOne
    @JoinColumn(name = "projectid")
    private CrmProject project;

    @OneToOne
    @JoinColumn(name = "taskid")
    private CrmTask task;
    public CrmTaskTimesheets() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatetimesheets() {
        return datetimesheets;
    }

    public void setDatetimesheets(Date datetimesheets) {
        this.datetimesheets = datetimesheets;
    }

    public CrmProject getProject() {
        return project;
    }

    public void setProject(CrmProject project) {
        this.project = project;
    }

    public CrmTask getTask() {
        return task;
    }

    public void setTask(CrmTask task) {
        this.task = task;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public CrmUser getUser() {
        return user;
    }

    public void setUser(CrmUser user) {
        this.user = user;
    }
}
