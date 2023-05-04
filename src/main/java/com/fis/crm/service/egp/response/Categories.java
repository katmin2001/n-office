package com.fis.crm.service.egp.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Categories {

    private Map<String, Category> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Category> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Category value) {
        this.additionalProperties.put(name, value);
    }

}
