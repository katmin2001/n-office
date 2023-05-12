package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "CRM_INTERVIEW", schema = "CRM_UAT", catalog = "")
public class CrmInterview {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "INTERVIEWID")
    private int interviewid;
    @Basic
    @Column(name = "INTERVIEW_DATE")
    private Date interviewDate;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;
    @Basic
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @OneToOne
    @JoinColumn(name = "CANDIDATEID")
    private CrmCandidate candidate;
    @OneToOne
    @JoinColumn(name = "ISID")
    private CrmInterviewStatus interviewStatus;
    @OneToMany(mappedBy = "interview")
    private Set<CrmInterviewDetail> interviewDetails;

    public CrmInterview() {
    }

    public CrmInterview(int interviewid, Date interviewDate, Boolean status, Date createDate, CrmCandidate candidate, CrmInterviewStatus interviewStatus, Set<CrmInterviewDetail> interviewDetails) {
        this.interviewid = interviewid;
        this.interviewDate = interviewDate;
        this.status = status;
        this.createDate = createDate;
        this.candidate = candidate;
        this.interviewStatus = interviewStatus;
        this.interviewDetails = interviewDetails;
    }

    public Set<CrmInterviewDetail> getInterviewDetails() {
        return interviewDetails;
    }

    public void setInterviewDetails(Set<CrmInterviewDetail> interviewDetails) {
        this.interviewDetails = interviewDetails;
    }

    public int getInterviewid() {
        return interviewid;
    }

    public void setInterviewid(int interviewid) {
        this.interviewid = interviewid;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public CrmCandidate getCandidate() {
        return candidate;
    }

    public void setCandidate(CrmCandidate candidate) {
        this.candidate = candidate;
    }

    public CrmInterviewStatus getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(CrmInterviewStatus interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
