package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DocumentPostBookmarks.
 */
@Entity
@Table(name = "document_post_bookmarks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentPostBookmarks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_post_id")
    private Long documentPostId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentPostId() {
        return documentPostId;
    }

    public DocumentPostBookmarks documentPostId(Long documentPostId) {
        this.documentPostId = documentPostId;
        return this;
    }

    public void setDocumentPostId(Long documentPostId) {
        this.documentPostId = documentPostId;
    }

    public Long getUserId() {
        return userId;
    }

    public DocumentPostBookmarks userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public DocumentPostBookmarks createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
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
        if (!(o instanceof DocumentPostBookmarks)) {
            return false;
        }
        return id != null && id.equals(((DocumentPostBookmarks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPostBookmarks{" +
            "id=" + getId() +
            ", documentPostId=" + getDocumentPostId() +
            ", userId=" + getUserId() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            "}";
    }
}
