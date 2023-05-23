package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_entity.DTO.UpdateNewRoleForUser;
import com.fis.crm.crm_service.CrmUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Qualifier("crmUserRoleServiceImpl")
    @Autowired
    private CrmUserRoleService userRoleService;

    @GetMapping("/get-all-user-role")
    public ResponseEntity<Result>  getAllUserRole(){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",userRoleService.getAllUserRole()));
    };

    @PostMapping("/add-user-role")
    public ResponseEntity<Result> addUserRole(@RequestBody CrmUserRoleDTO userRoleDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Result("OK","Thêm mới thành công",userRoleService.addUserRole(userRoleDTO)));
    };

    @PostMapping("/find-user-role-by-role/{roleId}")
    public ResponseEntity<Result> findUserByRole(@PathVariable Long roleId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",userRoleService.findUserByRole(roleId)));
    }

    @PostMapping("/find-user-role-by-user/{userId}")
    public ResponseEntity<Result> findUserByUser(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",userRoleService.findRoleByUser(userId)));
    }

    @PutMapping("/update-user-role")
    public ResponseEntity<Result> updateUserRole(@RequestBody UpdateNewRoleForUser newRoleForUser){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Cập nhật thành công",userRoleService.updateUserRole(newRoleForUser)));
    };

    @DeleteMapping("/delete-user-role")
    public ResponseEntity<Result> deleteUserRoleForUser(@RequestBody CrmUserRoleDTO userRoleDTO){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Xoá thành công",userRoleService.deleteUserRoleForUser(userRoleDTO)));
    };
}
