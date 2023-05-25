package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.RegisterUserRoleDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewRoleForUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@Transactional
public interface CrmUserRoleService {
    public CrmUserRole updateUserRole(UpdateNewRoleForUser newRoleForUser);
    public String addUserRole(RegisterUserRoleDTO userRoleDTO);                 //xong
    public CrmUserRole deleteUserRoleForUser(CrmUserRoleDTO userRoleDTO);
    public List<CrmUserRoleDTO> getAllUserRole();                               //xong  da test
    public List<CrmUserRoleDTO> findUserByRole(Long roleId);                    //xong  da test
    public List<CrmUserRoleDTO> findRoleByUser(Long userId);                    //xong  da test
}
