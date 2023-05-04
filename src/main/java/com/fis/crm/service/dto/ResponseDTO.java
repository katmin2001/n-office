package com.fis.crm.service.dto;

public class ResponseDTO {

    private Long status;

    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(Long status, String message) {
        this.status = status;
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
