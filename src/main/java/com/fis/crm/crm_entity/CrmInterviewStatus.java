package com.fis.crm.crm_entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_INTERVIEW_STATUS", schema = "CRM_UAT", catalog = "")
public class CrmInterviewStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ISID")
    private int isid;
    @Basic
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToOne(mappedBy = "interviewStatus")
    private CrmCandidate candidate;

    public int getIsid() {
        return isid;
    }

    public void setIsid(int isid) {
        this.isid = isid;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CrmCandidate getCandidate() {
        return candidate;
    }

    public void setCandidate(CrmCandidate candidate) {
        this.candidate = candidate;
    }
}
