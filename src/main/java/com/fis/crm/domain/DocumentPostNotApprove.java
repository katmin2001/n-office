package com.fis.crm.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author tamdx
 */
@Entity
public class DocumentPostNotApprove {
    @Id
    private Long id;
    private Long documentId;
    private String name;
    private String title;
    private String content;
    private String status;
    private String tags;
    private Long viewTotal;
    private Instant createDatetime;
    private Long createUser;
    private String approveStatus;
    private Instant approveDatetime;
    private Long approveUser;
    private String documentName;
    private String createUserName;

    public DocumentPostNotApprove() {
    }
    public DocumentPostNotApprove(DocumentPost documentPost, String documentName, String createUserName) {
        this.id = documentPost.getId();
        this.documentId = documentPost.getId();
        this.name = documentPost.getName();
        this.title = documentPost.getTitle();
        this.content = documentPost.getContent();
        this.status = documentPost.getStatus();
        this.tags = documentPost.getTags();
        this.viewTotal = documentPost.getViewTotal();
        this.createDatetime = documentPost.getCreateDatetime();
        this.createUser = documentPost.getCreateUser();
        this.approveStatus = documentPost.getApproveStatus();
        this.approveDatetime = documentPost.getApproveDatetime();
        this.approveUser = documentPost.getApproveUser();
        this.documentName = documentName;
        this.createUserName = createUserName;
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

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
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

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
