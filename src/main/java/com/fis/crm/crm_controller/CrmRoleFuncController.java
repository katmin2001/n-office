package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_service.CrmRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-func")
public class CrmRoleFuncController {
    @Qualifier("crmRoleFuncServiceImpl")
    @Autowired
    private CrmRoleFuncService roleFuncService;

    @GetMapping("/get-all-user-role")
    public ResponseEntity<Result>  getAllRoleFunc(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",roleFuncService.getAllRoleFunc()));
    };

    @PostMapping("/add-user-role")
    public ResponseEntity<Result>  addRoleFunction(@RequestBody RegisterRoleFuncDTO roleFuncDTO){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Thêm mới thành công",roleFuncService.addRoleFunction(roleFuncDTO)));
    };

    @PutMapping("/update-role-func")
    public ResponseEntity<Result>  updateRoleFuncByRoleId(@RequestBody UpdateNewFuncForRole newFuncForRole){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Cập nhật thành công",roleFuncService.updateRoleFuncByRoleId(newFuncForRole)));
    };

    @DeleteMapping("/delete-role-func")
    public ResponseEntity<String>  deleteRoleFunc(@RequestBody RegisterRoleFuncDTO roleFuncDTO){
        return ResponseEntity.ok(roleFuncService.deleteRoleFunc(roleFuncDTO));
    };

    @PostMapping("/find-func-by-role/{roleId}")
    public ResponseEntity<Result>  findFuncByRoleId(@PathVariable Long roleId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",roleFuncService.findFuncByRoleId(roleId)));
    };

    @PostMapping("/find-role-by-func/{funcId}")
    public ResponseEntity<Result>  findRoleByFuncId(@PathVariable Long funcId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",roleFuncService.findRoleByFuncId(funcId)));
    };

    @GetMapping("/find-func-by-roleid/{roleId}")
    public ResponseEntity<Result>  findFuncDTOByRoleId(@PathVariable Long roleId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",roleFuncService.findFuncDTOByRoleId(roleId)));
    }

}

























