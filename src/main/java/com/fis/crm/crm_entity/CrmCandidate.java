package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "CRM_CANDIDATE", schema = "CRM_UAT", catalog = "")
public class CrmCandidate {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CANDIDATEID")
    private int candidateid;

    @Basic
    @Column(name = "FULLNAME")
    private String fullname;
    @Basic
    @Column(name = "PHONE")
    private Integer phone;
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
    @OneToOne
    @JoinColumn(name = "ISID")
    private CrmInterviewStatus interviewStatus;
    @OneToOne
    @JoinColumn(name = "MANAGEID")
    private CrmUser user;

    public CrmCandidate() {
    }

    public CrmCandidate(int candidateid, String fullname, Integer phone, Date birthday, String address, Boolean status, Date createDate, CrmInterview interview, CrmInterviewStatus interviewStatus, CrmUser user) {
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

    public int getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(int candidateid) {
        this.candidateid = candidateid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
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
