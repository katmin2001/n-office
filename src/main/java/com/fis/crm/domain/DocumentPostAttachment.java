package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DocumentPostAttachment.
 */
@Entity
@Table(name = "document_post_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentPostAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_post_id")
    private Long documentPostId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_name_encrypt")
    private String fileNameEncrypt;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "download_total")
    private Long downloadTotal;

    @Column(name = "status")
    private String status;

    @Column(name = "create_datetime")
    private Instant createDatetime;

    @Column(name = "create_user")
    private Long createUser;

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

    public DocumentPostAttachment documentPostId(Long documentPostId) {
        this.documentPostId = documentPostId;
        return this;
    }

    public void setDocumentPostId(Long documentPostId) {
        this.documentPostId = documentPostId;
    }

    public String getFileName() {
        return fileName;
    }

    public DocumentPostAttachment fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameEncrypt() {
        return fileNameEncrypt;
    }

    public DocumentPostAttachment fileNameEncrypt(String fileNameEncrypt) {
        this.fileNameEncrypt = fileNameEncrypt;
        return this;
    }

    public void setFileNameEncrypt(String fileNameEncrypt) {
        this.fileNameEncrypt = fileNameEncrypt;
    }

    public Double getCapacity() {
        return capacity;
    }

    public DocumentPostAttachment capacity(Double capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Long getDownloadTotal() {
        return downloadTotal;
    }

    public DocumentPostAttachment downloadTotal(Long downloadTotal) {
        this.downloadTotal = downloadTotal;
        return this;
    }

    public void setDownloadTotal(Long downloadTotal) {
        this.downloadTotal = downloadTotal;
    }

    public String getStatus() {
        return status;
    }

    public DocumentPostAttachment status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public DocumentPostAttachment createDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public DocumentPostAttachment createUser(Long createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentPostAttachment)) {
            return false;
        }
        return id != null && id.equals(((DocumentPostAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPostAttachment{" +
            "id=" + getId() +
            ", documentPostId=" + getDocumentPostId() +
            ", fileName='" + getFileName() + "'" +
            ", fileNameEncrypt='" + getFileNameEncrypt() + "'" +
            ", capacity=" + getCapacity() +
            ", downloadTotal=" + getDownloadTotal() +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            "}";
    }
}
