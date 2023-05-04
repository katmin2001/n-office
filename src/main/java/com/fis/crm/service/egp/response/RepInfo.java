package com.fis.crm.service.egp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "repName",
    "repIdType",
    "repIdNo",
    "repDepartment",
    "repDate",
    "repExpDate",
    "repNation",
    "repPhone",
    "repEmail",
    "repPosition"
})
public class RepInfo {

    @JsonProperty("repName")
    private String repName;
    @JsonProperty("repIdType")
    private String repIdType;
    @JsonProperty("repIdNo")
    private String repIdNo;
    @JsonProperty("repDepartment")
    private String repDepartment;
    @JsonProperty("repDate")
    private Date repDate;
    @JsonProperty("repExpDate")
    private Date repExpDate;
    @JsonProperty("repNation")
    private String repNation;
    @JsonProperty("repPhone")
    private String repPhone;
    @JsonProperty("repEmail")
    private String repEmail;
    @JsonProperty("repPosition")
    private String repPosition;

    public RepInfo(String repName, String repIdType, String repIdNo, String repDepartment, Date repDate, Date repExpDate, String repNation, String repPhone, String repEmail, String repPosition) {
        this.repName = repName;
        this.repIdType = repIdType;
        this.repIdNo = repIdNo;
        this.repDepartment = repDepartment;
        this.repDate = repDate;
        this.repExpDate = repExpDate;
        this.repNation = repNation;
        this.repPhone = repPhone;
        this.repEmail = repEmail;
        this.repPosition = repPosition;
    }

    public RepInfo() {
    }

    @Override
    public String toString() {
        return "RepInfo{" +
            "repName='" + repName + '\'' +
            ", repIdType='" + repIdType + '\'' +
            ", repIdNo='" + repIdNo + '\'' +
            ", repDepartment='" + repDepartment + '\'' +
            ", repDate=" + repDate +
            ", repExpDate=" + repExpDate +
            ", repNation='" + repNation + '\'' +
            ", repPhone='" + repPhone + '\'' +
            ", repEmail='" + repEmail + '\'' +
            ", repPosition='" + repPosition + '\'' +
            '}';
    }
}
