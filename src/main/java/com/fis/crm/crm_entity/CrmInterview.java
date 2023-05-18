package com.fis.crm.crm_entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CRM_INTERVIEW", schema = "CRM_UAT", catalog = "")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "interviewid")
public class CrmInterview {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crm_interview_Sequence")
    @SequenceGenerator(name = "crm_interview_Sequence", sequenceName = "CRM_INTERVIEW_SEQ", allocationSize = 1)
    @Id
    @Column(name = "INTERVIEWID")
    private Long interviewid;
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
    @ManyToOne
    @JoinColumn(name = "ISID")
    private CrmInterviewStatus interviewStatus;
    @ManyToMany
    @JoinTable(name = "CRM_INTERVIEW_DETAIL",
        joinColumns = @JoinColumn(name = "INTERVIEWID"),
        inverseJoinColumns = @JoinColumn(name = "USERID"))
    private Set<CrmUser> users;
    public CrmInterview() {
    }

    public CrmInterview(Long interviewid, Date interviewDate, Boolean status, Date createDate, CrmCandidate candidate, CrmInterviewStatus interviewStatus, Set<CrmUser> users) {
        this.interviewid = interviewid;
        this.interviewDate = interviewDate;
        this.status = status;
        this.createDate = createDate;
        this.candidate = candidate;
        this.interviewStatus = interviewStatus;
        this.users = users;
    }

    public Set<CrmUser> getUsers() {
        return users;
    }

    public void setUsers(Set<CrmUser> users) {
        this.users = users;
    }

    public Long getInterviewid() {
        return interviewid;
    }

    public void setInterviewid(Long interviewid) {
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
