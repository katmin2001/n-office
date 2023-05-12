package com.fis.crm.crm_entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_INTERVIEW_DETAIL", schema = "CRM_UAT", catalog = "")
public class CrmInterviewDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IMID")
    private int imid;

    @ManyToOne
    @JoinColumn(name = "USERID")
    private CrmUser user;
    @ManyToOne
    @JoinColumn(name = "INTERVIEWID")
    private CrmInterview interview;

    public CrmInterviewDetail() {
    }

    public CrmInterviewDetail(int imid, CrmUser user, CrmInterview interview) {
        this.imid = imid;
        this.user = user;
        this.interview = interview;
    }

    public int getImid() {
        return imid;
    }

    public void setImid(int imid) {
        this.imid = imid;
    }


    public CrmUser getUser() {
        return user;
    }

    public void setUser(CrmUser user) {
        this.user = user;
    }

    public CrmInterview getInterview() {
        return interview;
    }

    public void setInterview(CrmInterview interview) {
        this.interview = interview;
    }
}
