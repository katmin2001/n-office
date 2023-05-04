package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.EvaluateResult} entity.
 */
public class EvaluateResultDTO implements Serializable {

    private Long id;

    private Long objectId;

    private String channelType;
    private String channelTypeName;

    private Double durations;

    private String startCall;

    private String callFile;

    private Long evaluaterId;

    private Long evaluatedId;

    private String phoneNumber;

    private Long criteriaRatingId;
    private String criteriaRatingName;

    private Long totalScores;

    private Long criteriaRatingNewId;

    private Long totalScoresNew;

    private String error1;
    private String error1Name;

    private String error2;
    private String error2Name;

    private String content;

    private String suggest;

    private Date createDatetime;
    private String createDatetimeName;

    private Date createDatetimeNew;

    private String recordLink;

    private String evaluaterName;

    private String evaluatedName;

    private List<CriteriaDetailDTO> criteriaDetailDTOS;

    List<EvaluateResultDetailDTO> evaluateResultDetailDTOS;

    private Date startDate;

    private Date endDate;

    private Date recordDate;
    private String recordDateName;
    private String evaluateStatus;

    private Long evaluateAssignmentDetailId;
    private Date callDatetime;

    public String getStartCall() {
        return startCall;
    }

    public void setStartCall(String startCall) {
        this.startCall = startCall;
    }

    public String getCallFile() {
        return callFile;
    }

    public void setCallFile(String callFile) {
        this.callFile = callFile;
    }

    public Date getCallDatetime() {
        return callDatetime;
    }

    public void setCallDatetime(Date callDatetime) {
        this.callDatetime = callDatetime;
    }

    public Long getEvaluateAssignmentDetailId() {
        return evaluateAssignmentDetailId;
    }

    public void setEvaluateAssignmentDetailId(Long evaluateAssignmentDetailId) {
        this.evaluateAssignmentDetailId = evaluateAssignmentDetailId;
    }

    public String getCreateDatetimeName() {
        return createDatetimeName;
    }

    public void setCreateDatetimeName(String createDatetimeName) {
        this.createDatetimeName = createDatetimeName;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public String getRecordDateName() {
        return recordDateName;
    }

    public void setRecordDateName(String recordDateName) {
        this.recordDateName = recordDateName;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getCriteriaRatingName() {
        return criteriaRatingName;
    }

    public void setCriteriaRatingName(String criteriaRatingName) {
        this.criteriaRatingName = criteriaRatingName;
    }

    public String getError1Name() {
        return error1Name;
    }

    public void setError1Name(String error1Name) {
        this.error1Name = error1Name;
    }

    public String getError2Name() {
        return error2Name;
    }

    public void setError2Name(String error2Name) {
        this.error2Name = error2Name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<EvaluateResultDetailDTO> getEvaluateResultDetailDTOS() {
        return evaluateResultDetailDTOS;
    }

    public void setEvaluateResultDetailDTOS(List<EvaluateResultDetailDTO> evaluateResultDetailDTOS) {
        this.evaluateResultDetailDTOS = evaluateResultDetailDTOS;
    }

    public List<CriteriaDetailDTO> getCriteriaDetailDTOS() {
        return criteriaDetailDTOS;
    }

    public void setCriteriaDetailDTOS(List<CriteriaDetailDTO> criteriaDetailDTOS) {
        this.criteriaDetailDTOS = criteriaDetailDTOS;
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

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Double getDurations() {
        return durations;
    }

    public void setDurations(Double durations) {
        this.durations = durations;
    }

    public Long getEvaluaterId() {
        return evaluaterId;
    }

    public void setEvaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
    }

    public Long getEvaluatedId() {
        return evaluatedId;
    }

    public void setEvaluatedId(Long evaluatedId) {
        this.evaluatedId = evaluatedId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCriteriaRatingId() {
        return criteriaRatingId;
    }

    public void setCriteriaRatingId(Long criteriaRatingId) {
        this.criteriaRatingId = criteriaRatingId;
    }

    public Long getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(Long totalScores) {
        this.totalScores = totalScores;
    }

    public Long getCriteriaRatingNewId() {
        return criteriaRatingNewId;
    }

    public void setCriteriaRatingNewId(Long criteriaRatingNewId) {
        this.criteriaRatingNewId = criteriaRatingNewId;
    }

    public Long getTotalScoresNew() {
        return totalScoresNew;
    }

    public void setTotalScoresNew(Long totalScoresNew) {
        this.totalScoresNew = totalScoresNew;
    }

    public String getError1() {
        return error1;
    }

    public void setError1(String error1) {
        this.error1 = error1;
    }

    public String getError2() {
        return error2;
    }

    public void setError2(String error2) {
        this.error2 = error2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getCreateDatetimeNew() {
        return createDatetimeNew;
    }

    public void setCreateDatetimeNew(Date createDatetimeNew) {
        this.createDatetimeNew = createDatetimeNew;
    }

    public String getRecordLink() {
        return recordLink;
    }

    public void setRecordLink(String recordLink) {
        this.recordLink = recordLink;
    }

    public String getEvaluaterName() {
        return evaluaterName;
    }

    public void setEvaluaterName(String evaluaterName) {
        this.evaluaterName = evaluaterName;
    }

    public String getEvaluatedName() {
        return evaluatedName;
    }

    public void setEvaluatedName(String evaluatedName) {
        this.evaluatedName = evaluatedName;
    }
}
