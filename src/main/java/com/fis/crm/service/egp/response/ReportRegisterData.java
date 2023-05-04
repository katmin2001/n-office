package com.fis.crm.service.egp.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "crmId",
    "egpId",
    "reserve",
    "approve"
})
public class ReportRegisterData {

    @JsonProperty("crmId")
    private String crmId;
    @JsonProperty("egpId")
    private String egpId;
    @JsonProperty("reserve")
    private PageContractor<ReserveContent> reserve;
    @JsonProperty("approve")
    private PageContractor<ApproveContent> approve;

    public ReportRegisterData(String crmId, String egpId, PageContractor<ReserveContent> reserve, PageContractor<ApproveContent> approve) {
        this.crmId = crmId;
        this.egpId = egpId;
        this.reserve = reserve;
        this.approve = approve;
    }

    public ReportRegisterData() {
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getEgpId() {
        return egpId;
    }

    public void setEgpId(String egpId) {
        this.egpId = egpId;
    }

    public PageContractor<ReserveContent> getReserve() {
        return reserve;
    }

    public void setReserve(PageContractor<ReserveContent> reserve) {
        this.reserve = reserve;
    }

    public PageContractor<ApproveContent> getApprove() {
        return approve;
    }

    public void setApprove(PageContractor<ApproveContent> approve) {
        this.approve = approve;
    }

    @Override
    public String toString() {
        return "ReportRegisterData{" +
            "crmId='" + crmId + '\'' +
            ", egpId='" + egpId + '\'' +
            ", reserve=" + reserve +
            ", approve=" + approve +
            '}';
    }
}
