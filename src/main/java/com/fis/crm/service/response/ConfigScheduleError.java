package com.fis.crm.service.response;

public class ConfigScheduleError {
    private String msgCode;
    private String message;

    public ConfigScheduleError(String msgCode, String message) {
        this.msgCode = msgCode;
        this.message = message;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
