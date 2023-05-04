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
    "categoryTypeCode"
})
public class QueryParams {

    @JsonProperty("categoryTypeCode")
    private CategoryTypeCode categoryTypeCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("categoryTypeCode")
    public CategoryTypeCode getCategoryTypeCode() {
        return categoryTypeCode;
    }

    @JsonProperty("categoryTypeCode")
    public void setCategoryTypeCode(CategoryTypeCode categoryTypeCode) {
        this.categoryTypeCode = categoryTypeCode;
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
