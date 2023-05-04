package com.fis.crm.service.dto;

import java.io.Serializable;
import java.util.List;

public class GeneralCampaignDTO implements Serializable {

    private Long campaignResourceId;
    private List<GroupUserDTO> lstGroupDto;
    private List<UserDTO> lstUserDto;
    private Long campaignId;
    private Long groupId;
    private Long userId;
    private Long noCampaignGroupYetAssign;
    private UserDTO userDTO;
    private GroupUserDTO groupUserDTO;
    private String action;//1-Trả dữ liệu chưa gọi về nguồn chiến dịch / 2-TRả dữ liệu không hợp lệ về nguồn chiến dịch

    public GeneralCampaignDTO(Long campaignResourceId, List<GroupUserDTO> lstGroupDto, List<UserDTO> lstUserDto, Long campaignId, Long groupId, Long userId, Long noCampaignGroupYetAssign, UserDTO userDTO, GroupUserDTO groupUserDTO, String action) {
        this.campaignResourceId = campaignResourceId;
        this.lstGroupDto = lstGroupDto;
        this.lstUserDto = lstUserDto;
        this.campaignId = campaignId;
        this.groupId = groupId;
        this.userId = userId;
        this.noCampaignGroupYetAssign = noCampaignGroupYetAssign;
        this.userDTO = userDTO;
        this.groupUserDTO = groupUserDTO;
        this.action = action;
    }

    public GeneralCampaignDTO() {
    }

    public Long getCampaignResourceId() {
        return campaignResourceId;
    }

    public void setCampaignResourceId(Long campaignResourceId) {
        this.campaignResourceId = campaignResourceId;
    }

    public List<GroupUserDTO> getLstGroupDto() {
        return lstGroupDto;
    }

    public void setLstGroupDto(List<GroupUserDTO> lstGroupDto) {
        this.lstGroupDto = lstGroupDto;
    }

    public List<UserDTO> getLstUserDto() {
        return lstUserDto;
    }

    public void setLstUserDto(List<UserDTO> lstUserDto) {
        this.lstUserDto = lstUserDto;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNoCampaignGroupYetAssign() {
        return noCampaignGroupYetAssign;
    }

    public void setNoCampaignGroupYetAssign(Long noCampaignGroupYetAssign) {
        this.noCampaignGroupYetAssign = noCampaignGroupYetAssign;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public GroupUserDTO getGroupUserDTO() {
        return groupUserDTO;
    }

    public void setGroupUserDTO(GroupUserDTO groupUserDTO) {
        this.groupUserDTO = groupUserDTO;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
