package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CRM_PROJECT_PRIVACY")
public class CrmProjectPrivacy {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private char id;
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "privacy")
    private List<CrmProject> projects;
}
