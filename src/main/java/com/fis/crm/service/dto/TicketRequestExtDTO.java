package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.fis.crm.domain.TicketRequest} entity.
 */
public class TicketRequestExtDTO implements Serializable {


    private Long ticketRequestId;

    private Long ticketId;

    private String ticketRequestCode;

    private Long requestType;

    private Long bussinessType;

    private Long priority;

    private Long departmentId;

    private String status;

    private Instant deadline;

    private String content;

    private Instant confirmDate;

    private Long timeNotify;

    private Long createUser;

    private String createUserName;

    private Long updateUser;

    private String updateUserName;

    private Instant createDatetime;

    private Instant updateDatetime;

    private Long no;

    private boolean disableCloseSave;

    private String createDepartments;

    public boolean isDisableCloseSave() {
        return disableCloseSave;
    }

    public void setDisableCloseSave(boolean disableCloseSave) {
        this.disableCloseSave = disableCloseSave;
    }

    public TicketRequestExtDTO() {
    }

    public TicketRequestExtDTO(Object[] objects) {
        int i = 0;
        this.ticketRequestId = DataUtil.safeToLong(objects[i++]);
        this.ticketId = DataUtil.safeToLong(objects[i++]);
        this.ticketRequestCode = DataUtil.safeToString(objects[i++]);
        this.requestType = DataUtil.safeToLong(objects[i++]);
        this.bussinessType = DataUtil.safeToLong(objects[i++]);
        this.priority = DataUtil.safeToLong(objects[i++]);
        this.departmentId = DataUtil.safeToLong(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.deadline = DataUtil.safeToInstant(objects[i++]);
        this.content = DataUtil.safeToString(objects[i++]);
        this.confirmDate = DataUtil.safeToInstant(objects[i++]);
        this.timeNotify = DataUtil.safeToLong(objects[i++]);
        this.createUser = DataUtil.safeToLong(objects[i++]);
        this.updateUser = DataUtil.safeToLong(objects[i++]);
        this.createDatetime = DataUtil.safeToInstant(objects[i++]);
        this.updateDatetime = DataUtil.safeToInstant(objects[i++]);
        this.no = DataUtil.safeToLong(objects[i++]);
        this.createUserName = DataUtil.safeToString(objects[i++]);
        this.createDepartments = DataUtil.safeToString(objects[i++]);
    }

    public String getCreateDepartments() {
        return createDepartments;
    }

    public void setCreateDepartments(String createDepartments) {
        this.createDepartments = createDepartments;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }


    public Long getTicketRequestId() {
        return ticketRequestId;
    }

    public void setTicketRequestId(Long ticketRequestId) {
        this.ticketRequestId = ticketRequestId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketRequestCode() {
        return ticketRequestCode;
    }

    public void setTicketRequestCode(String ticketRequestCode) {
        this.ticketRequestCode = ticketRequestCode;
    }

    public Long getRequestType() {
        return requestType;
    }

    public void setRequestType(Long requestType) {
        this.requestType = requestType;
    }

    public Long getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(Long bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Instant confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getTimeNotify() {
        return timeNotify;
    }

    public void setTimeNotify(Long timeNotify) {
        this.timeNotify = timeNotify;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketRequestExtDTO)) {
            return false;
        }

        TicketRequestExtDTO ticketRequestDTO = (TicketRequestExtDTO) o;
        if (this.ticketRequestId == null) {
            return false;
        }
        return Objects.equals(this.ticketRequestId, ticketRequestDTO.ticketRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ticketRequestId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketRequestDTO{" +
            " ticketRequestId=" + getTicketRequestId() +
            ", ticketId='" + getTicketId() + "'" +
            ", ticketRequestCode='" + getTicketRequestCode() + "'" +
            ", requestType=" + getRequestType() +
            ", bussinessType=" + getBussinessType() +
            ", priority=" + getPriority() +
            ", departmentId=" + getDepartmentId() +
            ", status='" + getStatus() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", content='" + getContent() + "'" +
            ", confirmDate='" + getConfirmDate() + "'" +
            ", timeNotify=" + getTimeNotify() +
            ", createUser=" + getCreateUser() +
            ", updateUser=" + getUpdateUser() +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            "}";
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
