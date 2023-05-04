package com.fis.crm.service.dto;

public class CampaignResourceGeneralDTO {
    private String groupName;
    private String userName;
    private Long received;
    private Long called;
    private Long yetCall;
    private Long assigned;
    private Long yetAssign;
    private Long assignUserId;
    private Long groupId;
    private Long notDoneCall;

    public CampaignResourceGeneralDTO(String groupName, String userName, Long received, Long called, Long yetCall, Long assigned, Long yetAssign, Long assignUserId, Long groupId) {
        this.groupName = groupName;
        this.userName = userName;
        this.received = received;
        this.called = called;
        this.yetCall = yetCall;
        this.assigned = assigned;
        this.yetAssign = yetAssign;
        this.assignUserId = assignUserId;
        this.groupId = groupId;
    }

    public Long getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Long assignUserId) {
        this.assignUserId = assignUserId;
    }

    public CampaignResourceGeneralDTO() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getReceived() {
        return received;
    }

    public void setReceived(Long received) {
        this.received = received;
    }

    public Long getCalled() {
        return called;
    }

    public void setCalled(Long called) {
        this.called = called;
    }

    public Long getYetCall() {
        return yetCall;
    }

    public void setYetCall(Long yetCall) {
        this.yetCall = yetCall;
    }

    public Long getAssigned() {
        return assigned;
    }

    public void setAssigned(Long assigned) {
        this.assigned = assigned;
    }

    public Long getYetAssign() {
        return yetAssign;
    }

    public void setYetAssign(Long yetAssign) {
        this.yetAssign = yetAssign;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getNotDoneCall() {
        return notDoneCall;
    }

    public void setNotDoneCall(Long notDoneCall) {
        this.notDoneCall = notDoneCall;
    }
}
