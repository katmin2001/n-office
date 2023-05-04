package com.fis.crm.service.dto;

public class MessageResponse<T> {
    private String message;
    private T data;

    public MessageResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public MessageResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
