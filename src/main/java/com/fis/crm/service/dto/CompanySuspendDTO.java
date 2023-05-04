package com.fis.crm.service.dto;

import java.util.Date;

public class CompanySuspendDTO {

    private Long id;

    private String cid;

    private String taxCode;

    private String name;

    private String reason;

    private Date suspendTime;

    private Date insertDate;

    private String suspendTimeFrom;

    private String suspendTimeTo;

    private String suspendTimeDisplay;

    public String getSuspendTimeFrom() {
        return suspendTimeFrom;
    }

    public void setSuspendTimeFrom(String suspendTimeFrom) {
        this.suspendTimeFrom = suspendTimeFrom;
    }

    public String getSuspendTimeTo() {
        return suspendTimeTo;
    }

    public void setSuspendTimeTo(String suspendTimeTo) {
        this.suspendTimeTo = suspendTimeTo;
    }

    public String getSuspendTimeDisplay() {
        return suspendTimeDisplay;
    }

    public void setSuspendTimeDisplay(String suspendTimeDisplay) {
        this.suspendTimeDisplay = suspendTimeDisplay;
    }

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
