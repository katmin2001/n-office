package com.fis.crm.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseNoBody<T> {

    @JsonProperty("status")
    private ResponseStatus status;

    @Override
    public String toString() {
        return "{" + "status=" + status + '}';
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }


}
