package com.fis.crm.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company_suspend")
public class CompanySuspend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SUSPEND_GEN")
    @SequenceGenerator(name = "COMPANY_SUSPEND_GEN", sequenceName = "COMPANY_SUSPEND_SEQ", allocationSize = 1)
    @Column(name = "company_suspend_id", nullable = false)
    private Long id;

    @Column(name = "cid", nullable = false)
    private String cid;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "name")
    private String name;

    @Column(name = "reason")
    private String reason;

    @Column(name = "suspend_time")
    private Date suspendTime;

    @Column(name = "insert_date")
    private Date insertDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(Date suspendTime) {
        this.suspendTime = suspendTime;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}
