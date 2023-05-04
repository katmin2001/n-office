package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ConfirmTicket.
 */
@Entity
@Table(name = "confirm_ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfirmTicket implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIRM_TICKET_GEN")
  @SequenceGenerator(name = "CONFIRM_TICKET_GEN", sequenceName = "CONFIRM_TICKET_SEQ", allocationSize = 1)
  @Column(name = "confirm_ticket_id")
  private Long confirmTicketId;

  @Column(name = "ticket_id")
  private Long ticketId;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getConfirmTicketId() {
        return confirmTicketId;
    }

    public void setConfirmTicketId(Long confirmTicketId) {
        this.confirmTicketId = confirmTicketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
    if (!(o instanceof ConfirmTicket)) {
      return false;
    }
    return confirmTicketId != null && confirmTicketId.equals(((ConfirmTicket) o).confirmTicketId);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ConfirmTicket{" +
            " confirmTicketId=" + getConfirmTicketId() +
            ", ticketId=" + getTicketId() +
            ", content='" + getContent() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime=" + getCreateDatetime() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
