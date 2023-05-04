package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "CONTACTOR_SUSPEND")
public class ContractorsSuspend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTORS_SUSPEND_GEN")
    @SequenceGenerator(name = "CONTRACTORS_SUSPEND_GEN", sequenceName = "CONTRACTORS_SUSPEND_SEQ", allocationSize = 1)
    @Column(name = "contractors_suspend_id", nullable = false)
    private Long id;

    @Column(name = "cid", nullable = false)
    private String cid;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "fee_type")
    private String feeType;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
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
