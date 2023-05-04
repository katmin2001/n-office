package com.fis.crm.commons;

public class CustomException extends Exception {
    private Integer errorCode;

    public CustomException() {
        super();
    }

    public CustomException(int userNotFound1, String errorMessage) {
        super(errorMessage);
        this.errorCode = userNotFound1;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
