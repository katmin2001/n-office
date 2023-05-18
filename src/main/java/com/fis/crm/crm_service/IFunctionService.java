package com.fis.crm.crm_service;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface IFunctionService {
    public CrmFunction registerFunc(CrmFunctionDTO function);       //xong          da test
    public List<CrmFunctionDTO> getAllFuncs();                      //xong          da test
    public void deleteFuncByFuncName(CrmFunctionDTO funtion);       //xong          da test
}
