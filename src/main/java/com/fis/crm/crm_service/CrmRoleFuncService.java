package com.fis.crm.crm_service;


import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_entity.DTO.CrmRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.RegisterRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewFuncForRole;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public interface CrmRoleFuncService {
    public Set<CrmRole> findRoleByFunc(Long funcId);
    public List<CrmRoleFunction> updateRoleFuncByRoleId(UpdateNewFuncForRole newFuncForRole);     //xong
    public String deleteRoleFunc(RegisterRoleFuncDTO roleFuncDTO);
    public String addRoleFunction(RegisterRoleFuncDTO roleFuncDTO);
    public List<CrmRoleFuncDTO> findFuncByRoleId(Long roleId);                              //xong
    public List<CrmRoleFuncDTO> findRoleByFuncId(Long funcId);                              //XONG
    public List<CrmRoleFuncDTO > getAllRoleFunc();                                          //xong
    public List<CrmFunctionDTO> findFuncDTOByRoleId(Long roleId);


}
