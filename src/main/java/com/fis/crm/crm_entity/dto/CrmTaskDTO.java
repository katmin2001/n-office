package com.fis.crm.crm_entity.DTO;

import java.sql.Date;

public class CrmTaskDTO {
    private Long taskId;
    private String taskName;
    private Long statusCode;
    private Long giverTaskId;
    private Long receiverTaskId;
    private Date startDate;
    private Date endDate;
    private Long stageId;

    public Long getTaskId() {
        return taskId;
    }

    public CrmTaskDTO() {

    }

    public CrmTaskDTO(Long taskId, String taskName, Long statusCode, Long giverTaskId, Long receiverTaskId, Date startDate, Date endDate, Long stageId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.statusCode = statusCode;
        this.giverTaskId = giverTaskId;
        this.receiverTaskId = receiverTaskId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.stageId = stageId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatuscode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public Long getGiverTaskId() {
        return giverTaskId;
    }

    public void setGiverTaskId(Long giverTaskId) {
        this.giverTaskId = giverTaskId;
    }

    public Long getReceiverTaskId() {
        return receiverTaskId;
    }

    public void setReceiverTaskId(Long receiverTaskId) {
        this.receiverTaskId = receiverTaskId;
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

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    @Override
    public String toString() {
        return "CrmTaskDTO{" +
            "taskId=" + taskId +
            ", taskName='" + taskName + '\'' +
            ", statusCode=" + statusCode +
            ", giverTaskId=" + giverTaskId +
            ", receiverTaskId=" + receiverTaskId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", stageId=" + stageId +
            '}';
    }
}
