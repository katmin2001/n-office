package com.fis.crm.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.EvaluateResult} entity.
 */
public class EvaluateResultRatingDTO implements Serializable {

    private Long criteriaRatingId;
    private String criteriaRatingName;
    private Long times;
    private Long totalCall;
    private String rate;

    public String getCriteriaRatingName() {
        return criteriaRatingName;
    }

    public void setCriteriaRatingName(String criteriaRatingName) {
        this.criteriaRatingName = criteriaRatingName;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Long getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(Long totalCall) {
        this.totalCall = totalCall;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Long getCriteriaRatingId() {
        return criteriaRatingId;
    }

    public void setCriteriaRatingId(Long criteriaRatingId) {
        this.criteriaRatingId = criteriaRatingId;
    }
}
