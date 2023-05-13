package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;

public class UserDtoMapper {

    public Crm_UserDTO userDtoMapper(CrmUser user){
        Crm_UserDTO userDTO = new Crm_UserDTO();
            userDTO.setUserName(user.getUsername());
            userDTO.setFullName(user.getFullname());
            userDTO.setCreateDate(user.getCreatedate());
            userDTO.setPhone(user.getPhone());
            userDTO.setBirthday(user.getBirthday());
            userDTO.setAddress(user.getAddress());
            userDTO.setStatus(user.getStatus());
        return userDTO;
    }
}
