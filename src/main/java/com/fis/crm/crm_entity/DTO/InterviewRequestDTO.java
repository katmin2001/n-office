package com.fis.crm.crm_entity.DTO;

import java.sql.Date;
import java.util.List;

public class InterviewRequestDTO {
    private Date interviewDate;
    private Boolean status;
    private Long candidateId;
    private Long ISID;
    private List<Long> userId;

    public InterviewRequestDTO() {
    }

    public InterviewRequestDTO(Date interviewDate, Boolean status, Long candidateId, Long ISID, List<Long> userId) {
        this.interviewDate = interviewDate;
        this.status = status;
        this.candidateId = candidateId;
        this.ISID = ISID;
        this.userId = userId;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getISID() {
        return ISID;
    }

    public void setISID(Long ISID) {
        this.ISID = ISID;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }

    public List<Long> getUserId() {
        return userId;
    }
}
