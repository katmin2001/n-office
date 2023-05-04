package com.fis.crm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "evaluate_result_history")
public class EvaluateResultHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVALUATE_RESULT_HISTORY_GEN" )
    @SequenceGenerator(name = "EVALUATE_RESULT_HISTORY_GEN", sequenceName = "EVALUATE_RESULT_HISTORY_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "EVALUATE_RESULT_ID")
    private Long evaluateResultId;

    @Column(name = "channel_type")
    private String channelType;

    @Column(name = "durations")
    private Double durations;

    @Column(name = "evaluater_id")
    private Long evaluaterId;

    @Column(name = "evaluated_id")
    private Long evaluatedId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "criteria_rating_id")
    private Long criteriaRatingId;

    @Column(name = "total_scores")
    private Long totalScores;

    @Column(name = "criteria_rating_new_id")
    private Long criteriaRatingNewId;

    @Column(name = "total_scores_new")
    private Long totalScoresNew;

    @Column(name = "error1")
    private String error1;

    @Column(name = "error2")
    private String error2;

    @Column(name = "content")
    private String content;

    @Column(name = "suggest")
    private String suggest;

    @Column(name = "create_datetime")
    private Date createDatetime;

    @Column(name = "create_datetime_new")
    private Date createDatetimeNew;

    @Column(name = "EVALUATE_STATUS")
    private String evaluateStatus;

    @OneToMany(
        mappedBy = "evaluateResultHistory",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<EvaluateResultDetailHistory> evaluateResultDetailHistories = new ArrayList<>();

    public Long getEvaluateResultId() {
        return evaluateResultId;
    }

    public void setEvaluateResultId(Long evaluateResultId) {
        this.evaluateResultId = evaluateResultId;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public List<EvaluateResultDetailHistory> getEvaluateResultDetailHistories() {
        return evaluateResultDetailHistories;
    }

    public void setEvaluateResultDetailHistories(List<EvaluateResultDetailHistory> evaluateResultDetailHistories) {
        this.evaluateResultDetailHistories = evaluateResultDetailHistories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public EvaluateResultHistory objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getChannelType() {
        return channelType;
    }

    public EvaluateResultHistory channelType(String channelType) {
        this.channelType = channelType;
        return this;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Double getDurations() {
        return durations;
    }

    public EvaluateResultHistory durations(Double durations) {
        this.durations = durations;
        return this;
    }

    public void setDurations(Double durations) {
        this.durations = durations;
    }

    public Long getEvaluaterId() {
        return evaluaterId;
    }

    public EvaluateResultHistory evaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
        return this;
    }

    public void setEvaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
    }

    public Long getEvaluatedId() {
        return evaluatedId;
    }

    public EvaluateResultHistory evaluatedId(Long evaluatedId) {
        this.evaluatedId = evaluatedId;
        return this;
    }

    public void setEvaluatedId(Long evaluatedId) {
        this.evaluatedId = evaluatedId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EvaluateResultHistory phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCriteriaRatingId() {
        return criteriaRatingId;
    }

    public EvaluateResultHistory criteriaRatingId(Long criteriaRatingId) {
        this.criteriaRatingId = criteriaRatingId;
        return this;
    }

    public void setCriteriaRatingId(Long criteriaRatingId) {
        this.criteriaRatingId = criteriaRatingId;
    }

    public Long getTotalScores() {
        return totalScores;
    }

    public EvaluateResultHistory totalScores(Long totalScores) {
        this.totalScores = totalScores;
        return this;
    }

    public void setTotalScores(Long totalScores) {
        this.totalScores = totalScores;
    }

    public Long getCriteriaRatingNewId() {
        return criteriaRatingNewId;
    }

    public EvaluateResultHistory criteriaRatingNewId(Long criteriaRatingNewId) {
        this.criteriaRatingNewId = criteriaRatingNewId;
        return this;
    }

    public void setCriteriaRatingNewId(Long criteriaRatingNewId) {
        this.criteriaRatingNewId = criteriaRatingNewId;
    }

    public Long getTotalScoresNew() {
        return totalScoresNew;
    }

    public EvaluateResultHistory totalScoresNew(Long totalScoresNew) {
        this.totalScoresNew = totalScoresNew;
        return this;
    }

    public void setTotalScoresNew(Long totalScoresNew) {
        this.totalScoresNew = totalScoresNew;
    }

    public String getError1() {
        return error1;
    }

    public EvaluateResultHistory error1(String error1) {
        this.error1 = error1;
        return this;
    }

    public void setError1(String error1) {
        this.error1 = error1;
    }

    public String getError2() {
        return error2;
    }

    public EvaluateResultHistory error2(String error2) {
        this.error2 = error2;
        return this;
    }

    public void setError2(String error2) {
        this.error2 = error2;
    }

    public String getContent() {
        return content;
    }

    public EvaluateResultHistory content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuggest() {
        return suggest;
    }

    public EvaluateResultHistory suggest(String suggest) {
        this.suggest = suggest;
        return this;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public EvaluateResultHistory createDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
        return this;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getCreateDatetimeNew() {
        return createDatetimeNew;
    }

    public EvaluateResultHistory createDatetimeNew(Date createDatetimeNew) {
        this.createDatetimeNew = createDatetimeNew;
        return this;
    }

    public void setCreateDatetimeNew(Date createDatetimeNew) {
        this.createDatetimeNew = createDatetimeNew;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateResultHistory)) {
            return false;
        }
        return id != null && id.equals(((EvaluateResultHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateResult{" +
            "id=" + getId() +
            ", objectId=" + getObjectId() +
            ", channelType='" + getChannelType() + "'" +
            ", durations=" + getDurations() +
            ", evaluaterId=" + getEvaluaterId() +
            ", evaluatedId=" + getEvaluatedId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", criteriaRatingId=" + getCriteriaRatingId() +
            ", totalCores=" + getTotalScores() +
            ", criteriaRatingNewId=" + getCriteriaRatingNewId() +
            ", totalScoresNew=" + getTotalScoresNew() +
            ", error1='" + getError1() + "'" +
            ", error2='" + getError2() + "'" +
            ", content='" + getContent() + "'" +
            ", suggest='" + getSuggest() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createDatetimeNew='" + getCreateDatetimeNew() + "'" +
            "}";
    }
}
