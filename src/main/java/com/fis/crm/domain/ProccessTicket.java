package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A TicketRequest.
 */
@Entity
@Table(name = "process_ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProccessTicket implements Serializable {

  private static final long serialVersionUID = 1L;


  @Id
  @Column(name = "process_ticket_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCESS_TICKET_SEQ_GEN")
  @SequenceGenerator(name = "PROCESS_TICKET_SEQ_GEN", sequenceName = "PROCESS_TICKET_SEQ", allocationSize = 1)
  private Long processTicketId;

  @Column(name = "ticket_id")
  private Long ticketId;

  @Column(name = "ticket_request_id")
  private Long ticketRequestId;

  @Column(name = "content")
  private String content;

  @Column(name = "department_id")
  private Long departmentId;

  @Column(name = "create_datetime")
  private Instant createDatetime;

  @Column(name = "create_user")
  private Long createUser;

  @Column(name = "status")
  private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProcessTicketId() {
        return processTicketId;
    }

    public void setProcessTicketId(Long processTicketId) {
        processTicketId = processTicketId;
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

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
    if (!(o instanceof ProccessTicket)) {
      return false;
    }
    return processTicketId != null && processTicketId.equals(((ProccessTicket) o).processTicketId);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ProcessTicket{" +
            " processTicketId=" + getProcessTicketId() +
            ", ticketId=" + getTicketId() +
            ", ticketRequestId=" + getTicketRequestId() +
            ", content='" + getContent() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime=" + getCreateDatetime() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
