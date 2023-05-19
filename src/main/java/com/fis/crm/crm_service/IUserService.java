package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IUserService {
    public CrmUser registerUser(CrmUser user);                                 //xong
    public CrmUser updateUser(Long userId, CrmUser user);                      //xong
    public List<CrmUser> findUserDto(String fullName, Date create_date,
                        String phone, Date birthday, String address,
                        String status, int roleId);
    public List<Crm_UserDTO> findAllUserDto();                                 //xong
    public Crm_UserDTO getUserDetail(Long userId);                             //xong
    public CrmUser getUserById(Long userId);
    public List<Crm_UserDTO> findUserByFunc(Long funcId);
    public CrmUserRole updateUserRole(Long userId, Long roleId);
    public CrmUser changePassword(Long userId, String newPassword);            //xong
}
