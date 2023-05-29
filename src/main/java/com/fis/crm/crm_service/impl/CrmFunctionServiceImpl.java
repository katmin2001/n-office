package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_entity.DTO.CrmUserDTO;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_repository.CrmFunctionRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmUserRoleRepo;
import com.fis.crm.crm_service.CrmFunctionService;
import com.fis.crm.crm_service.CrmRoleFuncService;
import com.fis.crm.crm_service.CrmUserRoleService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CrmFunctionServiceImpl implements CrmFunctionService {

    private CrmFunctionRepo functionRepo;
    private CrmUserRepo userRepo;
    private CrmUserRoleRepo userRoleRepo;
    private CrmUserRoleService userRoleService;
    private CrmRoleFuncService roleFuncService;

    public CrmFunctionServiceImpl(CrmFunctionRepo functionRepo, CrmUserRepo userRepo, CrmUserRoleRepo userRoleRepo, @Qualifier("crmUserRoleServiceImpl") CrmUserRoleService userRoleService, @Qualifier("crmRoleFuncServiceImpl") CrmRoleFuncService roleFuncService) {
        this.functionRepo = functionRepo;
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRoleService = userRoleService;
        this.roleFuncService = roleFuncService;
    }

    private final Logger log = LoggerFactory.getLogger(CrmFunctionServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();
    @Override
    public CrmFunction registerFunc(CrmFunctionDTO functionDTO) {
        CrmFunction crmFunction = functionRepo.findCrmFunctionByRolename(functionDTO.getFuncName());
        if (crmFunction!=null){
            log.warn("function đã tồn tại", new NullPointerException());
        }
        CrmFunction function = new CrmFunction();
        function.setFuncname(functionDTO.getFuncName());
        log.info("Tạo mới thành công");
        return functionRepo.save(function);
    }

    @Override
    public List<CrmFunctionDTO> getAllFuncs() {
        List<CrmFunction> functionList = functionRepo.findAll();
        if (functionList.size()==0){
            log.error("Không tồn tại func nào cả", new NullPointerException());
        }
        List<CrmFunctionDTO> dtoList = new ArrayList<>();
        for (CrmFunction function : functionList){
            dtoList.add(mapper.functionDTOMapper(function));
        }
        return dtoList;
    }

    @Override
    public void deleteFuncByFuncName(CrmFunctionDTO functionDTO) {
        CrmFunction function = functionRepo.findCrmFunctionByRolename(functionDTO.getFuncName());
        if (function==null){
            log.error("Đối tượng cần xoá không tồn tại", new IllegalArgumentException());
        }
        functionRepo.delete(function);
        log.info("XOá thành công");
    }

    @Override
    public List<CrmFunctionDTO> findByUserId(Long userId) {
        CrmUser user = userRepo.findCrmUserByUserid(userId);
        List<CrmFunctionDTO> functionDTOS = new ArrayList<>();
        List<CrmFunctionDTO> list = new ArrayList<>();
        Set<String> funcName = new HashSet<>();
        if (user==null){
            log.error("Tài khoản không tồn tại", new NullPointerException());
        }
        List<CrmUserRole> userRole = userRoleRepo.findCrmUserRoleByUserId(userId);
        if (userRole.isEmpty()){
            log.error("User chưa được set role nào", new NullPointerException());
        }
        for (CrmUserRole value : userRole){
            functionDTOS.addAll(roleFuncService.findFuncDTOByRoleId(value.getRole().getRoleid()));
        }
        if (functionDTOS.isEmpty()){
            log.error("Không tồn tại func với roleid cho trước", new NullPointerException());
        }
        //        loại bỏ giá trị trùng lặp qua username
        for(CrmFunctionDTO value : functionDTOS){
            if(!funcName.contains(value.getFuncName())){
                funcName.add(value.getFuncName());
                list.add(value);
            }
        }
        return list;
    }
}
