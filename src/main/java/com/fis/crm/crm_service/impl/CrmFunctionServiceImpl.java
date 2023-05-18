package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_repository.IFunctionRepo;
import com.fis.crm.crm_service.IFunctionService;
import com.fis.crm.crm_util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrmFunctionServiceImpl implements IFunctionService {
    @Autowired
    private IFunctionRepo functionRepo;
    private final DtoMapper mapper = new DtoMapper();
    @Override
    public CrmFunction registerFunc(CrmFunctionDTO functionDTO) {
        CrmFunction crmFunction = functionRepo.findCrmFunctionByRolename(functionDTO.getFuncName());
        if (crmFunction!=null){
            return null;
        }
        CrmFunction function = new CrmFunction();
        function.setFuncname(functionDTO.getFuncName());
        return functionRepo.save(function);
    }

    @Override
    public List<CrmFunctionDTO> getAllFuncs() {
        List<CrmFunction> functionList = functionRepo.findAll();
        List<CrmFunctionDTO> dtoList = new ArrayList<>();
        for (CrmFunction function : functionList){
            dtoList.add(mapper.functionDTOMapper(function));
        }
        return dtoList;
    }

    @Override
    public void deleteFuncByFuncName(CrmFunctionDTO functionDTO) {
        CrmFunction function = functionRepo.findCrmFunctionByRolename(functionDTO.getFuncName());
        if (function!=null){
            functionRepo.delete(function);
        }
    }
}
