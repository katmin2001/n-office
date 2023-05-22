package com.fis.crm.crm_entity.DTO;

import java.sql.Date;
import java.util.Set;

public class InterviewDTO {
    private Long interviewId;
    private Date interviewDate;
    private Boolean status;
    private Date createDate;
    private String candidateName;
    private String interviewStatus;
    private Set<Crm_UserDTO> crmUserDTOS;

    public InterviewDTO() {
    }

    public InterviewDTO(Long interviewId, Date interviewDate, Boolean status, Date createDate, String candidateName, String interviewStatus, Set<Crm_UserDTO> crmUserDTOS) {
        this.interviewId = interviewId;
        this.interviewDate = interviewDate;
        this.status = status;
        this.createDate = createDate;
        this.candidateName = candidateName;
        this.interviewStatus = interviewStatus;
        this.crmUserDTOS = crmUserDTOS;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

    public Set<Crm_UserDTO> getCrmUserDTOS() {
        return crmUserDTOS;
    }

    public void setCrmUserDTOS(Set<Crm_UserDTO> crmUserDTOS) {
        this.crmUserDTOS = crmUserDTOS;
    }
}
