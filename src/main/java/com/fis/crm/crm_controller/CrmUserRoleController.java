package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.DTO.*;
import com.fis.crm.crm_service.CrmUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-role")
public class CrmUserRoleController {
    @Qualifier("crmUserRoleServiceImpl")
    @Autowired
    private CrmUserRoleService userRoleService;

    @GetMapping("/get-all-user-role")
    public ResponseEntity<Result>  getAllUserRole(){
        List<CrmUserRoleDTO> dtoList = userRoleService.getAllUserRole();
        if (dtoList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result("NOT_OK","Không tồn tại đôí tượng cần tìm",""));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",dtoList));
    };

    @PostMapping("/add-user-role")
    public ResponseEntity<String> addUserRole(@RequestBody RegisterUserRoleDTO userRoleDTO){
        return ResponseEntity.ok(userRoleService.addUserRole(userRoleDTO));
    };

    @PostMapping  ("/find-user-role-by-role/{roleId}")
    public ResponseEntity<Result> findUserByRole(@PathVariable Long roleId) {
        List<CrmUserRoleDTO> list = userRoleService.findUserByRole(roleId);
        if (list.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result("NOT_OK","Tìm kiếm thất bại",""));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",list));
    }

    @GetMapping("/find-user-role-by-user/{userId}")
    public ResponseEntity<Result> findUserByUser(@PathVariable Long userId){
        List<CrmUserRoleDTO> list = userRoleService.findRoleByUser(userId);
        if (list.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result("NOT_OK","Tìm kiếm thất bại",""));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Tìm kiếm thành công",list));
    }

    @PutMapping("/update-user-role")
    public ResponseEntity<Result> updateUserRole(@RequestBody UpdateNewRoleForUser newRoleForUser){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Cập nhật thành công",userRoleService.updateUserRole(newRoleForUser)));
    };

    @DeleteMapping("/delete-user-role")
    public ResponseEntity<Result> deleteUserRoleForUser(@RequestBody DeleteUserRoleDTO userRoleDTO){
        userRoleService.deleteUserRoleForUser(userRoleDTO);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Xoá thành công",""));
    };
}
