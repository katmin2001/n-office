package com.fis.crm.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.CampaignScript} entity.
 */
public class CampaignScriptDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String startContent;

    private String endContent;

    private String status;

    private Instant createDatetime;

    private Long createUserId;

    private Instant updateDatetime;

    private Long updateUserId;

    private String createUsername;

    private String updateUsername;

    private String createFullName;

    private String updateFullName;

    //custom properties
    private String errorCodeConfig;

    private String isUsed;

    public String getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(String errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartContent() {
        return startContent;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }

    public String getEndContent() {
        return endContent;
    }

    public void setEndContent(String endContent) {
        this.endContent = endContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Instant updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

    public String getUpdateFullName() {
        return updateFullName;
    }

    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignScriptDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignScriptDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampaignScriptDTO{" +
            "id=" + id +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", startContent='" + startContent + '\'' +
            ", endContent='" + endContent + '\'' +
            ", status='" + status + '\'' +
            ", createDatetime=" + createDatetime +
            ", createUserId=" + createUserId +
            ", updateDatetime=" + updateDatetime +
            ", updateUserId=" + updateUserId +
            ", createUsername='" + createUsername + '\'' +
            ", updateUsername='" + updateUsername + '\'' +
            ", createFullName='" + createFullName + '\'' +
            ", updateFullName='" + updateFullName + '\'' +
            ", errorCodeConfig='" + errorCodeConfig + '\'' +
            '}';
    }
}
