package com.fis.crm.crm_entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Optional;
@Entity
@Table(name = "CRM_CANDIDATE")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "candidateid")
public class CrmCandidate {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crm_candidate_Sequence")
    @SequenceGenerator(name = "crm_candidate_Sequence", sequenceName = "CRM_CANDIDATE_SEQ", allocationSize = 1)
    @Id
    @Column(name = "CANDIDATEID")
    private Long candidateid;

    @Basic
    @Column(name = "FULLNAME")
    private String fullname;
    @Basic
    @Column(name = "PHONE")
    private String phone;
    @Basic
    @Column(name = "BIRTHDAY")
    private Date birthday;
    @Basic
    @Column(name = "ADDRESS")
    private String address;
    @Basic
    @Column(name = "STATUS")
    private Boolean status;
    @Basic
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @OneToOne(mappedBy = "candidate")
    private CrmInterview interview;
    @ManyToOne
    @JoinColumn(name = "ISID")
    private CrmInterviewStatus interviewStatus;
    @ManyToOne
    @JoinColumn(name = "MANAGEID")
    private CrmUser user;

    public CrmCandidate() {
    }

    public CrmCandidate(Long candidateid, String fullname, String phone, Date birthday, String address, Boolean status, Date createDate, CrmInterview interview, CrmInterviewStatus interviewStatus, CrmUser user) {
        this.candidateid = candidateid;
        this.fullname = fullname;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.status = status;
        this.createDate = createDate;
        this.interview = interview;
        this.interviewStatus = interviewStatus;
        this.user = user;
    }

    public CrmUser getUser() {
        return user;
    }

    public void setUser(CrmUser user) {
        this.user = user;
    }

    public Long getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(Long candidateid) {
        this.candidateid = candidateid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public CrmInterview getInterview() {
        return interview;
    }

    public void setInterview(CrmInterview interview) {
        this.interview = interview;
    }

    public CrmInterviewStatus getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(CrmInterviewStatus interviewStatus) {
        this.interviewStatus = interviewStatus;
    }
}
