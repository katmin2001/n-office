package com.fis.crm.service.dto;

import com.fis.crm.domain.EvaluateAssignmentDetail;

import java.io.Serializable;

/**
 * A DTO for the {@link EvaluateAssignmentDetail} entity.
 */
public class EvaluateAssignmentDetailDTO implements Serializable {

    private Long id;

    private Long evaluateAssignmentId;

    private Long userId;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvaluateAssignmentId() {
        return evaluateAssignmentId;
    }

    public void setEvaluateAssignmentId(Long evaluateAssignmentId) {
        this.evaluateAssignmentId = evaluateAssignmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateAssignmentDetailDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluateAssignmentDetailDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateAssignmentDDTO{" +
            "id=" + getId() +
            ", evaluateAssignmentId=" + getEvaluateAssignmentId() +
            ", userId=" + getUserId() +
            "}";
    }
}
