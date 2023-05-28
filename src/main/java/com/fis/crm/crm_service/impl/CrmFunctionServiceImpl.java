package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_repository.CrmFunctionRepo;
import com.fis.crm.crm_service.CrmFunctionService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrmFunctionServiceImpl implements CrmFunctionService {

    private CrmFunctionRepo functionRepo;

    public CrmFunctionServiceImpl(CrmFunctionRepo functionRepo) {
        this.functionRepo = functionRepo;
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
}
