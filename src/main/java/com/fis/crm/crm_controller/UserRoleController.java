package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewRoleForUser;
import com.fis.crm.crm_service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Qualifier("crmUserRoleServiceImpl")
    @Autowired
    private IUserRoleService userRoleService;

    @GetMapping("/get-all-user-role")
    public ResponseEntity<List<CrmUserRoleDTO>>  getAllUserRole(){
        return ResponseEntity.ok(userRoleService.getAllUserRole());
    };

    @PostMapping("/add-user-role")
    public ResponseEntity<CrmUserRole> addUserRole(@RequestBody CrmUserRoleDTO userRoleDTO){
        return ResponseEntity.ok(userRoleService.addUserRole(userRoleDTO));
    };

    @PostMapping("/find-user-role-by-role/{roleId}")
    public ResponseEntity<List<CrmUserRoleDTO>> findUserByRole(@PathVariable Long roleId){
        return ResponseEntity.ok(userRoleService.findUserByRole(roleId));
    }

    @PostMapping("/find-user-role-by-user/{userId}")
    public ResponseEntity<List<CrmUserRoleDTO>> findUserByUser(@PathVariable Long userId){
        return ResponseEntity.ok(userRoleService.findRoleByUser(userId));
    }

    @PutMapping("/update-user-role")
    public ResponseEntity<CrmUserRole> updateUserRole(@RequestBody UpdateNewRoleForUser newRoleForUser){
        return ResponseEntity.ok(userRoleService.updateUserRole(newRoleForUser));
    };

    @DeleteMapping("/delete-user-role")
    public ResponseEntity<CrmUserRole> deleteUserRoleForUser(@RequestBody CrmUserRoleDTO userRoleDTO){
        return ResponseEntity.ok(userRoleService.deleteUserRoleForUser(userRoleDTO));
    };
}
