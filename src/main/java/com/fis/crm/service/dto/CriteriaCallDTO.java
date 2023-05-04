package com.fis.crm.service.dto;

import com.fis.crm.domain.EvaluateResultDetail;

import java.io.Serializable;

/**
 * A DTO for the {@link EvaluateResultDetail} entity.
 */
public class CriteriaCallDTO implements Serializable {

    private String criteriaName;

    private String criteriaPerCall;

    private String rate;

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getCriteriaPerCall() {
        return criteriaPerCall;
    }

    public void setCriteriaPerCall(String criteriaPerCall) {
        this.criteriaPerCall = criteriaPerCall;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
