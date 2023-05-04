package com.fis.crm.service.dto;

import java.util.Date;

public class ContractorsSuspendDTO {

    private Long id;

    private String cid;

    private String taxCode;

    private String name;

    private String address;

    private String feeType;

    private Date suspendTime;

    private String suspendTimeFrom;

    private String suspendTimeTo;

    private Date insertDate;

    private String feeTypeDisplay;

    private String suspendTimeDisplay;

    public String getSuspendTimeDisplay() {
        return suspendTimeDisplay;
    }

    public void setSuspendTimeDisplay(String suspendTimeDisplay) {
        this.suspendTimeDisplay = suspendTimeDisplay;
    }

    public String getFeeTypeDisplay() {
        return feeTypeDisplay;
    }

    public void setFeeTypeDisplay(String feeTypeDisplay) {
        this.feeTypeDisplay = feeTypeDisplay;
    }

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
