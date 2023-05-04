package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.DocumentPost} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentPostDTO implements Serializable {

    private Long id;

    @NotNull(message = "{documentPost.documentIdNull}")
    private Long documentId;

    private String name;

    @NotNull(message = "{documentPost.titleNull}")
    @NotEmpty(message = "{documentPost.titleEmpty}")
    private String title;

//    @Length(max = 5000, message = "{documentPost.contentMaxLength2000}")
    @NotNull(message = "{documentPost.contentNull}")
    @NotEmpty(message = "{documentPost.contentEmpty}")
    private String content;

    private String status;

    private String tags;

    private Long viewTotal;

    private Instant createDatetime;

    private Long createUser;

    private String approveStatus;

    private Instant approveDatetime;

    private Long approveUser;

    private List<Long> lstDelAttachs;

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public List<Long> getLstDelAttachs() {
        return lstDelAttachs;
    }

    public void setLstDelAttachs(List<Long> lstDelAttachs) {
        this.lstDelAttachs = lstDelAttachs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getViewTotal() {
        return viewTotal;
    }

    public void setViewTotal(Long viewTotal) {
        this.viewTotal = viewTotal;
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

    public Instant getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(Instant approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public Long getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(Long approveUser) {
        this.approveUser = approveUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentPostDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentPostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentPostDTO{" +
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
