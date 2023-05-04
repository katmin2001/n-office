package com.fis.crm.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.fis.crm.domain.OptionSetValue} entity.
 */
public class OptionSetValueDTO implements Serializable {

    private Long id;

    private Long optionSetId;

    private String code;

    private Integer ord;

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    private String name;

    private String groupName;

    private String englishName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private Instant fromDate;
    private String fromDateView;

    private Instant endDate;
    private String endDateView;

    private String status;

    private Long createUser;

    private Instant createDate;
    private String createDateView;

    private Long updateUser;

    private Instant updateDate;
    private String updateDateView;

    private String c1;

    private String c2;

    private int page;

    private String objectSetCode;

    private String createUserName;
    private String updateUserName;

    public String getObjectSetCode() {
        return objectSetCode;
    }

    public void setObjectSetCode(String objectSetCode) {
        this.objectSetCode = objectSetCode;
    }

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
    public OptionSetValueDTO() {


    }

    public OptionSetValueDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.code = DataUtil.safeToString(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
        this.ord = DataUtil.safeToInt(objects[i++]);
        this.groupName = DataUtil.safeToString(objects[i++]);
        this.fromDateView = DataUtil.safeToString(objects[i++]);
        this.endDateView = DataUtil.safeToString(objects[i++]);
        this.status = DataUtil.safeToString(objects[i++]);
        this.createDateView = DataUtil.safeToString(objects[i++]);
        this.createUserName = DataUtil.safeToString(objects[i++]);
        this.updateDateView = DataUtil.safeToString(objects[i++]);
        this.updateUserName = DataUtil.safeToString(objects[i++]);
        this.c1 = DataUtil.safeToString(objects[i++]);
        this.c2 = DataUtil.safeToString(objects[i++]);
    }

    public OptionSetValueDTO(Long optionSetId, String code, Integer ord, String name, String status, Long createUser, Instant createDate, Long updateUser, Instant updateDate, int page, int size, String keySearch, String errorCodeConfig,
                             Instant fromDate, Instant endDate, String c1, String c2, String groupName, String englishName) {
        this.optionSetId = optionSetId;
        this.code = code;
        this.name = name;
        this.status = status;
        this.createUser = createUser;
        this.createDate = createDate;
        this.updateUser = updateUser;
        this.updateDate = updateDate;
        this.page = page;
        this.size = size;
        this.keySearch = keySearch;
        this.errorCodeConfig = errorCodeConfig;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.c1 = c1;
        this.c2 = c2;
        this.ord = ord;
        this.groupName = groupName;
        this.englishName = englishName;
    }


    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC1() {
        return c1;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC2() {
        return c2;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionSetDTO)) {
            return false;
        }

        return optionSetId != null && optionSetId.equals(((OptionSetDTO) o).getOptionSetId());
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
            ", code=" + code +
            ", name=" + name +
            ", groupName =" + groupName +
            ", ord =" + ord +
            ", fromDate =" + fromDate +
            ", endDate='" + endDate + '\'' +
            ", status='" + status + '\'' +
            ", createUser=" + createUser +
            ", createDate=" + createDate +
            ", updateUser=" + updateUser +
            ", updateDate=" + updateDate +
            ", c1=" + c1 +
            ", c2=" + c2 +
            ", page=" + page +
            ", size=" + size +
            ", keySearch='" + keySearch + '\'' +
            '}';
    }

    public String getFromDateView() {
        return fromDateView;
    }

    public void setFromDateView(String fromDateView) {
        this.fromDateView = fromDateView;
    }

    public String getEndDateView() {
        return endDateView;
    }

    public void setEndDateView(String endDateView) {
        this.endDateView = endDateView;
    }

    public String getCreateDateView() {
        return createDateView;
    }

    public void setCreateDateView(String createDateView) {
        this.createDateView = createDateView;
    }

    public String getUpdateDateView() {
        return updateDateView;
    }

    public void setUpdateDateView(String updateDateView) {
        this.updateDateView = updateDateView;
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
