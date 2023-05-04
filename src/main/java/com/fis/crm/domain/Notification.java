package com.fis.crm.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTiFICATION_SEQ")
    @SequenceGenerator(name = "NOTiFICATION_SEQ", sequenceName = "NOTiFICATION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_read")
    private String isRead;

    @Column(name = "status")
    private String status;

    @Column(name = "object_code")
    private String objectCode;

    @Column(name = "ref_id")
    private Long refId;

    @Column(name = "ticket_request_id")
    private Long ticketRequestId;

    @Column(name = "ticket_request_code")
    private String ticketRequestCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public String getTicketRequestCode() {
        return ticketRequestCode;
    }

    public void setTicketRequestCode(String ticketRequestCode) {
        this.ticketRequestCode = ticketRequestCode;
    }

    public Notification id(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Notification type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Notification content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateDatetime() {
        return this.createDatetime;
    }

    public Notification createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Notification userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsRead() {
        return this.isRead;
    }

    public Notification isRead(String isRead) {
        this.isRead = isRead;
        return this;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getStatus() {
        return this.status;
    }

    public Notification status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjectCode() {
        return this.objectCode;
    }

    public Notification objectCode(String objectCode) {
        this.objectCode = objectCode;
        return this;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public Long getRefId() {
        return this.refId;
    }

    public Notification refId(Long refId) {
        this.refId = refId;
        return this;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", userId=" + getUserId() +
            ", isRead='" + getIsRead() + "'" +
            ", status='" + getStatus() + "'" +
            ", objectCode='" + getObjectCode() + "'" +
            ", refId=" + getRefId() +
            "}";
    }
}
