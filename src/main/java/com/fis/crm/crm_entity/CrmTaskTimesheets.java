package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
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

    @Column(name = "projectid")
    private Long projectid;

    @Column(name = "taskid")
    private Long taskid;
    public CrmTaskTimesheets() {

    }
    public CrmTaskTimesheets(Long id, Long crmTask, String description, Date datetimesheets) {
        this.id = id;
        this.taskid = crmTask;
        this.description = description;
        this.datetimesheets = datetimesheets;
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

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }
}
