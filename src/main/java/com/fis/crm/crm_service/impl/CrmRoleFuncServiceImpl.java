package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_entity.DTO.CrmRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.RegisterRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewFuncForRole;
import com.fis.crm.crm_repository.CrmFunctionRepo;
import com.fis.crm.crm_repository.CrmRoleFuncRepo;
import com.fis.crm.crm_repository.CrmRoleRepo;
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
    private CrmRoleFuncRepo roleFuncRepo;
    private CrmRoleRepo roleRepo;
    private CrmFunctionRepo functionRepo;

    public CrmRoleFuncServiceImpl(CrmRoleFuncRepo roleFuncRepo, CrmRoleRepo roleRepo, CrmFunctionRepo functionRepo) {
        this.roleFuncRepo = roleFuncRepo;
        this.roleRepo = roleRepo;
        this.functionRepo = functionRepo;
    }

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
    public List<CrmRoleFunction> updateRoleFuncByRoleId(UpdateNewFuncForRole newFuncForRole) {
        if (newFuncForRole.getRoleName()==null){
            log.error("Role cần cập nhật chưa được chọn", new NullPointerException());
        }
        if (newFuncForRole.getFunctionDTOS().size()==0){
            log.error("Func cần cập nhật chưa được chọn", new NullPointerException());
        }
        if (newFuncForRole.getNewFuncName().size()==0){
            log.error("Func mới nhật chưa được chọn", new NullPointerException());
        }
        CrmRole role = roleRepo.findCrmRoleByRolename(newFuncForRole.getRoleName());
        List<CrmRoleFunction> a = roleFuncRepo.findCrmRoleFunctionsByRoleName(newFuncForRole.getRoleName());
        for (int i = 0; i < a.size(); i++){
            roleFuncRepo.delete(a.get(i));
            log.info("Xoá rolefunc với rolename là "+newFuncForRole.getRoleName()+" có funcname là "+a.get(i).getFunction().getFuncname());
        }
        List<CrmRoleFunction> list = new ArrayList<>();
        for (CrmFunctionDTO newFunc : newFuncForRole.getNewFuncName()){
            CrmRoleFunction newRoleFunction = new CrmRoleFunction();
            newRoleFunction.setRole(role);
            newRoleFunction.setFunction(functionRepo.findCrmFunctionByFuncName(newFunc.getFuncName()));
            roleFuncRepo.save(newRoleFunction);
            list.add(newRoleFunction);
            log.info("Cập nhật "+newFunc.getFuncName()+" cho "+newFuncForRole.getRoleName()+" thành công");
        }
        return list;
    }

//    @Override
//    public CrmRoleFunction updateRoleFuncByRoleId(UpdateNewFuncForRole newFuncForRole) {
//        //tìm role theo roleid lấy ra từ roleFunction
//        CrmRole role = roleRepo.findCrmRoleByRoleid(newFuncForRole.getRoleId());
//        //tìm func theo funcid lấy ra từ roleFunction
//        CrmFunction function = functionRepo.findCrmFunctionByFuncId(newFuncForRole.getFuncId());
//        //tìm rolefunc theo role và func vừa tìm đc
//        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
//        if (crmRoleFunction==null){
//            CrmRoleFunction newRoleFunc = new CrmRoleFunction();
//            newRoleFunc.setRole(role);
//            newRoleFunc.setFunction(function);
//            roleFuncRepo.save(newRoleFunc);
//        }
//        CrmFunction newFunc = functionRepo.findCrmFunctionByFuncId(newFuncForRole.getNewFuncId());
//        if (newFunc==null){
//            throw new NullPointerException();
//        }
//        if (crmRoleFunction!=null){
//            crmRoleFunction.setFunction(newFunc);
//        }
//        return roleFuncRepo.save(crmRoleFunction);
//    }

    @Override
    public String deleteRoleFunc(RegisterRoleFuncDTO roleFuncDTO) {
        if (roleFuncDTO.getRoleName()==null || roleFuncDTO.getFuncName().size()==0){
            log.error("Chưa lựa chọn roleName hoặc funcName",new NullPointerException());
        }
        for (String value : roleFuncDTO.getFuncName()){
            CrmRoleFunction roleFunction = roleFuncRepo
                .findCrmRoleFunctionsByRoleNameAndFunctionName(roleFuncDTO.getRoleName(), value);
            if (roleFunction==null){
                log.warn("Không tồn tại đối tượng cần xoá");
            }
            roleFuncRepo.delete(roleFunction);
            log.warn("Xoá thành công "+value+" của "+roleFuncDTO.getRoleName());
        }
        return "Xoá thành công";
    }

