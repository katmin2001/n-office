package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProcessTicketEntity.
 */
@Entity
@Table(name = "process_ticket_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "process_ticket_id")
    private Long processTicketId;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "ticket_request_id")
    private Long ticketRequestId;

    @Column(name = "content")
    private String content;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessTicketId() {
        return processTicketId;
    }

    public ProcessTicketEntity processTicketId(Long processTicketId) {
        this.processTicketId = processTicketId;
        return this;
    }

    public void setProcessTicketId(Long processTicketId) {
        this.processTicketId = processTicketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public ProcessTicketEntity ticketId(Long ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public ProcessTicketEntity ticketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
        return this;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public String getContent() {
        return content;
    }

    public ProcessTicketEntity content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public ProcessTicketEntity departmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public ProcessTicketEntity createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public ProcessTicketEntity createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStatus() {
        return status;
    }

    public ProcessTicketEntity status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessTicketEntity)) {
            return false;
        }
        return id != null && id.equals(((ProcessTicketEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessTicketEntity{" +
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
