package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EvaluateAssignmentD.
 */
@Entity
@Table(name = "evaluate_assignment_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluateAssignmentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_ASSIGNMENT_DETAIL_GEN")
    @SequenceGenerator(name = "EVALUATE_ASSIGNMENT_DETAIL_GEN", sequenceName = "EVALUATE_ASSIGNMENT_DETAIL_SEQ", allocationSize = 1)
    private Long id;

//    @Column(name = "evaluate_assignment_id")
//    private Long evaluateAssignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private EvaluateAssignment evaluateAssignment;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "TOTAL_EVALUATED")
    private Long totalEvaluated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluateAssignment getEvaluateAssignment() {
        return evaluateAssignment;
    }

    public void setEvaluateAssignment(EvaluateAssignment evaluateAssignment) {
        this.evaluateAssignment = evaluateAssignment;
    }

    public Long getUserId() {
        return userId;
    }

    public EvaluateAssignmentDetail userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotalEvaluated() {
        return totalEvaluated;
    }

    public void setTotalEvaluated(Long totalEvaluated) {
        this.totalEvaluated = totalEvaluated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateAssignmentDetail)) {
            return false;
        }
        return id != null && id.equals(((EvaluateAssignmentDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EvaluateAssignmentD{" +
            "id=" + getId() +
            ", EvaluateAssignment=" + getEvaluateAssignment() +
            ", userId=" + getUserId() +
            "}";
    }
}
