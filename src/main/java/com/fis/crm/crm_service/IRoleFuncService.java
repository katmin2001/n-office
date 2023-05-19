package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IRoleFuncService {
    public Set<CrmRole> findRoleByFunc(Long funcId);
    public CrmRoleFunction updateRoleFuncByRoleId(Long roleId);
    public CrmRoleFunction deleteRoleFuncByRoleId(Long roleId);
    public CrmRoleFunction addRoleFunction(CrmRoleFunction roleFunction);
    public List<CrmFunction> findFuncByRoleId(Long roleId);

}
