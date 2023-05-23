package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CRM_PROJECT_PRIVACY", schema = "CRM_UAT", catalog = "")
public class CrmProjectPrivacy {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Byte id;
    @Basic
    @Column(name = "NAME")
    private String name;
//    @OneToMany(mappedBy = "privacy")
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
        CrmProjectPrivacy that = (CrmProjectPrivacy) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
