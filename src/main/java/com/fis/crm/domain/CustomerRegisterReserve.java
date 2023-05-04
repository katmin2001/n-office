package com.fis.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "customer_register_service_reserve")
public class CustomerRegisterReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_REGISTER_SERVICE_RESERVE_GEN")
    @SequenceGenerator(name = "CUSTOMER_REGISTER_SERVICE_RESERVE_GEN", sequenceName = "CUSTOMER_REGISTER_SERVICE_RESERVE_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "records_code", nullable = false)
    private String recordsCode;

    @Column(name = "cid", nullable = false)
    private String cid;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "name")
    private String name;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "approve_date")
    private Date approveDate;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "doc_no")
    private String docNo;

    @Column(name = "reasons")
    private String reasons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordsCode() {
        return recordsCode;
    }

    public void setRecordsCode(String recordsCode) {
        this.recordsCode = recordsCode;
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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}
