package com.fis.crm.service.egp.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "categoryTypeCodeLst"
})
public class CategoryRequest {

    @JsonProperty("categoryTypeCodeLst")
    private List<String> categoryTypeCodeLst = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("categoryTypeCodeLst")
    public List<String> getCategoryTypeCodeLst() {
        return categoryTypeCodeLst;
    }

    @JsonProperty("categoryTypeCodeLst")
    public void setCategoryTypeCodeLst(List<String> categoryTypeCodeLst) {
        this.categoryTypeCodeLst = categoryTypeCodeLst;
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
