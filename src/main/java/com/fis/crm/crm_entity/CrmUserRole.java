package com.fis.crm.crm_entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_USER_ROLE", schema = "CRM_UAT", catalog = "")
public class CrmUserRole {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "URID")
    private Long urid;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    private CrmUser user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    private CrmRole role;

    public CrmUserRole() {
    }

    public CrmUserRole(Long urid, CrmUser user, CrmRole role) {
        this.urid = urid;
        this.user = user;
        this.role = role;
    }

    public Long getUrid() {
        return urid;
    }
    public void setUrid(Long urid) {
        this.urid = urid;
    }

    public CrmUser getUser() {
        return user;
    }

    public void setUser(CrmUser user) {
        this.user = user;
    }

    public CrmRole getRole() {
        return role;
    }

    public void setRole(CrmRole role) {
        this.role = role;
    }
}
