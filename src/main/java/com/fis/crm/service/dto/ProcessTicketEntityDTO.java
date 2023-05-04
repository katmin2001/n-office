package com.fis.crm.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.ProcessTicketEntity} entity.
 */
public class ProcessTicketEntityDTO implements Serializable {
    
    private Long id;

    private Long processTicketId;

    private Long ticketId;

    private Long ticketRequestId;

    private String content;

    private Long departmentId;

    private Long createUser;

    private Instant createDatetime;

    private String status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessTicketId() {
        return processTicketId;
    }

    public void setProcessTicketId(Long processTicketId) {
        this.processTicketId = processTicketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessTicketEntityDTO)) {
            return false;
        }

        return id != null && id.equals(((ProcessTicketEntityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessTicketEntityDTO{" +
            "id=" + getId() +
            ", processTicketId=" + getProcessTicketId() +
            ", ticketId=" + getTicketId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", content='" + getContent() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
