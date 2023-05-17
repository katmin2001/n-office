package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CRM_INTERVIEW_STATUS", schema = "CRM_UAT", catalog = "")
public class CrmInterviewStatus {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crm_interview_status_Sequence")
    @SequenceGenerator(name = "crm_interview_status_Sequence", sequenceName = "CRM_INTERVIEW_STATUS_SEQ", allocationSize = 1)
    @Id
    @Column(name = "ISID")
    private Long isid;
    @Basic
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "interviewStatus")
    private Set<CrmCandidate> candidates;
    @OneToMany(mappedBy = "interviewStatus")
    private Set<CrmInterview> interviews;
    public CrmInterviewStatus() {
    }

    public CrmInterviewStatus(Long isid, String statusName, String description, CrmCandidate candidate) {
        this.isid = isid;
        this.statusName = statusName;
        this.description = description;
        this.candidates = candidates;
    }

    public Long getIsid() {
        return isid;
    }

    public void setIsid(Long isid) {
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

    public Set<CrmCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<CrmCandidate> candidates) {
        this.candidates = candidates;
    }
}
