package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK_STATUS", schema = "CRM_UAT", catalog = "")
public class CrmTaskStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "STATUSCODE")
    private Long statuscode;

    @Column(name = "STATUSNAME")
    private String statusname;

    public Long getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Long statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTaskStatus that = (CrmTaskStatus) o;
        return Objects.equals(statuscode, that.statuscode) && Objects.equals(statusname, that.statusname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuscode, statusname);
    }
}
