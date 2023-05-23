package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewFuncForRole;
import com.fis.crm.crm_repository.IFunctionRepo;
import com.fis.crm.crm_repository.IRoleFuncRepo;
import com.fis.crm.crm_repository.IRoleRepo;
import com.fis.crm.crm_service.CrmRoleFuncService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CrmRoleFuncServiceImpl implements CrmRoleFuncService {
    @Autowired
    IRoleFuncRepo roleFuncRepo;
    @Autowired
    IRoleRepo roleRepo;
    @Autowired
    IFunctionRepo functionRepo;
    private final Logger log = LoggerFactory.getLogger(CrmRoleFuncServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();
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
    public CrmRoleFunction updateRoleFuncByRoleId(UpdateNewFuncForRole newFuncForRole) {
        //tìm role theo roleid lấy ra từ roleFunction
        CrmRole role = roleRepo.findCrmRoleByRoleid(newFuncForRole.getRoleId());
        //tìm func theo funcid lấy ra từ roleFunction
        CrmFunction function = functionRepo.findCrmFunctionByFuncId(newFuncForRole.getFuncId());
        //tìm rolefunc theo role và func vừa tìm đc
        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
        if (crmRoleFunction==null){
            CrmRoleFunction newRoleFunc = new CrmRoleFunction();
            newRoleFunc.setRole(role);
            newRoleFunc.setFunction(function);
            roleFuncRepo.save(newRoleFunc);
        }
        CrmFunction newFunc = functionRepo.findCrmFunctionByFuncId(newFuncForRole.getNewFuncId());
        if (newFunc==null){
            throw new NullPointerException();
        }
        if (crmRoleFunction!=null){
            crmRoleFunction.setFunction(newFunc);
        }
        return roleFuncRepo.save(crmRoleFunction);
    }

    @Override
    public CrmRoleFunction deleteRoleFuncByRoleId(CrmRoleFuncDTO roleFuncDTO) {
        //tìm role theo roleid lấy ra từ roleFunction
        CrmRole role = roleRepo.findCrmRoleByRoleid(roleFuncDTO.getRoleId());
        //tìm func theo funcid lấy ra từ roleFunction
        CrmFunction function = functionRepo.findCrmFunctionByFuncId(roleFuncDTO.getFuncId());
        //tìm rolefunc theo role và func vừa tìm đc
        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
        if (crmRoleFunction!=null){
            roleFuncRepo.delete(crmRoleFunction);
        }
        return null;
    }

    @Override
    public CrmRoleFunction addRoleFunction(CrmRoleFuncDTO roleFuncDTO) {
        //tìm role theo roleid lấy ra từ roleFunction
        CrmRole role = roleRepo.findCrmRoleByRoleid(roleFuncDTO.getRoleId());
        //tìm func theo funcid lấy ra từ roleFunction
        CrmFunction function = functionRepo.findCrmFunctionByFuncId(roleFuncDTO.getFuncId());
        //tìm rolefunc theo role và func vừa tìm đc
        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
        if (crmRoleFunction!=null){
            return null;
        }
        //nếu rolefunc không tồn tại thì tạo mới 1 đối tượng rolefunc gán giá trị của role và func vào và lưu mới
        CrmRoleFunction newRoleFunc = new CrmRoleFunction();
        newRoleFunc.setFunction(function);
        newRoleFunc.setRole(role);
        return roleFuncRepo.save(newRoleFunc);
    }

    @Override
    public List<CrmRoleFuncDTO> findFuncByRoleId(Long roleId) {
        CrmRole role = roleRepo.findCrmRoleByRoleid(roleId);
        List<CrmRoleFunction> list = roleFuncRepo.findCrmRoleFunctionsByRole(role);
        List<CrmRoleFuncDTO> dtoList = new ArrayList<>();
        for (CrmRoleFunction value : list){
            dtoList.add(mapper.roleFuncDTOMapper(value));
        }
        return dtoList;
    }

    @Override
    public List<CrmRoleFuncDTO> findRoleByFuncId(Long funcId) {
        CrmFunction function = functionRepo.findCrmFunctionByFuncId(funcId);
        List<CrmRoleFunction> list = roleFuncRepo.findCrmRoleFunctionsByFunction(function);
        List<CrmRoleFuncDTO> dtoList = new ArrayList<>();
        for (CrmRoleFunction value : list){
            dtoList.add(mapper.roleFuncDTOMapper(value));
        }
        return dtoList;
    }

    @Override
    public List<CrmRoleFuncDTO> getAllRoleFunc() {
        List<CrmRoleFuncDTO> dtoList = new ArrayList<>();
        List<CrmRoleFunction> list = roleFuncRepo.findAll();
        for (CrmRoleFunction value : list){
            dtoList.add(mapper.roleFuncDTOMapper(value));
        }
        return dtoList;
    }

    @Override
    public List<CrmRoleFunction> testGetAll() {
        return roleFuncRepo.findAll();
    }
}
