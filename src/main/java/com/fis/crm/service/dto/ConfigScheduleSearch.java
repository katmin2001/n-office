package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class ConfigScheduleSearch implements Serializable {

    @Id
    private Long id;

    private Integer requestType;
    private String requestTypeStr;

    private Integer bussinessType;
    private String bussinessTypeStr;

    private Double processTime;
    private String processTimeStr;

    private String processTimeType;

    private Double confirmTime;
    private String confirmTimeStr;

    private String confirmTimeType;

    private String status;

    private Long createUser;

    private Instant createDate;

    private Long updateUser;

    private Instant updateDate;

    public ConfigScheduleSearch(Long id, Integer requestType, String requestTypeStr, Integer bussinessType, String bussinessTypeStr, Double processTime, String processTimeType, Double confirmTime, String confirmTimeType, String status) {
        this.id = id;
        this.requestType = requestType;
        this.requestTypeStr = requestTypeStr;
        this.bussinessType = bussinessType;
        this.bussinessTypeStr = bussinessTypeStr;
        this.processTime = processTime;
        this.processTimeType = processTimeType;
        this.confirmTime = confirmTime;
        this.confirmTimeType = confirmTimeType;
        this.status = status;
    }

    public ConfigScheduleSearch() {

    }

    public String getRequestTypeStr() {
        return requestTypeStr;
    }

    public void setRequestTypeStr(String requestTypeStr) {
        this.requestTypeStr = requestTypeStr;
    }

    public String getBussinessTypeStr() {
        return bussinessTypeStr;
    }

    public void setBussinessTypeStr(String bussinessTypeStr) {
        this.bussinessTypeStr = bussinessTypeStr;
    }

    public String getProcessTimeStr() {
        return processTimeStr;
    }

    public void setProcessTimeStr(String processTimeStr) {
        this.processTimeStr = processTimeStr;
    }

    public String getConfirmTimeStr() {
        return confirmTimeStr;
    }

    public void setConfirmTimeStr(String confirmTimeStr) {
        this.confirmTimeStr = confirmTimeStr;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(Integer bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Double getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Double processTime) {
        this.processTime = processTime;
    }

    public String getProcessTimeType() {
        return processTimeType;
    }

    public void setProcessTimeType(String processTimeType) {
        this.processTimeType = processTimeType;
    }

    public Double getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Double confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmTimeType() {
        return confirmTimeType;
    }

    public void setConfirmTimeType(String confirmTimeType) {
        this.confirmTimeType = confirmTimeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigScheduleSearch)) {
            return false;
        }

        return id != null && id.equals(((ConfigScheduleSearch) o).id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
// prettier-ignore

    @Override
    public String toString() {
        return "ConfigScheduleDTO{" +
            "id=" + id +
            ", requestType=" + requestType +
            ", bussinessType=" + bussinessType +
            ", processTime=" + processTime +
            ", processTimeType='" + processTimeType + '\'' +
            ", confirmTime=" + confirmTime +
            ", confirmTimeType='" + confirmTimeType + '\'' +
            ", status='" + status + '\'' +
            ", createUser=" + createUser +
            ", createDate=" + createDate +
            ", updateUser=" + updateUser +
            ", updateDate=" + updateDate +
            '}';
    }
}
