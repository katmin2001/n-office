package com.fis.crm.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GeneralResponse Format of response returned to client
 * @param <T>
 */

public class GeneralResponse<T> {

    @JsonProperty("status")
    private ResponseStatus status;

    @JsonProperty("data")
    private T data;

    @Override
    public String toString() {
        return "{" + "status=" + status +
            ", data=" + data +
            '}';
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
