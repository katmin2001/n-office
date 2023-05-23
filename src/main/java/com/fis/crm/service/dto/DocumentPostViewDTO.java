package com.fis.crm.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.DocumentPostView} entity.
 */
public class DocumentPostViewDTO implements Serializable {

    private Long id;

    private Long documentPostId;

    private Long userId;

    private Instant createDatetime;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentPostViewDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentPostViewDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPostViewDTO{" +
            "id=" + getId() +
            ", documentPostId=" + getDocumentPostId() +
            ", userId=" + getUserId() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            "}";
    }
}
