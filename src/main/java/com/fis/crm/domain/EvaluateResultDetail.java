package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A EvaluateResultDetai1.
 */
@Entity
@Table(name = "evaluate_result_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluateResultDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_RESULT_DETAIL_GEN" )
    @SequenceGenerator(name = "EVALUATE_RESULT_DETAIL_GEN", sequenceName = "EVALUATE_RESULT_DETAIL_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluateResult evaluateResult;

    @Column(name = "criteria_detail_id")
    private Long criteriaDetailId;

    @Column(name = "note")
    private String note;

    @Column(name = "create_datetime")
    private Date createDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluateResult getEvaluateResult() {
        return evaluateResult;
    }

    public void setEvaluateResult(EvaluateResult evaluateResult) {
        this.evaluateResult = evaluateResult;
    }

    public Long getCriteriaDetailId() {
        return criteriaDetailId;
    }

    public EvaluateResultDetail criteriaDetailId(Long criteriaDetailId) {
        this.criteriaDetailId = criteriaDetailId;
        return this;
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

    public EvaluateResultDetail createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateResultDetail)) {
            return false;
        }
        return id != null && id.equals(((EvaluateResultDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateResultDetai1{" +
            "id=" + getId() +
            ", evaluateResult=" + getEvaluateResult() +
            ", criteriaDetailId=" + getCriteriaDetailId() +
            ", note='" + getNote() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            "}";
    }
}
