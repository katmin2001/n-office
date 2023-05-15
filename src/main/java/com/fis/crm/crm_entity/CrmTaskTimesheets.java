package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK_TIMESHEETS", schema = "CRM_UAT", catalog = "")
public class CrmTaskTimesheets {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATETIMESHEETS")
    private Date datetimesheets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskid")
    private CrmTask crmTask;
    public CrmTaskTimesheets() {

    }
    public CrmTaskTimesheets(Long id, CrmTask crmTask, String description, Date datetimesheets) {
        this.id = id;
        this.crmTask = crmTask;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTaskTimesheets that = (CrmTaskTimesheets) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(datetimesheets, that.datetimesheets) && Objects.equals(crmTask, that.crmTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, datetimesheets);
    }
}
