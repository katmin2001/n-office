package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.EvaluateAssignment} entity.
 */
public class EvaluateAssignmentDTO implements Serializable {

    private Long id;

    private String channelId;

    private Long evaluaterId;

    private Long businessTypeId;

    private Long totalCall;

    private Long totalUserId;

    private Date startDate;

    private Date endDate;

    private String evaluateStatus;

    private Date createDatetime;

    private Long createUser;

    private Date updateDatetime;

    private Long updateUser;

    private List<UserDTO> lstUserDTO;

    private Long userId;

    private List<EvaluateAssignmentDetailDTO> evaluateAssignmentDetailsDTO = new ArrayList<>();


    public List<EvaluateAssignmentDetailDTO> getEvaluateAssignmentDetailsDTO() {
        return evaluateAssignmentDetailsDTO;
    }

    public void setEvaluateAssignmentDetailsDTO(List<EvaluateAssignmentDetailDTO> evaluateAssignmentDetailsDTO) {
        this.evaluateAssignmentDetailsDTO = evaluateAssignmentDetailsDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UserDTO> getLstUserDTO() {
        return lstUserDTO;
    }

    public void setLstUserDTO(List<UserDTO> lstUserDTO) {
        this.lstUserDTO = lstUserDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Long getEvaluaterId() {
        return evaluaterId;
    }

    public void setEvaluaterId(Long evaluaterId) {
        this.evaluaterId = evaluaterId;
    }

    public Long getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(Long totalCal) {
        this.totalCall = totalCal;
    }

    public Long getTotalUserId() {
        return totalUserId;
    }

    public void setTotalUserId(Long totalUserId) {
        this.totalUserId = totalUserId;
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

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getEvaluateStatus() {
        return evaluateStatus;
    }

    public void setEvaluateStatus(String evaluateStatus) {
        this.evaluateStatus = evaluateStatus;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateAssignmentDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluateAssignmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluateAssignmentDTO{" +
            "id=" + getId() +
            ", channelId='" + getChannelId() + "'" +
            ", evaluaterId=" + getEvaluaterId() +
            ", businessTypeId=" + getBusinessTypeId() +
            ", totalCal=" + getTotalCall() +
            ", totalUserId=" + getTotalUserId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", evaluateStatus='" + getEvaluateStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
