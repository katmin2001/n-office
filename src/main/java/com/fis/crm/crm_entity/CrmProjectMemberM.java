package com.fis.crm.crm_entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_PROJECT_MEMBER")
public class CrmProjectMemberM {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private CrmProject project;
    @Column(name = "project_id")
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private CrmUser member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrmUser getMember() {
        return member;
    }

    public void setMember(CrmUser member) {
        this.member = member;
    }
}
