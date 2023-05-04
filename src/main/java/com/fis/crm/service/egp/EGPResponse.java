package com.fis.crm.service.egp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "responseCode",
    "responseMessage",
    "responseEntityMessages",
    "body"
})
public class EGPResponse<T> implements Serializable {

    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseEntityMessages")
    private Object responseEntityMessages;
    @JsonProperty("body")
    private T data;

    public EGPResponse(String responseCode, String responseMessage, Object responseEntityMessages, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseEntityMessages = responseEntityMessages;
        this.data = data;
    }

    public EGPResponse() {
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getResponseEntityMessages() {
        return responseEntityMessages;
    }

    public void setResponseEntityMessages(Object responseEntityMessages) {
        this.responseEntityMessages = responseEntityMessages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EGPResponse{" +
            "responseCode='" + responseCode + '\'' +
            ", responseMessage='" + responseMessage + '\'' +
            ", responseEntityMessages=" + responseEntityMessages +
            ", data=" + data +
            '}';
    }
}
