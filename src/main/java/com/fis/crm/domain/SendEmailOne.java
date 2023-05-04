package com.fis.crm.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "SEND_EMAIL_ONE")
public class SendEmailOne {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEND_EMAIL_ONE_GEN")
    @SequenceGenerator(name = "SEND_EMAIL_ONE_GEN", sequenceName = "SEND_EMAIL_ONE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TO_EMAIL")
    private String toEmail;

    @Column(name = "CC_EMAIL")
    private String ccEmail;

    @Column(name = "BCC_EMAIL")
    private String bccEmail;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_DATETIME")
    private Instant createDateTime;

    @Column(name = "CREATE_USER")
    private Long createUser;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SEND_DATE")
    private Instant sendDate;

    @Column(name = "RESPONSE")
    private String response;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Instant createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
