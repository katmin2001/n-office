package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmFunction;
import com.fis.crm.crm_entity.DTO.CrmFunctionDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Result> registerFunc(@RequestBody CrmFunctionDTO function){
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Result("CREATED","KHởi tạo thành công",functionService.registerFunc(function)));
    };
    @GetMapping("/get-all-func")
    public ResponseEntity<Result>  getAllFuncs(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",functionService.getAllFuncs()));
    };
    @DeleteMapping("/delete-func-by-funcname")
    public ResponseEntity<Result> deleteFuncByFuncName(@RequestBody CrmFunctionDTO functionDTO){
        functionService.deleteFuncByFuncName(functionDTO);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Xoá thành công",""));
    };
}
