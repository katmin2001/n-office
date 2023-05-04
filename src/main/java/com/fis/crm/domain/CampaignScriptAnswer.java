package com.fis.crm.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "CAMPAIGN_SCRIPT_ANWSER")
@EntityListeners(AuditingEntityListener.class)
public class CampaignScriptAnswer {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMPAIGN_SCRIPT_ANSWER_GEN_ID")
    @SequenceGenerator(name = "CAMPAIGN_SCRIPT_ANSWER_GEN_ID", sequenceName = "CAMPAIGN_SCRIPT_ANWSER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "CODE")
    private String code;

//    0: One, 1: Multi, 2: Text, 3: Rank
    @Column(name = "TYPE")
    private String type;

    @Column(name = "POSITION")
    private Integer position;

//    0: none, 1: show
    @Column(name = "DISPLAY")
    private String display;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MIN_VALUE")
    private Integer min;

    @Column(name = "MAX_VALUE")
    private Integer max;

    @Column(name = "CAMPAIGN_SCRIPT_ID")
    private Long campaignScriptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_SCRIPT_QUESTION_ID")
    private CampaignScriptQuestion question;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Long getCampaignScriptId() {
        return campaignScriptId;
    }

    public void setCampaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public CampaignScriptQuestion getQuestion() {
        return question;
    }

    public void setQuestion(CampaignScriptQuestion question) {
        this.question = question;
    }
}
