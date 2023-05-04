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
    "code",
    "parent",
    "parentCode",
    "areaType",
    "name",
    "orderIndex",
    "status",
    "nameTranslate"
})
public class Parent {

    @JsonProperty("code")
    private String code;
    @JsonProperty("parent")
    private Parent parent;
    @JsonProperty("parentCode")
    private String parentCode;
    @JsonProperty("areaType")
    private String areaType;
    @JsonProperty("name")
    private String name;
    @JsonProperty("orderIndex")
    private Integer orderIndex;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("nameTranslate")
    private String nameTranslate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("parent")
    public Parent getParent() {
        return parent;
    }

    @JsonProperty("parent")
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @JsonProperty("parentCode")
    public String getParentCode() {
        return parentCode;
    }

    @JsonProperty("parentCode")
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @JsonProperty("areaType")
    public String getAreaType() {
        return areaType;
    }

    @JsonProperty("areaType")
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("orderIndex")
    public Integer getOrderIndex() {
        return orderIndex;
    }

    @JsonProperty("orderIndex")
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("nameTranslate")
    public String getNameTranslate() {
        return nameTranslate;
    }

    @JsonProperty("nameTranslate")
    public void setNameTranslate(String nameTranslate) {
        this.nameTranslate = nameTranslate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
