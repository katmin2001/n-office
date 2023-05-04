package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.fis.crm.domain.EvaluateResultResponse} entity.
 */
public class EvaluateResultResponseDTO implements Serializable {

    private Long id;

    private Long evaluateResultId;

    private String content;

    private Long createUser;

    private UserDTO userDTO;

    private Date createDatetime;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
        if (!(o instanceof EvaluateResultResponseDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluateResultResponseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateResultResponseDTO{" +
            "id=" + getId() +
            ", evaluateResultId=" + getEvaluateResultId() +
            ", content='" + getContent() + "'" +
            ", createUser=" + getCreateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            "}";
    }
}
