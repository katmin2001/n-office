package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errorCode",
    "messageParam",
    "pageContractor"
})
public class PmContractorStatusData {

    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("messageParam")
    private Object messageParam;
    @JsonProperty("pageContractor")
    private PageContractor<PmContractStatusContent> pageContractor;

    public PmContractorStatusData(String errorCode, Object messageParam, PageContractor<PmContractStatusContent> pageContractor) {
        this.errorCode = errorCode;
        this.messageParam = messageParam;
        this.pageContractor = pageContractor;
    }

    public PmContractorStatusData() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getMessageParam() {
        return messageParam;
    }

    public void setMessageParam(Object messageParam) {
        this.messageParam = messageParam;
    }

    public PageContractor<PmContractStatusContent> getPageContractor() {
        return pageContractor;
    }

    public void setPageContractor(PageContractor<PmContractStatusContent> pageContractor) {
        this.pageContractor = pageContractor;
    }

    @Override
    public String toString() {
        return "PmContractorStatusData{" +
            "errorCode='" + errorCode + '\'' +
            ", messageParam=" + messageParam +
            ", pageContractor=" + pageContractor +
            '}';
    }
}


