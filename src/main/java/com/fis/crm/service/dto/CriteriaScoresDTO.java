package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CriteriaScoresDTO implements Serializable {

    private Double scores;

    public Double getScores() {
        return scores;
    }

    public void setScores(Double scores) {
        this.scores = scores;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CriteriaScoresDTO)) {
            return false;
        }

        CriteriaScoresDTO criteriaScoresDTO = (CriteriaScoresDTO) o;
        if (this.scores == null) {
            return false;
        }
        return Objects.equals(this.scores, criteriaScoresDTO.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scores);
    }

    @Override
    public String toString() {
        return "CriteriaScoresDTO{" +
            "scores=" + scores +
            '}';
    }
}
