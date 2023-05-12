package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "CRM_PROJECT")
public class CrmProject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE")
    private String code;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "FINISH_DATE")
    private Date finishDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CrmCustomer customer;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private CrmUser manager;

    @ManyToOne
    @JoinColumn(name = "privacy_id")
    private CrmProjectPrivacy privacy;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private CrmProjectStatus status;
    @OneToMany(mappedBy = "project")
    private List<CrmStage> stages;
    @OneToMany(mappedBy = "project")
    private List<CrmProjectMember> members;
}
