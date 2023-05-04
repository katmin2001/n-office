package com.fis.crm.web.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ResponseStatus class handle response status
 * Format of response status returned to client
 */

public class ResponseStatus implements Serializable {
    /**
     * ResponseStatus constructor
     * @return ResponseStatus
     * @author xuatdd
     * @version 1.0
     * @since 2020/03/01
     */
    public ResponseStatus() {
    }

    /**
     * ResponseStatus constructor
     * @param responseStatus response status
     * @return ResponseStatus
     * @author xuatdd
     * @version 1.0
     * @since 2020/03/01
     */
    public ResponseStatus(ResponseStatusCode responseStatus) {
        this.code = String.valueOf(responseStatus.getHttpCode().value());
        String messageDb =responseStatus.getMessage();
        if (messageDb != null && !messageDb.isEmpty()) {
            this.message = messageDb;
        } else {
            this.message = responseStatus.getHttpCode().getReasonPhrase();
        }
    }

    /**
     * ResponseStatus constructor
     * @param code code
     * @param setMessageImplicitly message implicitly flag
     * @return ResponseStatus
     * @author xuatdd
     * @version 1.0
     * @since 2020/03/01
     */
    public ResponseStatus(String code, boolean setMessageImplicitly) {
        setCode(code, setMessageImplicitly);
    }

    @JsonProperty("code")
    private String code;

    /**
     * Set the code. this will implicitly set the message based on the locale
     *
     * @param code
     */
    public void setCode(String code) {
        setCode(code, true);
    }

    /**
     * Setter the code
     * @param code
     */
    public void setCode(String code, boolean setMessageImplicitly) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "{" + "\"code\":\"" + code + "\"" +
            ", \"message\":\"" + message + "\"" +
            '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
