package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import com.fis.crm.crm_entity.DTO.RegisterUserDto;
import com.fis.crm.crm_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class CrmUserController {
    @Autowired
    IUserService userService;


    @GetMapping("/get-all-users")
    public List<Crm_UserDTO> findAllUserDto(){
        return userService.findAllUserDto();
    };

    @PostMapping("/find-user")
    public ResponseEntity<List<Crm_UserDTO>>  findUserDto(@RequestBody Crm_UserDTO crmUser){
        return ResponseEntity.ok(userService.findUserDto(crmUser)) ;
    }

    @PostMapping("/register-user")
    public ResponseEntity<CrmUser>  registerUser(@RequestBody RegisterUserDto registerUserDto){
        CrmUser user = userService.registerUser(registerUserDto, registerUserDto.getPassword());
        return ResponseEntity.ok(user);
    };

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<CrmUser>  updateCrmUser(@PathVariable("userId") Long userId, @RequestBody CrmUser crmUser){
        return ResponseEntity.ok(userService.updateCrmUser(userId, crmUser)) ;
    };

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<CrmUser>>  findByCrmUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findByCrmUserId(userId)) ;
    }

    @GetMapping("/user-detail/{userId}")
    public ResponseEntity<Crm_UserDTO>  getUserDetail(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserDetail(userId)) ;
    };

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<CrmUser>  changePassword(@PathVariable Long userId, @RequestBody String newPassWord){
        return ResponseEntity.ok(userService.changePassword(userId,newPassWord)) ;
    };

    @PostMapping("/find-user-by-func/{funcId}")
    public ResponseEntity<Set<Crm_UserDTO>>  findUserByFunc(@PathVariable Long funcId){
        return ResponseEntity.ok(userService.findUserByFunc(funcId));
    };
}
