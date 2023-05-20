package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_entity.DTO.CrmRoleDTO;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;

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



}
