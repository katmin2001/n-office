package com.fis.crm.service.egp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "debtFeeType",
    "criteria"
})
public class PmContractorStatus extends Page {

    @JsonProperty("debtFeeType")
    private Integer debtFeeType;
    @JsonProperty("criteria")
    private Criteria criteria;

    public PmContractorStatus(String pageNumber, Integer pageSize, Integer debtFeeType, Criteria criteria) {
        super(pageNumber, pageSize);
        this.debtFeeType = debtFeeType;
        this.criteria = criteria;
    }

    public PmContractorStatus(Integer debtFeeType, Criteria criteria) {
        this.debtFeeType = debtFeeType;
        this.criteria = criteria;
    }
    public PmContractorStatus() {
    }

    public PmContractorStatus(String pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }


    public Integer getDebtFeeType() {
        return debtFeeType;
    }

    public void setDebtFeeType(Integer debtFeeType) {
        this.debtFeeType = debtFeeType;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "PmContractorStatus{" +
            "debtFeeType=" + debtFeeType +
            ", criteria=" + criteria +
            '}';
    }
}