//    @Override
//    public CrmRoleFunction deleteRoleFunc(RegisterRoleFuncDTO roleFuncDTO) {
//        //tìm role theo roleid lấy ra từ roleFunction
//        CrmRole role = roleRepo.findCrmRoleByRoleid(roleFuncDTO.getRoleId());
//        //tìm func theo funcid lấy ra từ roleFunction
//        CrmFunction function = functionRepo.findCrmFunctionByFuncId(roleFuncDTO.getFuncId());
//        //tìm rolefunc theo role và func vừa tìm đc
//        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
//
//        if (crmRoleFunction!=null){
//            roleFuncRepo.delete(crmRoleFunction);
//        }
//        throw new NullPointerException();
//    }

    @Override
    public String addRoleFunction(RegisterRoleFuncDTO roleFuncDTO) {
        if (roleFuncDTO.getRoleName()==null || roleFuncDTO.getFuncName().size()==0){
            log.error("Thông tin role hoặc func bị bỏ trống");
            throw new NullPointerException();
        }
        for (String value : roleFuncDTO.getFuncName()){
            CrmRoleFunction roleFunction = roleFuncRepo
                .findCrmRoleFunctionsByRoleNameAndFunctionName(roleFuncDTO.getRoleName(), value);
            if (roleFunction!=null){
                log.error("Đã tồn tại "+value+" cho "+roleFuncDTO.getRoleName());
            }
            CrmRole role = roleRepo.findCrmRoleByRolename(roleFuncDTO.getRoleName());
            CrmFunction function = functionRepo.findCrmFunctionByFuncName(value);
            CrmRoleFunction newRoleFunc = new CrmRoleFunction();
            newRoleFunc.setRole(role);
            newRoleFunc.setFunction(function);
            roleFuncRepo.save(newRoleFunc);
            log.warn("Thêm mới thành công "+value+" cho "+roleFuncDTO.getRoleName());
        }
        return "Đã thêm func cho role";
    }

//    @Override
//    public CrmRoleFunction addRoleFunction(CrmRoleFuncDTO roleFuncDTO) {
//        //tìm role theo roleid lấy ra từ roleFunction
//        CrmRole role = roleRepo.findCrmRoleByRoleid(roleFuncDTO.getRoleId());
//        //tìm func theo funcid lấy ra từ roleFunction
//        CrmFunction function = functionRepo.findCrmFunctionByFuncId(roleFuncDTO.getFuncId());
//        //tìm rolefunc theo role và func vừa tìm đc
//        CrmRoleFunction crmRoleFunction = roleFuncRepo.findCrmRoleFunctionsByFunctionaAndRole(function,role);
//        if (crmRoleFunction!=null){
//            throw new IllegalArgumentException();
//        }
//        //nếu rolefunc không tồn tại thì tạo mới 1 đối tượng rolefunc gán giá trị của role và func vào và lưu mới
//        CrmRoleFunction newRoleFunc = new CrmRoleFunction();
//        newRoleFunc.setFunction(function);
//        newRoleFunc.setRole(role);
//        return roleFuncRepo.save(newRoleFunc);
//    }

    @Override
    public List<CrmRoleFuncDTO> findFuncByRoleId(Long roleId) {
        List<CrmRoleFunction> list = roleFuncRepo.findCrmRoleFunctionsByRoleId(roleId);
        if (list.size()==0){
            log.error("Không tồn tại rolefunc có roleid là "+ roleId,new NullPointerException());
        }
        List<CrmRoleFuncDTO> dtoList = new ArrayList<>();
        for (CrmRoleFunction value : list){
            dtoList.add(mapper.roleFuncDTOMapper(value));
        }
        return dtoList;
    }

    @Override
    public List<CrmRoleFuncDTO> findRoleByFuncId(Long funcId) {
        List<CrmRoleFunction> list = roleFuncRepo.findCrmRoleFunctionsByFunctionId(funcId);
        if (list.size()==0){
            log.error("Không tồn tại rolefunc có funcid là "+ funcId,new NullPointerException());
        }
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
        if (list.size()==0){
            log.error("Không tồn tại rolefunc nào ",new NullPointerException());
        }
        for (CrmRoleFunction value : list){
            dtoList.add(mapper.roleFuncDTOMapper(value));
        }
        return dtoList;
    }

}
