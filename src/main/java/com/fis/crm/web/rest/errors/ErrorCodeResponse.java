package com.fis.crm.web.rest.errors;


import com.fis.crm.commons.Translator;

public enum ErrorCodeResponse {
    UNKNOWN("100", "error.haveSomeError"),
    CAMPAIGN_ASSIGN_OVERLOAD("101","campaign.resource.assignment.overload"),
    MAX_FILE_SIZE("102", "");


    private final String message;

    private final String errorCode;

    ErrorCodeResponse(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageI18N() {
        return Translator.toLocale(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
