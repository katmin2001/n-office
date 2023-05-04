package com.fis.crm.service.egp.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "code",
    "name",
    "nameEn",
    "orderIndex",
    "createdDate",
    "createdBy",
    "lastModifiedDate",
    "lastModifiedBy",
    "categoryTypeCode",
    "groupCode",
    "effectFrom",
    "effectTo"
})
public class Category {

    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nameEn")
    private Object nameEn;
    @JsonProperty("orderIndex")
    private Integer orderIndex;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("lastModifiedDate")
    private String lastModifiedDate;
    @JsonProperty("lastModifiedBy")
    private String lastModifiedBy;
    @JsonProperty("categoryTypeCode")
    private String categoryTypeCode;
    @JsonProperty("groupCode")
    private Object groupCode;
    @JsonProperty("effectFrom")
    private String effectFrom;
    @JsonProperty("effectTo")
    private String effectTo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("nameEn")
    public Object getNameEn() {
        return nameEn;
    }

    @JsonProperty("nameEn")
    public void setNameEn(Object nameEn) {
        this.nameEn = nameEn;
    }

    @JsonProperty("orderIndex")
    public Integer getOrderIndex() {
        return orderIndex;
    }

    @JsonProperty("orderIndex")
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("lastModifiedDate")
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    @JsonProperty("lastModifiedDate")
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @JsonProperty("lastModifiedBy")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @JsonProperty("lastModifiedBy")
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonProperty("categoryTypeCode")
    public String getCategoryTypeCode() {
        return categoryTypeCode;
    }

    @JsonProperty("categoryTypeCode")
    public void setCategoryTypeCode(String categoryTypeCode) {
        this.categoryTypeCode = categoryTypeCode;
    }

    @JsonProperty("groupCode")
    public Object getGroupCode() {
        return groupCode;
    }

    @JsonProperty("groupCode")
    public void setGroupCode(Object groupCode) {
        this.groupCode = groupCode;
    }

    @JsonProperty("effectFrom")
    public String getEffectFrom() {
        return effectFrom;
    }

    @JsonProperty("effectFrom")
    public void setEffectFrom(String effectFrom) {
        this.effectFrom = effectFrom;
    }

    @JsonProperty("effectTo")
    public String getEffectTo() {
        return effectTo;
    }

    @JsonProperty("effectTo")
    public void setEffectTo(String effectTo) {
        this.effectTo = effectTo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id='" + id + '\'' +
            ", code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", nameEn=" + nameEn +
            ", orderIndex=" + orderIndex +
            ", createdDate='" + createdDate + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate='" + lastModifiedDate + '\'' +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", categoryTypeCode='" + categoryTypeCode + '\'' +
            ", groupCode=" + groupCode +
            ", effectFrom='" + effectFrom + '\'' +
            ", effectTo='" + effectTo + '\'' +
            '}';
    }
}
