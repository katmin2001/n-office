package com.fis.crm.crm_entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "CRM_PROJECT_MEMBER")
public class CrmProjectMember {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private CrmProject project;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private CrmUser member;
}
