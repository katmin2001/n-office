package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.EvaluateAssignment} entity.
 */
public class EvaluateAssignmentDataDTO implements Serializable {

    private Long id;

    private String channelId;

    private Long evaluaterId;

    private String evaluaterName;

    private Long businessTypeId;

    private Long totalCall;

    private Long totalUserId;

    private Date startDate;

    private Date endDate;

    private Date createDatetime;

    private Long createUser;
    private String createUserName;

    private Date updateDatetime;

    private Long updateUser;

    private List<UserDTO> lstUserDTO;

    private Long userId;

    private String names;

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    public String getEvaluaterName() {
        return evaluaterName;
    }

    public void setEvaluaterName(String evaluaterName) {
        this.evaluaterName = evaluaterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluateAssignmentDataDTO)) {
            return false;
        }

        return id != null && id.equals(((EvaluateAssignmentDataDTO) o).id);
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
            ", totalCal=" + getTotalCall() +
            ", totalUserId=" + getTotalUserId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
