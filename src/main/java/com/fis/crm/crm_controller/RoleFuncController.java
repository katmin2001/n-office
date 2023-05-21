package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewFuncForRole;
import com.fis.crm.crm_service.IRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-func")
public class RoleFuncController {
    @Qualifier("crmRoleFuncServiceImpl")
    @Autowired
    private IRoleFuncService roleFuncService;

    @GetMapping("/get-all-user-role")
    public ResponseEntity<List<CrmRoleFuncDTO>>  getAllRoleFunc(){
        return ResponseEntity.ok(roleFuncService.getAllRoleFunc());
    };

    @PostMapping("/add-user-role")
    public ResponseEntity<CrmRoleFunction>  addRoleFunction(@RequestBody CrmRoleFuncDTO roleFuncDTO){
        CrmRoleFunction roleFunction = roleFuncService.addRoleFunction(roleFuncDTO);
        return ResponseEntity.ok(roleFunction);
    };

    @PutMapping("/update-role-func")
    public ResponseEntity<CrmRoleFunction>  updateRoleFuncByRoleId(@RequestBody UpdateNewFuncForRole newFuncForRole){
        return ResponseEntity.ok(roleFuncService.updateRoleFuncByRoleId(newFuncForRole));
    };

    @DeleteMapping("/delete-role-func")
    public ResponseEntity<CrmRoleFunction>  deleteRoleFuncByRoleId(@RequestBody CrmRoleFuncDTO roleFuncDTO){
        return ResponseEntity.ok(roleFuncService.deleteRoleFuncByRoleId(roleFuncDTO));
    };

    @PostMapping("/find-func-by-role/{roleId}")
    public ResponseEntity<List<CrmRoleFuncDTO>>  findFuncByRoleId(@PathVariable Long roleId){
        return ResponseEntity.ok(roleFuncService.findFuncByRoleId(roleId));
    };

    @PostMapping("/find-role-by-func/{funcId}")
    public ResponseEntity<List<CrmRoleFuncDTO>>  findRoleByFuncId(@PathVariable Long funcId){
        return ResponseEntity.ok(roleFuncService.findRoleByFuncId(funcId));
    };

    @GetMapping("/test")
    public List<CrmRoleFunction> getAll(){
        return roleFuncService.testGetAll();
    }

}

























