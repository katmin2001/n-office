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
    "areaCodeLst"
})
public class AreaRequest {

    @JsonProperty("areaCodeLst")
    private List<String> areaCodeLst = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public AreaRequest() {
    }

    public AreaRequest(List<String> areaCodeLst) {
        this.areaCodeLst = areaCodeLst;
    }

    @JsonProperty("areaCodeLst")
    public List<String> getAreaCodeLst() {
        return areaCodeLst;
    }

    @JsonProperty("areaCodeLst")
    public void setAreaCodeLst(List<String> areaCodeLst) {
        this.areaCodeLst = areaCodeLst;
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
