package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewRoleForUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface IUserRoleService {
    public CrmUserRole updateUserRole(UpdateNewRoleForUser newRoleForUser);     //xong  da test
    public CrmUserRole addUserRole(CrmUserRoleDTO userRoleDTO);                 //xong  da test
    public CrmUserRole deleteUserRoleForUser(CrmUserRoleDTO userRoleDTO);       //xong  da test
    public List<CrmUserRoleDTO> getAllUserRole();                               //xong  da test
    public List<CrmUserRoleDTO> findUserByRole(Long roleId);                    //xong  da test
    public List<CrmUserRoleDTO> findRoleByUser(Long userId);                    //xong  da test
}
