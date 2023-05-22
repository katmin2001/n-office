package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CRM_TASK_STATUS", schema = "CRM_UAT", catalog = "")
public class CrmTaskStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "statuscode", nullable = false)
    private Long id;

    @Column(name = "statusname")
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTaskStatus that = (CrmTaskStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
