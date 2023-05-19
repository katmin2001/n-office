package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.DTO.CrmRoleDTO;
import com.fis.crm.crm_service.IRoleService;
import oracle.jdbc.proxy.annotation.Pre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Qualifier("crmRoleServiceImpl")
    @Autowired
    private IRoleService roleService;
    @PostMapping("/register-role")
    public ResponseEntity<CrmRole>  registerRole(@RequestBody CrmRoleDTO role){
        return ResponseEntity.ok(roleService.registerRole(role));
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<List<CrmRoleDTO>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    };

    @DeleteMapping("/delete-role-by-rolename")
    public void deleteRoleByRoleName(@RequestBody CrmRoleDTO roleDTO){
        roleService.deleteRoleByRoleName(roleDTO);
    }
}
