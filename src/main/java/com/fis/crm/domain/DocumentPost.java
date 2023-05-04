package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DocumentPost.
 */
@Entity
@Table(name = "document_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT_POST_SEQ")
    @SequenceGenerator(name = "DOCUMENT_POST_SEQ", sequenceName = "DOCUMENT_POST_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;
    @Column(name = "tags")
    private String tags;

    @Column(name = "view_total")
    private Long viewTotal;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "create_user")
    private Long createUser;

    @Column(name = "approve_status")
    private String approveStatus;


    @Column(name = "approve_datetime")
    private Instant approveDatetime;

    @Column(name = "approve_user")
    private Long approveUser;

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public DocumentPost documentId(Long documentId) {
        this.documentId = documentId;
        return this;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public DocumentPost name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public DocumentPost title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public DocumentPost content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public DocumentPost status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public DocumentPost tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getViewTotal() {
        return viewTotal;
    }

    public DocumentPost viewTotal(Long viewTotal) {
        this.viewTotal = viewTotal;
        return this;
    }

    public void setViewTotal(Long viewTotal) {
        this.viewTotal = viewTotal;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public DocumentPost createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public DocumentPost createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getApproveDatetime() {
        return approveDatetime;
    }

    public DocumentPost approveDatetime(Instant approveDatetime) {
        this.approveDatetime = approveDatetime;
        return this;
    }

    public void setApproveDatetime(Instant approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public Long getApproveUser() {
        return approveUser;
    }

    public DocumentPost approveUser(Long approveUser) {
        this.approveUser = approveUser;
        return this;
    }

    public void setApproveUser(Long approveUser) {
        this.approveUser = approveUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentPost)) {
            return false;
        }
        return id != null && id.equals(((DocumentPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPost{" +
            "id=" + getId() +
            ", documentId=" + getDocumentId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", tags='" + getTags() + "'" +
            ", viewTotal=" + getViewTotal() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", approveDatetime='" + getApproveDatetime() + "'" +
            ", approveUser=" + getApproveUser() +
            "}";
    }
}
