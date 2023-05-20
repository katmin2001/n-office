package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface IUserService {
    public CrmUser registerUser(Crm_UserDTO userDTO, String password);          //xong da test
    public CrmUser updateCrmUser(Long userId, CrmUser user);                    //xong  da test => chua hop li
    public Optional<CrmUser>  findByCrmUserId(Long userId);                     //xong  da test
    public List<Crm_UserDTO> findUserDto(Crm_UserDTO crmUser);                  //xong  da test
    public List<Crm_UserDTO> findAllUserDto();                                  //xong  da test
    public Crm_UserDTO getUserDetail(Long userId);                              //xong  da test
    public Set<Crm_UserDTO> findUserByFunc(Long funcId);                        //xong  da test
    public CrmUser changePassword(Long userId, String newPassword);             //xong  da test => chua hop li => lam lai
    public Set<Crm_UserDTO> findCrmUserDtoByRoleId(Long roleId);                //xong  chua test
}
