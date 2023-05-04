package com.fis.crm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A EvaluateResultDetai1.
 */
@Entity
@Table(name = "evaluate_result_detail_history")
public class EvaluateResultDetailHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_RESULT_DH_GEN" )
    @SequenceGenerator(name = "EVALUATE_RESULT_DH_GEN", sequenceName = "EVALUATE_RESULT_DH_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluateResultHistory evaluateResultHistory;

    @Column(name = "criteria_detail_id")
    private Long criteriaDetailId;

    @Column(name = "EVALUATE_RESULT_DETAIL_ID")
    private Long evaluateResultDetailId;

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

    public Long getEvaluateResultDetailId() {
        return evaluateResultDetailId;
    }

    public void setEvaluateResultDetailId(Long evaluateResultDetailId) {
        this.evaluateResultDetailId = evaluateResultDetailId;
    }

    public EvaluateResultHistory getEvaluateResultHistory() {
        return evaluateResultHistory;
    }

    public void setEvaluateResultHistory(EvaluateResultHistory evaluateResultHistory) {
        this.evaluateResultHistory = evaluateResultHistory;
    }

    public Long getCriteriaDetailId() {
        return criteriaDetailId;
    }

    public EvaluateResultDetailHistory criteriaDetailId(Long criteriaDetailId) {
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

    public EvaluateResultDetailHistory createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}
