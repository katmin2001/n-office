package com.fis.crm.service.dto;

import com.fis.crm.domain.DocumentPostAttachment;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.DocumentPostAttachment} entity.
 */
@Entity
public class DocumentPostAttachmentDTO implements Serializable {

    @Id
    private Long id;

    private Long documentPostId;

    private String fileName;

    private String fileNameEncrypt;

    private Double capacity;

    private Long downloadTotal;

    private String status;

    private Instant createDatetime;

    private Long createUser;
    private String createUserName;

    public DocumentPostAttachmentDTO() {
    }

    public DocumentPostAttachmentDTO(DocumentPostAttachment attachment, String userName) {
        this.id = attachment.getId();
        this.documentPostId = attachment.getDocumentPostId();
        this.fileName = attachment.getFileName();
        this.fileNameEncrypt = attachment.getFileNameEncrypt();
        this.capacity = attachment.getCapacity();
        this.downloadTotal = attachment.getDownloadTotal();
        this.status = attachment.getStatus();
        this.createDatetime = attachment.getCreateDatetime();
        this.createUser = attachment.getCreateUser();
        this.createUserName = userName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentPostId() {
        return documentPostId;
    }

    public void setDocumentPostId(Long documentPostId) {
        this.documentPostId = documentPostId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameEncrypt() {
        return fileNameEncrypt;
    }

    public void setFileNameEncrypt(String fileNameEncrypt) {
        this.fileNameEncrypt = fileNameEncrypt;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Long getDownloadTotal() {
        return downloadTotal;
    }

    public void setDownloadTotal(Long downloadTotal) {
        this.downloadTotal = downloadTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentPostAttachmentDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentPostAttachmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPostAttachmentDTO{" +
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
