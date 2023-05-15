package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "CRM_STAGE")
public class CrmStage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STARTDATE")
    private Date startdate;
    @Column(name = "ENDDATE")
    private Date enddate;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private CrmProject project;
}
