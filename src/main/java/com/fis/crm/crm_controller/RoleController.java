package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.DTO.CrmRoleDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_service.CrmRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Qualifier("crmRoleServiceImpl")
    @Autowired
    private CrmRoleService roleService;
    @PostMapping("/register-role")
    public ResponseEntity<Result>  registerRole(@RequestBody CrmRoleDTO role){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Result("OK","Khởi tạo thành công",roleService.registerRole(role)));
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<Result> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tim kiếm thành công",roleService.getAllRoles()));
    };

    @DeleteMapping("/delete-role-by-rolename")
    public ResponseEntity<Result> deleteRoleByRoleName(@RequestBody CrmRoleDTO roleDTO){
        roleService.deleteRoleByRoleName(roleDTO);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Xoá thành công",""));

    }
}
