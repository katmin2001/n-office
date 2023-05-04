package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userName",
    "fullName",
    "status",
    "caExpDate"
})
public class CaInfo implements Serializable {

    @JsonProperty("userName")
    private String userName;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("status")
    private Integer status;
    private String statusName;
    @JsonProperty("caExpDate")
    private Date caExpDate;

    public CaInfo(String userName, String fullName, Integer status, Date caExpDate) {
        this.userName = userName;
        this.fullName = fullName;
        this.status = status;
        this.caExpDate = caExpDate;
    }

    public CaInfo() {
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCaExpDate() {
        return caExpDate;
    }

    public void setCaExpDate(Date caExpDate) {
        this.caExpDate = caExpDate;
    }

}
