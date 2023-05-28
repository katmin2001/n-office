package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.CrmUserDTO;

import java.util.List;
import java.util.Set;


public interface CrmUserService {
    public CrmUser registerUser(CrmUserDTO userDTO, String password);          //xong da test
    public CrmUser updateCrmUser(Long userId, CrmUser user);                    //xong  da test => chua hop li
    public CrmUserDTO findByCrmUserId(Long userId);                           //xong  da test
    public List<CrmUserDTO> findUserDto(CrmUserDTO crmUser);                  //xong  da test => chua hop li
    public List<CrmUserDTO> findAllUserDto();                                  //xong  da test
    public CrmUserDTO getUserDetail(Long userId);                              //xong  da test
    public List<CrmUserDTO> findUserByFunc(Long funcId);                        //xong  da test
    public List<CrmUserDTO> findCrmUserDtoByRoleId(Long roleId);

}
