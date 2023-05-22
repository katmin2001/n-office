package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import com.fis.crm.crm_entity.DTO.RegisterUserDto;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    public ResponseEntity<Result>  registerUser(@RequestBody @Valid RegisterUserDto registerUserDto,
                                                BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(
                error->errors.put(error.getField(),error.getDefaultMessage())
            );
            String msgErr ="";
            for (String key : errors.keySet()){
                msgErr += "Lỗi ở trường: " + key + ", lí do: " + errors.get(key) + "\n";
            }
            return (ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result("NOT_OK","Tạo tài khoản thất bại",msgErr)));
        }
        CrmUser user = userService.registerUser(registerUserDto, registerUserDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                                .body(new Result("OK","Tạo thành công",user));
    };

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<CrmUser>  updateCrmUser(@PathVariable("userId") Long userId, @RequestBody CrmUser crmUser){
        return ResponseEntity.ok(userService.updateCrmUser(userId, crmUser)) ;
    };

    @GetMapping("/{userId}")
    public ResponseEntity<Crm_UserDTO>  findByCrmUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userService.findByCrmUserId(userId)) ;
    }

    @GetMapping("/user-detail/{userId}")
    public ResponseEntity<Crm_UserDTO>  getUserDetail(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserDetail(userId)) ;
    };

    @PostMapping("/find-user-by-func/{funcId}")
    public ResponseEntity<Set<Crm_UserDTO>>  findUserByFunc(@PathVariable Long funcId){
        return ResponseEntity.ok(userService.findUserByFunc(funcId));
    };

    @GetMapping("/find-by-roleid/{roleId}")
    public Set<Crm_UserDTO> findCrmUserDtoByRoleId(@PathVariable Long roleId){
        return userService.findCrmUserDtoByRoleId(roleId);
    };
}
