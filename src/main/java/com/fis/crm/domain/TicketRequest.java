package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A TicketRequest.
 */
@Entity
@Table(name = "ticket_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "ticket_request_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TICKET_REQUEST_SEQ_GEN")
    @SequenceGenerator(name = "TICKET_REQUEST_SEQ_GEN", sequenceName = "TICKET_REQUEST_SEQ", allocationSize = 1)
    private Long ticketRequestId;


    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "ticket_request_code")
    private String ticketRequestCode;

    @Column(name = "request_type")
    private Long requestType;

    @Column(name = "bussiness_type")
    private Long bussinessType;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "status")
    private String status;

    @Column(name = "deadline")
    private Instant deadline;

    @Column(name = "content")
    private String content;

    @Column(name = "confirm_datetime")
    private Date confirmDate;

    @Column(name = "time_notify")
    private Long timeNotify;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "update_user")
    private Long updateUser;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "UPDATE_DATIME")
    private Instant updateDatetime;

    @Column(name = "no")
    private Long no;

    @Column(name = "CREATE_DEPARTMENTS")
    private String createDepartments;

    public String getCreateDepartments() {
        return createDepartments;
    }

    public void setCreateDepartments(String createDepartments) {
        this.createDepartments = createDepartments;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getTicketRequestId() {
        return this.ticketRequestId;
    }

    public TicketRequest ticketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
        return this;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketRequestCode() {
        return this.ticketRequestCode;
    }

    public TicketRequest ticketRequestCode(String ticketRequestCode) {
        this.ticketRequestCode = ticketRequestCode;
        return this;
    }

    public void setTicketRequestCode(String ticketRequestCode) {
        this.ticketRequestCode = ticketRequestCode;
    }

    public Long getRequestType() {
        return this.requestType;
    }

    public TicketRequest requestType(Long requestType) {
        this.requestType = requestType;
        return this;
    }

    public void setRequestType(Long requestType) {
        this.requestType = requestType;
    }

    public Long getBussinessType() {
        return this.bussinessType;
    }

    public TicketRequest bussinessType(Long bussinessType) {
        this.bussinessType = bussinessType;
        return this;
    }

    public void setBussinessType(Long bussinessType) {
        this.bussinessType = bussinessType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public TicketRequest departmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getStatus() {
        return this.status;
    }

    public TicketRequest status(String status) {
        this.status = status;
        return this;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDeadline() {
        return this.deadline;
    }

    public TicketRequest deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public String getContent() {
        return this.content;
    }

    public TicketRequest content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getConfirmDate() {
        return this.confirmDate;
    }

    public TicketRequest confirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
        return this;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getTimeNotify() {
        return this.timeNotify;
    }

    public TicketRequest timeNotify(Long timeNotify) {
        this.timeNotify = timeNotify;
        return this;
    }

    public void setTimeNotify(Long timeNotify) {
        this.timeNotify = timeNotify;
    }

    public Long getCreateUser() {
        return this.createUser;
    }

    public TicketRequest createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return this.createDatetime;
    }

    public TicketRequest createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketRequest)) {
            return false;
        }
        return ticketRequestId != null && ticketRequestId.equals(((TicketRequest) o).ticketRequestId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketRequest{" +
            " ticketRequestId=" + getTicketRequestId() +
            ", ticketId='" + getTicketId() + "'" +
            ", ticketRequestCode='" + getTicketRequestCode() + "'" +
            ", requestType=" + getRequestType() +
            ", bussinessType=" + getBussinessType() +
            ", priority=" + getPriority() +
            ", departmentId=" + getDepartmentId() +
            ", status='" + getStatus() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", content='" + getContent() + "'" +
            ", confirmDate='" + getConfirmDate() + "'" +
            ", timeNotify=" + getTimeNotify() +
            ", createUser=" + getCreateUser() +
            ", updateUser=" + getUpdateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            "}";
    }
}
