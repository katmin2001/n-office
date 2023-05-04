package com.fis.crm.service.egp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EgpRequest<T> {

    @JsonProperty("body")
    private T data;

    public EgpRequest(T data) {
        this.data = data;
    }

    public EgpRequest() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EgpRequest{" +
            "data=" + data +
            '}';
    }
}
