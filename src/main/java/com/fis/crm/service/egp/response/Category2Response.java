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
    "groupCode",
    "categories"
})
public class Category2Response {

    @JsonProperty("groupCode")
    private Object groupCode;
    @JsonProperty("categories")
    private Categories categories;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("groupCode")
    public Object getGroupCode() {
        return groupCode;
    }

    @JsonProperty("groupCode")
    public void setGroupCode(Object groupCode) {
        this.groupCode = groupCode;
    }

    @JsonProperty("categories")
    public Categories getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(Categories categories) {
        this.categories = categories;
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
