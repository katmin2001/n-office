package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.fis.crm.domain.Campaign} entity.
 */
public class CampaignDTO implements Serializable {

    private Long id;

    private String name;

    private Long campaignTemplateId;

    private Long campaignScriptId;

    private String groupUser;

    private String displayPhone;

    private Date startDate;

    private Date endDate;

    private String status;

    private Date createDatetime;

    private Long createUser;

    private Date updateDatetime;

    private Long updateUser;




    private List<String> lstGroupUser;
    private String errorCodeConfig;
    private String campaignTemplateName;
    private String campaignScriptName;

    private String[] listCallStatusArray;
    private Integer nullCallFail;

    public String[] getListCallStatusArray() {
        return listCallStatusArray;
    }

    public void setListCallStatusArray(String[] listCallStatusArray) {
        this.listCallStatusArray = listCallStatusArray;
    }

    public Integer getNullCallFail() {
        return nullCallFail;
    }

    public void setNullCallFail(Integer nullCallFail) {
        this.nullCallFail = nullCallFail;
    }

    public String getCampaignTemplateName() {
        return campaignTemplateName;
    }

    public void setCampaignTemplateName(String campaignTemplateName) {
        this.campaignTemplateName = campaignTemplateName;
    }

    public String getCampaignScriptName() {
        return campaignScriptName;
    }

    public void setCampaignScriptName(String campaignScriptName) {
        this.campaignScriptName = campaignScriptName;
    }

    public List<String> getLstGroupUser() {
        return lstGroupUser;
    }

    public void setLstGroupUser(List<String> lstGroupUser) {
        this.lstGroupUser = lstGroupUser;
    }

    public String getErrorCodeConfig() {
        return errorCodeConfig;
    }

    public void setErrorCodeConfig(String errorCodeConfig) {
        this.errorCodeConfig = errorCodeConfig;
    }

    public CampaignDTO() {
    }

    public CampaignDTO(Long id, String name, Long campaignTemplateId, Long campaignScriptId,
                       String groupUser, String displayPhone, Date startDate, Date endDate,
                       String status, Date createDatetime, Long createUser, Date updateDatetime,
                       Long updateUser, String campaignTemplateName, String campaignScriptName
                      ) {
        this.id = id;
        this.name = name;
        this.campaignTemplateId = campaignTemplateId;
        this.campaignScriptId = campaignScriptId;
        this.groupUser = groupUser;
        this.displayPhone = displayPhone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createDatetime = createDatetime;
        this.createUser = createUser;
        this.updateDatetime = updateDatetime;
        this.updateUser = updateUser;
        this.campaignTemplateName = campaignTemplateName;
        this.campaignScriptName = campaignScriptName;
    }

    public CampaignDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
        this.campaignTemplateId = DataUtil.safeToLong(objects[i++]);
        this.campaignScriptId = DataUtil.safeToLong(objects[i++]);
        this.groupUser = DataUtil.safeToString(objects[i++]);
        this.displayPhone = DataUtil.safeToString(objects[i++]);
        this.startDate = DataUtil.safeToDate(objects[i++]);
        this.endDate = DataUtil.safeToDate(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.createDatetime = DataUtil.safeToDate(objects[i++]);
        this.createUser = DataUtil.safeToLong(objects[i++]);
        this.updateDatetime = DataUtil.safeToDate(objects[i++]);
        this.updateUser = DataUtil.safeToLong(objects[i++]);
        this.campaignTemplateName = DataUtil.safeToString(objects[i++]);
        this.campaignScriptName = DataUtil.safeToString(objects[i++]);
        String callStatusString = DataUtil.safeToString(objects[i++]);
        this.listCallStatusArray=(callStatusString!=null && !callStatusString.isEmpty())? callStatusString.split(","):null;
        this.nullCallFail = DataUtil.safeToInt(objects[i++]);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCampaignScriptId() {
        return campaignScriptId;
    }

    public void setCampaignScriptId(Long campaignScriptId) {
        this.campaignScriptId = campaignScriptId;
    }

    public Long getCampaignTemplateId() {
        return campaignTemplateId;
    }

    public void setCampaignTemplateId(Long campaignTemplateId) {
        this.campaignTemplateId = campaignTemplateId;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CampaignDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", getCampaignTemplateId=" + getCampaignTemplateId() +
            ", getCampaignScriptId=" + getCampaignScriptId() +
            ", groupUser='" + getGroupUser() + "'" +
            ", displayPhone='" + getDisplayPhone() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            ", updateDatetime='" + getUpdateDatetime() + "'" +
            ", updateUser=" + getUpdateUser() +
            "}";
    }
}
