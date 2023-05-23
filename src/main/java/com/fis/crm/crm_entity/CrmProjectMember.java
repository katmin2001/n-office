package com.fis.crm.crm_entity;

import javax.persistence.*;
@Entity
@Table(name = "CRM_PROJECT_MEMBER")
public class CrmProjectMember {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_PROJECT_MEMBER_SEQ_GEN")
    @SequenceGenerator(name = "CRM_PROJECT_MEMBER_SEQ_GEN", sequenceName = "CRM_PROJECT_MEMBER_SEQ", allocationSize = 1)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "member_id")
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
