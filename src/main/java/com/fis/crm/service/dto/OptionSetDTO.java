package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis.crm.commons.DataUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fis.crm.domain.OptionSet} entity.
 */
public class OptionSetDTO implements Serializable {

    private Long optionSetId;

    private String code;

    private String name;

    private String englishName;

    private Instant fromDate;

    private Instant endDate;

    private String status;

    private Long createUser;

    private String createUserName;

    private Instant createDate;

    private Long updateUser;

    private Instant updateDate;

    private String updateUserName;

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
    @JsonIgnore
    private XSSFWorkbook xssfWorkbook;

    public XSSFWorkbook getXssfWorkbook() {
        return xssfWorkbook;
    }

    public void setXssfWorkbook(XSSFWorkbook xssfWorkbook) {
        this.xssfWorkbook = xssfWorkbook;
    }

    public OptionSetDTO() {
    }

    public OptionSetDTO(Object[] objects){
        int i = 0;
        this.optionSetId = DataUtil.safeToLong(objects[i++]);
        this.code = DataUtil.safeToString(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
        this.fromDate = DataUtil.safeToInstant(objects[i++]);
        this.endDate = DataUtil.safeToInstant(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.createUserName = DataUtil.safeToString(objects[i++]);
        this.createDate = DataUtil.safeToInstant(objects[i++]);
        this.updateDate = DataUtil.safeToInstant(objects[i++]);
        this.updateUserName = DataUtil.safeToString(objects[i++]);
        this.englishName = DataUtil.safeToString(objects[i++]);
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
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

    public Long getOptionSetId() {
        return optionSetId;
    }

    public void setOptionSetId(Long optionSetId) {
        this.optionSetId = optionSetId;
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

    public Instant getFromDate() {
        return fromDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionSetDTO)) {
            return false;
        }

        return optionSetId != null && optionSetId.equals(((OptionSetDTO) o).optionSetId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "OptionSetDTO{" +
            "optionSetId=" + optionSetId +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", fromDate=" + fromDate +
            ", endDate=" + endDate +
            ", status='" + status + '\'' +
            ", createUser=" + createUser +
            ", createUserName='" + createUserName + '\'' +
            ", createDate=" + createDate +
            ", updateUser=" + updateUser +
            ", updateDate=" + updateDate +
            ", updateUserName='" + updateUserName + '\'' +
            ", page=" + page +
            ", size=" + size +
            ", keySearch='" + keySearch + '\'' +
            ", errorCodeConfig='" + errorCodeConfig + '\'' +
            ", importType='" + importType + '\'' +
            ", xssfWorkbook=" + xssfWorkbook +
            '}';
    }
}
