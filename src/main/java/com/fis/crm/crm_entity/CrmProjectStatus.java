package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "CRM_PROJECT_STATUS", schema = "CRM_UAT", catalog = "")
public class CrmProjectStatus {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_PROJECT_SEQ_GEN")
    @SequenceGenerator(name = "CRM_PROJECT_SEQ_GEN", sequenceName = "CRM_PROJECT_SEQ", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Byte id;
    @Basic
    @Column(name = "NAME")
    private String name;

//    @OneToMany(mappedBy = "status")
//    private List<CrmProject> projects;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
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
        CrmProjectStatus that = (CrmProjectStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
