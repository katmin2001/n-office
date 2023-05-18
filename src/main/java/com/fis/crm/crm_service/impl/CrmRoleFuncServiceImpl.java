package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_repository.IRoleFuncRepo;
import com.fis.crm.crm_service.IRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CrmRoleFuncServiceImpl implements IRoleFuncService {
    @Autowired
    IRoleFuncRepo roleFuncRepo;
    @Override
    public Set<CrmRole> findRoleByFunc(Long funcId) {
        Set<CrmRole> set = new HashSet<>();
        //lấy lên list tất cả rolefunc dưới db
        List<CrmRoleFunction> listAllRoleFunctions = roleFuncRepo.findAll();
        //dùng vòng lặp lấy ra từng đối tượng rolefunc trong db có cùng funcid tham số và cho vào listrolefunc
        for(CrmRoleFunction roleFunction : listAllRoleFunctions){
            if(roleFunction.getFunction().getFuncid().equals(funcId)){
                set.add(roleFunction.getRole());
            }
        }
        return set;
    }

    @Override
    public CrmRoleFunction updateRoleFuncByRoleId(Long roleId) {
        return null;
    }

    @Override
    public CrmRoleFunction deleteRoleFuncByRoleId(Long roleId) {
        return null;
    }

    @Override
    public CrmRoleFunction addRoleFunction(CrmRoleFunction roleFunction) {
        return null;
    }

    @Override
    public List<CrmFunction> findFuncByRoleId(Long roleId) {
        return null;
    }
}
