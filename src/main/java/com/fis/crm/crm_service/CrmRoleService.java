package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.DTO.CrmRoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CrmRoleService {
    public CrmRole registerRole(CrmRoleDTO role);               //xong      da test
    public List<CrmRoleDTO> getAllRoles();                      //xong      da test
    public void deleteRoleByRoleName(CrmRoleDTO roleDTO);          //xong      da test


}
