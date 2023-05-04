package com.fis.crm.service.dto;

public class CampaignPerformDTO {

    private Long total;

    private Long called;

    private Long nonCalled;

    private String code;

    private String name;

    private String call_time_from;

    private String call_time_to;

    private Long campaign_id;

    private Long campaign_resource_id;

    private Long assign_user_id;

    private String type;

    private String status;

    private String saleStatus;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCalled() {
        return called;
    }

    public void setCalled(Long called) {
        this.called = called;
    }

    public Long getNonCalled() {
        return nonCalled;
    }

    public void setNonCalled(Long nonCalled) {
        this.nonCalled = nonCalled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCall_time_from() {
        return call_time_from;
    }

    public void setCall_time_from(String call_time_from) {
        this.call_time_from = call_time_from;
    }

    public String getCall_time_to() {
        return call_time_to;
    }

    public void setCall_time_to(String call_time_to) {
        this.call_time_to = call_time_to;
    }

    public Long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(Long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public Long getCampaign_resource_id() {
        return campaign_resource_id;
    }

    public void setCampaign_resource_id(Long campaign_resource_id) {
        this.campaign_resource_id = campaign_resource_id;
    }

    public Long getAssign_user_id() {
        return assign_user_id;
    }

    public void setAssign_user_id(Long assign_user_id) {
        this.assign_user_id = assign_user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }
}
