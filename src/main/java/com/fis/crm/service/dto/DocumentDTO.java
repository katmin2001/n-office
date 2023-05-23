package com.fis.crm.service.dto;

import com.fis.crm.commons.DataUtil;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fis.crm.domain.Document} entity.
 */
public class DocumentDTO implements Serializable {

    private Long id;

    private String name;

    private Long parentId;

    private String status;

    private Instant createDatetime;

    private Long createUser;

    private String pathName;
    private String pathId;
    private Long pathLevel;

    public DocumentDTO() {
    }

    public DocumentDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
        this.parentId = DataUtil.safeToLong(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.createDatetime = DataUtil.safeToInstant(objects[i++]);
        this.createUser = DataUtil.safeToLong(objects[i++]);
        this.pathName = DataUtil.safeToString(objects[i++]);
        this.pathId = DataUtil.safeToString(objects[i++]);
        this.pathLevel = DataUtil.safeToLong(objects[i++]);
    }


    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public Long getPathLevel() {
        return pathLevel;
    }

    public void setPathLevel(Long pathLevel) {
        this.pathLevel = pathLevel;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDatetime='" + getCreateDatetime() + "'" +
            ", createUser=" + getCreateUser() +
            "}";
    }
}
