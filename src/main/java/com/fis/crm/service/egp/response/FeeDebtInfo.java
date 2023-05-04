package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeeDebtInfo implements Serializable {

    @JsonProperty("feeType")
    private Integer feeType;
    private String freeTypeName;
    @JsonProperty("status")
    private Integer status;
    private String statusName;
    @JsonProperty("paymentExpDate")
    private Date paymentExpDate;
    @JsonProperty("feeTotal")
    private Long feeTotal;

    public FeeDebtInfo(Integer feeType, Integer status, Date paymentExpDate, Long feeTotal) {
        this.feeType = feeType;
        this.status = status;
        this.paymentExpDate = paymentExpDate;
        this.feeTotal = feeTotal;
    }

    public FeeDebtInfo() {
    }

    public String getFreeTypeName() {
        return freeTypeName;
    }

    public void setFreeTypeName(String freeTypeName) {
        this.freeTypeName = freeTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPaymentExpDate() {
        return paymentExpDate;
    }

    public void setPaymentExpDate(Date paymentExpDate) {
        this.paymentExpDate = paymentExpDate;
    }

    public Long getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Long feeTotal) {
        this.feeTotal = feeTotal;
    }
}
