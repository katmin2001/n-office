package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;

import java.util.List;
import java.util.Set;


public interface CrmUserService {
    public CrmUser registerUser(Crm_UserDTO userDTO, String password);          //xong da test
    public CrmUser updateCrmUser(Long userId, CrmUser user);                    //xong  da test => chua hop li
    public Crm_UserDTO  findByCrmUserId(Long userId);                           //xong  da test
    public List<Crm_UserDTO> findUserDto(Crm_UserDTO crmUser);                  //xong  da test => chua hop li
    public List<Crm_UserDTO> findAllUserDto();                                  //xong  da test
    public Crm_UserDTO getUserDetail(Long userId);                              //xong  da test
    public Set<Crm_UserDTO> findUserByFunc(Long funcId);                        //xong  da test
    public Set<Crm_UserDTO> findCrmUserDtoByRoleId(Long roleId);                //xong  chua test
}
