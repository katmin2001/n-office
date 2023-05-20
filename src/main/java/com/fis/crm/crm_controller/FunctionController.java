package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/func")
public class FunctionController {
    @Qualifier("crmFunctionServiceImpl")
    @Autowired
    private IFunctionService functionService;

    @PostMapping("/register-func")
    public ResponseEntity<CrmFunction> registerFunc(@RequestBody CrmFunctionDTO function){
        return ResponseEntity.ok(functionService.registerFunc(function));
    };
    @GetMapping("/get-all-func")
    public ResponseEntity<List<CrmFunctionDTO>>  getAllFuncs(){
        return ResponseEntity.ok(functionService.getAllFuncs());
    };
    @DeleteMapping("/delete-func-by-funcname")
    public void deleteFuncByFuncName(@RequestBody CrmFunctionDTO functionDTO){
        functionService.deleteFuncByFuncName(functionDTO);
    };
}
