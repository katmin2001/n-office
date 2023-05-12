package com.fis.crm.crm_entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CRM_FUNCTION", schema = "CRM_UAT", catalog = "")
public class CrmFunction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "FUNCID")
    private Long funcid;
    @Basic
    @Column(name = "FUNCNAME")
    private String funcname;
    @OneToMany(mappedBy = "function",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CrmRoleFunction> funcRoles;

    public CrmFunction() {
    }

    public CrmFunction(Long funcid, String funcname, Set<CrmRoleFunction> funcRoles) {
        this.funcid = funcid;
        this.funcname = funcname;
        this.funcRoles = funcRoles;
    }

    public Long getFuncid() {
        return funcid;
    }

    public void setFuncid(Long funcid) {
        this.funcid = funcid;
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public Set<CrmRoleFunction> getFuncRoles() {
        return funcRoles;
    }

    public void setFuncRoles(Set<CrmRoleFunction> funcRoles) {
        this.funcRoles = funcRoles;
    }
}
