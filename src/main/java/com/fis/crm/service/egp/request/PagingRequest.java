package com.fis.crm.service.egp.request;

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
    "queryParams",
    "orderBy",
    "pageNumber",
    "pageSize"
})
public class PagingRequest {

    @JsonProperty("queryParams")
    private QueryParams queryParams;
    @JsonProperty("orderBy")
    private String orderBy;
    @JsonProperty("pageNumber")
    private String pageNumber;
    @JsonProperty("pageSize")
    private Integer pageSize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("queryParams")
    public QueryParams getQueryParams() {
        return queryParams;
    }

    @JsonProperty("queryParams")
    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    @JsonProperty("orderBy")
    public String getOrderBy() {
        return orderBy;
    }

    @JsonProperty("orderBy")
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @JsonProperty("pageNumber")
    public String getPageNumber() {
        return pageNumber;
    }

    @JsonProperty("pageNumber")
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    @JsonProperty("pageSize")
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
