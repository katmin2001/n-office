package com.fis.crm.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * A DTO for the {@link com.fis.crm.domain.ConfigSchedule} entity.
 */
public class ActionLogDTO implements Serializable {

    private Long id;

    private Long userId;
    private String userName;

    private String actionType;
    private String actionTypeName;

    private Long objectId;

    private String objectName;

    private String note;

    private Instant issueDateTime;

    private Date createDatetime;
    private String createDatetimeName;

    private Long menuId;
    private String menuName;

    private Long menuItemId;
    private String menuItemName;

    private Date startDate;
    private Date endDate;

    public String getCreateDatetimeName() {
        return createDatetimeName;
    }

    public void setCreateDatetimeName(String createDatetimeName) {
        this.createDatetimeName = createDatetimeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getIssueDateTime() {
        return issueDateTime;
    }

    public void setIssueDateTime(Instant issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    public ActionLogDTO(){

    }

    public ActionLogDTO(Long id, Long userId, String actionType, Long objectId, String objectName, String note, Instant issueDateTime, Date createDatetime, Long menuId, Long menuItemId) {
        this.id = id;
        this.userId = userId;
        this.actionType = actionType;
        this.objectId = objectId;
        this.objectName = objectName;
        this.note = note;
        this.issueDateTime = issueDateTime;
        this.createDatetime = createDatetime;
        this.menuId = menuId;
        this.menuItemId = menuItemId;
    }

    public ActionLogDTO(Long userId, String actionType, Long objectId, String note, Date createDatetime, Long menuId, Long menuItemId, String objectName) {
        this.userId = userId;
        this.actionType = actionType;
        this.objectId = objectId;
        this.note = note;
        this.createDatetime = createDatetime;
        this.menuId = menuId;
        this.menuItemId = menuItemId;
        this.objectName = objectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionLogDTO)) {
            return false;
        }

        return id != null && id.equals(((ActionLogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 32;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionLogDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", actionType=" + getActionType() +
            ", objectId=" + getObjectId() +
            ", objectName='" + getObjectName() + "'" +
            ", note='" + getNote() + "'" +
            ", issueDateTime='" + getIssueDateTime() + "'" +
            "}";
    }
}
