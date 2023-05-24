package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.*;
import com.fis.crm.crm_entity.DTO.*;

import java.util.HashSet;
import java.util.Set;

public class DtoMapper {

    public CrmUserDTO userDtoMapper(CrmUser user){
        CrmUserDTO userDTO = new CrmUserDTO();
        userDTO.setUserId(user.getUserid());
        userDTO.setUsername(user.getUsername());
        userDTO.setFullName(user.getFullname());
        userDTO.setCreateDate(user.getCreatedate());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setAddress(user.getAddress());
        userDTO.setStatus(user.getStatus());
        //tạo 1 set chứa các roledto của 1 user
        Set<CrmRoleDTO> set = new HashSet<>();
        //dùng vòng lặp lấy ra các role từ userrole trong đối tượng user
        //sau đó convert từ role => roledto và add vào set vừa tạo
        for (CrmUserRole userRole: user.getUserRoles()) {
            CrmRoleDTO roleDto = roleDtoMapper(userRole.getRole());
            set.add(roleDto);
        }
        //gán set roledto vào set roledto của userdto
        userDTO.setUserRoles(set);
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
        userRoleDTO.setFullName(userRole.getUser().getFullname());
        userRoleDTO.setRoleId(userRole.getRole().getRoleid());
        userRoleDTO.setRoleName(userRole.getRole().getRolename());
        return userRoleDTO;
    }
    public CrmRoleFuncDTO roleFuncDTOMapper(CrmRoleFunction roleFunction){
        CrmRoleFuncDTO roleFuncDTO = new CrmRoleFuncDTO();
        roleFuncDTO.setId(roleFunction.getRfid());
        roleFuncDTO.setRoleId(roleFunction.getRole().getRoleid());
        roleFuncDTO.setRoleName(roleFunction.getRole().getRolename());
        roleFuncDTO.setFuncId(roleFunction.getFunction().getFuncid());
        roleFuncDTO.setFuncName(roleFunction.getFunction().getFuncname());
        return roleFuncDTO;
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

    public CrmUserDTO projectMemberMDTOMapper(CrmProjectMember projectMember) {
        CrmUserDTO dto = new CrmUserDTO();
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
