package com.fis.crm.service.dto;

import com.fis.crm.domain.DocumentPostView;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author tamdx
 */
@Entity
public class DocumentPostViewDetail {
    @Id
    private Long id;

    private Long documentPostId;

    private Long userId;

    private Instant createDatetime;
    private String userName;
    private String firstName;

    public DocumentPostViewDetail() {
    }

    public DocumentPostViewDetail(DocumentPostView documentPostView,
                                  String userName, String firstName) {
        this.id = documentPostView.getId();
        this.documentPostId = documentPostView.getDocumentPostId();
        this.userId = documentPostView.getUserId();
        this.createDatetime = documentPostView.getCreateDatetime();
        this.userName = userName;
        this.firstName = firstName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
