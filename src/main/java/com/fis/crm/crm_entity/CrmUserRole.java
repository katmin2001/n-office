package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "CRM_USER_ROLE")
public class CrmUserRole {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_USER_ROLE_SEQ_GEN")
    @SequenceGenerator(name = "CRM_USER_ROLE_SEQ_GEN", sequenceName = "CRM_USER_ROLE_SEQ", allocationSize = 1)
    @Id
    @Column(name = "URID")
    private Long urid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private CrmUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLEID")
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
