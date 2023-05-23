package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.*;

public class DtoMapper {

    public Crm_UserDTO userDtoMapper(CrmUser user){
        Crm_UserDTO userDTO = new Crm_UserDTO();
            userDTO.setUserId(user.getUserid());
            userDTO.setUsername(user.getUsername());
            userDTO.setFullName(user.getFullname());
            userDTO.setCreateDate(user.getCreatedate());
            userDTO.setPhone(user.getPhone());
            userDTO.setBirthday(user.getBirthday());
            userDTO.setAddress(user.getAddress());
            userDTO.setStatus(user.getStatus());
        return userDTO;
    }
    public CrmRoleDTO roleDtoMapper(CrmRole role){
        CrmRoleDTO roleDTO = new CrmRoleDTO();
            roleDTO.setRoleId(role.getRoleid());
            roleDTO.setRoleName(role.getRolename());
        return roleDTO;
    }
    public CrmFunctionDTO functionDTOMapper(CrmFunction function){
        CrmFunctionDTO functionDTO = new CrmFunctionDTO();
            functionDTO.setFuncId(function.getFuncid());
            functionDTO.setFuncName(function.getFuncname());
        return functionDTO;
    }
    public CrmUserRoleDTO userRoleDTOMapper(CrmUserRole userRole){
        CrmUserRoleDTO userRoleDTO = new CrmUserRoleDTO();
            userRoleDTO.setId(userRole.getUrid());
            userRoleDTO.setUserId(userRole.getUser().getUserid());
            userRoleDTO.setRoleId(userRole.getRole().getRoleid());
        return userRoleDTO;
    }

    public CrmProjectDTO projectDTOMapper(CrmProject project) {
        CrmProjectDTO dto = new CrmProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setCode(project.getCode());
        dto.setCustomer(project.getCustomer());
        dto.setManager(userDtoMapper(project.getManager()));
        dto.setPrivacy(project.getPrivacy());
        dto.setStatus(project.getStatus());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setFinishDate(project.getFinishDate());
        return dto;
    }

    public CrmProjectDTO projectMemberPDTOMapper(CrmProjectMember projectMember) {
        return null;
    }

    public Crm_UserDTO projectMemberMDTOMapper(CrmProjectMember projectMember) {
        Crm_UserDTO dto = new Crm_UserDTO();
//        dto.setUserId(projectMember.getMember().getUserid());
//        dto.setUsername(projectMember.getMember().getUsername());
//        dto.setFullName(projectMember.getMember().getFullname());
//        dto.setCreateDate(projectMember.getMember().getCreatedate());
//        dto.setPhone(projectMember.getMember().getPhone());
//        dto.setBirthday(projectMember.getMember().getBirthday());
//        dto.setAddress(projectMember.getMember().getAddress());
//        dto.setStatus(projectMember.getMember().getStatus());
        return dto;
    }

    public static CrmCustomerDTO customerDTOMapper(CrmCustomer customer) {
        CrmCustomerDTO dto = new CrmCustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        return dto;
    }
}
