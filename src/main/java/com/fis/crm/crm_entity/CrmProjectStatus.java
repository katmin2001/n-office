package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CRM_PROJECT_STATUS")
public class CrmProjectStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Byte id;
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
}
