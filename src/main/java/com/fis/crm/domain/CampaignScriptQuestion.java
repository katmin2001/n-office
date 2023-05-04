package com.fis.crm.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CAMPAIGN_SCRIPT_QUESTION")
public class CampaignScriptQuestion {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SCRIPT_QUESTION_GEN")
    @SequenceGenerator(name = "CAMPAIGN_SCRIPT_QUESTION_GEN", sequenceName = "CAMPAIGN_SCRIPT_QUESTION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "POSITION")
    private Integer position;

    //0: none, 1: show
    @Column(name = "DISPLAY")
    private String display;

    @Column(name = "CONTENT")
    private String content;

    //0: in use, 1: chua su dung
    @Column(name = "STATUS")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_SCRIPT_ID")
    private CampaignScript campaignScript;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<CampaignScriptAnswer> answers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_USER")
    private User createUser;

    @CreatedDate
    @Column(name = "CREATE_DATETIME")
    private Instant createDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATE_USER")
    private User updateUser;

    @LastModifiedDate
    @Column(name = "UPDATE_DATETIME")
    private Instant updateDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CampaignScript getCampaignScript() {
        return campaignScript;
    }

    public void setCampaignScript(CampaignScript campaignScript) {
        this.campaignScript = campaignScript;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public List<CampaignScriptAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<CampaignScriptAnswer> answers) {
        this.answers = answers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }
}
