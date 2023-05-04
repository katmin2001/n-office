package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ticket_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TICKET_SEQ_GEN")
    @SequenceGenerator(name = "TICKET_SEQ_GEN", sequenceName = "TICKET_SEQ", allocationSize = 1)
    private Long ticketId;

    @Column(name = "chanel_type")
    private String channelType;

    @Column(name = "ticket_code")
    private String ticketCode;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "status")
    private String status;

    @Column(name = "fcr")
    private String fcr;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "confirm_user")
    private Long confirmUser;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "update_datetime")
    private Instant updateDatetime;

    @Column(name = "confirm_datetime")
    private Date confirmDatetime;

    @Column(name = "confirm_deadline")
    private Instant confirmDeadline;

    @Column(name = "cid")
    private String cid;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Column(name = "SATISFIED")
    private String satisfied;

    @Column(name = "EVALUATE_STATUS")
    private String evaluateStatus;

    @Column(name = "TICKET_STATUS")
    private String ticketStatus;

    @Column(name = "TICKET_REQUEST_STATUS")
    private String ticketRequestStatus;

    @Column(name="CREATE_DEPARTMENTS")
    private String createDepartments;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "START_CALL")
    private String startCall;

    @Column(name = "CALL_FILE")
    private String callFile;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartCall() {
        return startCall;
    }

    public void setStartCall(String startCall) {
        this.startCall = startCall;
    }

    public String getCallFile() {
        return callFile;
    }

    public void setCallFile(String callFile) {
        this.callFile = callFile;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public Ticket id(Long id) {
//    this.id = id;
//    return this;
//  }

    public Long getTicketId() {
        return this.ticketId;
    }

    public Ticket ticketId(Long ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public Ticket channelType(String channelType) {
        this.channelType = channelType;
        return this;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getTicketCode() {
        return this.ticketCode;
    }

    public Ticket ticketCode(String ticketCode) {
        this.ticketCode = ticketCode;
        return this;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Ticket customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return this.status;
    }

    public Ticket status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFcr() {
        return this.fcr;
    }

    public Ticket fcr(String fcr) {
        this.fcr = fcr;
        return this;
    }

    public void setFcr(String fcr) {
        this.fcr = fcr;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public Ticket departmentId(Long departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreateUser() {
        return this.createUser;
    }

    public Ticket createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return this.createDatetime;
    }

    public Ticket createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return this.updateDatetime;
    }

    public Ticket updateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
        return this;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied;
    }

    public Long getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Long confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmDatetime() {
        return confirmDatetime;
    }

    public void setConfirmDatetime(Date confirmDatetime) {
        this.confirmDatetime = confirmDatetime;
    }

    public Instant getConfirmDeadline() {
        return confirmDeadline;
    }

    public void setConfirmDeadline(Instant confirmDeadline) {
        this.confirmDeadline = confirmDeadline;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketRequestStatus() {
        return ticketRequestStatus;
    }

    public void setTicketRequestStatus(String ticketRequestStatus) {
        this.ticketRequestStatus = ticketRequestStatus;
    }

    public String getCreateDepartments() {
        return createDepartments;
    }

    public void setCreateDepartments(String createDepartments) {
        this.createDepartments = createDepartments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return ticketId != null && ticketId.equals(((Ticket) o).ticketId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            " ticketId=" + getTicketId() +
            ", channelType='" + getChannelType() + "'" +
            ", ticketCode='" + getTicketCode() + "'" +
            ", customerId=" + getCustomerId() +
            ", status='" + getStatus() + "'" +
            ", fcr='" + getFcr() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", createUser=" + getCreateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", confirmDatetime='" + getConfirmDatetime() + "'" +
            ", confirmDeadline='" + getConfirmDeadline() + "'" +
            ", cid='" + getCid() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", satisfied='" + getSatisfied() + "'" +
            "}";
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }
}
