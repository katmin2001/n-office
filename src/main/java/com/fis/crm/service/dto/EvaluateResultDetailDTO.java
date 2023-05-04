package com.fis.crm.service.dto;

import com.fis.crm.domain.EvaluateResultDetail;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link EvaluateResultDetail} entity.
 */
public class EvaluateResultDetailDTO implements Serializable {

    private Long id;

    private Long evaluateResultId;

    private Long criteriaDetailId;

    private String note;

    private Date createDatetime;

    private Long criteriaGroupId;

    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCriteriaGroupId() {
        return criteriaGroupId;
    }

    public void setCriteriaGroupId(Long criteriaGroupId) {
        this.criteriaGroupId = criteriaGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvaluateResultId() {
        return evaluateResultId;
    }

    public void setEvaluateResultId(Long evaluateResultId) {
        this.evaluateResultId = evaluateResultId;
    }

    public Long getCriteriaDetailId() {
        return criteriaDetailId;
    }

    public void setCriteriaDetailId(Long criteriaDetailId) {
        this.criteriaDetailId = criteriaDetailId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateResultDetailDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluateResultDetailDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateResultDetai1DTO{" +
            "id=" + getId() +
            ", evaluateResultId=" + getEvaluateResultId() +
            ", criteriaDetailId=" + getCriteriaDetailId() +
            ", note='" + getNote() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            "}";
    }
}
