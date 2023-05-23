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
        userDTO.setUserRoles(user.getUserRoles());
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
    public CrmUserRoleDTO userRoleDTOMapper1(CrmUserRole userRole){
        CrmUserRoleDTO userRoleDTO = new CrmUserRoleDTO();
        userRoleDTO.setRoleId(userRole.getRole().getRoleid());
        userRoleDTO.setRoleName(userRole.getRole().getRolename());
        return userRoleDTO;
    }
}
