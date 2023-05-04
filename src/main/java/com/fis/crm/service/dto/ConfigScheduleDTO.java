package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis.crm.commons.DataUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.Instant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * A DTO for the {@link com.fis.crm.domain.ConfigSchedule} entity.
 */
public class ConfigScheduleDTO implements Serializable {

    private Long id;

    private Long requestType;
    private String requestTypeStr;

    private Long bussinessType;
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

    private int page;

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    private int size;
    private String keySearch;
    private String errorCodeConfig;

    private String importType;
    private String error;
    @JsonIgnore
    private Workbook xssfWorkbook;

    public Workbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(Workbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public ConfigScheduleDTO() {
    }

    public ConfigScheduleDTO(Long id, Long requestType, Long bussinessType, Double processTime, String processTimeType, Double confirmTime, String confirmTimeType, String status, Long createUser, Instant createDate, Long updateUser, Instant updateDate, int page, int size, String keySearch, String errorCodeConfig) {
        this.id = id;
        this.requestType = requestType;
        this.bussinessType = bussinessType;
        this.processTime = processTime;
        this.processTimeType = processTimeType;
        this.confirmTime = confirmTime;
        this.confirmTimeType = confirmTimeType;
        this.status = status;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.page = page;
        this.size = size;
        this.keySearch = keySearch;
        this.errorCodeConfig = errorCodeConfig;
    }

    public ConfigScheduleDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.requestType = DataUtil.safeToLong(objects[i++]);
        this.requestTypeStr = DataUtil.safeToString(objects[i++]);
        this.bussinessType = DataUtil.safeToLong(objects[i++]);
        this.bussinessTypeStr = DataUtil.safeToString(objects[i++]);
        this.processTime = DataUtil.safeToDouble(objects[i++]);
        this.processTimeType = DataUtil.safeToString(objects[i++]);
        this.confirmTime = DataUtil.safeToDouble(objects[i++]);
        this.confirmTimeType = DataUtil.safeToString(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(String errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof ConfigScheduleDTO)) {
            return false;
        }

        return id != null && id.equals(((ConfigScheduleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
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
            ", page=" + page +
            ", size=" + size +
            ", keySearch='" + keySearch + '\'' +
            '}';
    }
}
