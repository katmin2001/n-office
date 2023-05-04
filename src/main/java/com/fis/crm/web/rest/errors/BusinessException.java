package com.fis.crm.web.rest.errors;


import com.fis.crm.commons.Translator;

public class BusinessException extends RuntimeException{

    private String errorCode;
    private String message;
    /**
     * Construct a new instance of {@code RestClientException} with the given message.
     *
     * @param msg the message
     */
    public BusinessException(String msg) {
        super(msg);
        this.message = msg;
    }

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     *
     * @param msg the message
     * @param ex  the exception
     */
    public BusinessException(String msg, Throwable ex) {
        super(msg, ex);
        this.message = msg;
    }

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     * @param errorCode the message
     * @param msg the message
     * @param ex  the exception
     */
    public BusinessException(String errorCode, String msg, Throwable ex) {
        super(msg, ex);
        this.errorCode = errorCode;
        this.message = msg;
    }

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     * @param errCode enum error code
     */
    public BusinessException(ErrorCodeResponse errCode, String... params) {
        super(errCode.getErrorCode());
        this.errorCode = errCode.getErrorCode();
        this.message = Translator.toLocale(errCode.getMessage(), params);
    }

    public BusinessException(ErrorCodeResponse errCode) {
        super(errCode.getErrorCode());
        this.errorCode = errCode.getErrorCode();
        this.message = Translator.toLocale(errCode.getMessage());
    }

    public BusinessException(String errCode, String message) {
        super(errCode);
        this.errorCode = errCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
