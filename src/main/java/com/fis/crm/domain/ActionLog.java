package com.fis.crm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A ActionLog.
 */
@Entity
@Table(name = "action_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTION_LOG_GEN")
    @SequenceGenerator(name = "ACTION_LOG_GEN", sequenceName = "ACTION_LOG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "object_name")
    private String objectName;

    @Column(name = "note")
    private String note;

    @Column(name = "issue_datetime")
    private Date issueDateTime;

    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "MENU_ITEM_ID")
    private Long menuItemId;

    @Column(name = "CREATE_DATETIME")
    private Date createDatetime;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public ActionLog userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public ActionLog actionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public ActionLog objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public ActionLog objectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getNote() {
        return note;
    }

    public ActionLog note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getIssueDateTime() {
        return issueDateTime;
    }

    public ActionLog issueDateTime(Date issueDateTime) {
        this.issueDateTime = issueDateTime;
        return this;
    }

    public void setIssueDateTime(Date issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionLog)) {
            return false;
        }
        return id != null && id.equals(((ActionLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionLog{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", actionType=" + getActionType() +
            ", objectId=" + getObjectId() +
            ", objectName='" + getObjectName() + "'" +
            ", note=" + getNote() +
            ", issueDateTime='" + getIssueDateTime() + "'" +
            "}";
    }
}
