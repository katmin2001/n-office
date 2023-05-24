package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.DTO.CrmRoleFuncDTO;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_entity.DTO.UpdateNewFuncForRole;
import com.fis.crm.crm_service.CrmRoleFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Result>  addRoleFunction(@RequestBody CrmRoleFuncDTO roleFuncDTO){
        CrmRoleFunction roleFunction = roleFuncService.addRoleFunction(roleFuncDTO);
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Thêm mới thành công",roleFunction));
    };

    @PutMapping("/update-role-func")
    public ResponseEntity<Result>  updateRoleFuncByRoleId(@RequestBody UpdateNewFuncForRole newFuncForRole){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Cập nhật thành công",roleFuncService.updateRoleFuncByRoleId(newFuncForRole)));
    };

    @DeleteMapping("/delete-role-func")
    public ResponseEntity<Result>  deleteRoleFuncByRoleId(@RequestBody CrmRoleFuncDTO roleFuncDTO){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK","Xoá thành công",roleFuncService.deleteRoleFuncByRoleId(roleFuncDTO)));
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

}

























