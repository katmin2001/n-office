package com.fis.crm.crm_controller;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import com.fis.crm.crm_entity.DTO.RegisterUserDto;
import com.fis.crm.crm_entity.DTO.Result;
import com.fis.crm.crm_service.CrmUserService;
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
    CrmUserService userService;


    @GetMapping("/get-all-users")
    public ResponseEntity<Result>  findAllUserDto(){
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result("OK","Tìm kiếm thành công",userService.findAllUserDto()));
    };

    @PostMapping("/find-user")
    public ResponseEntity<Result>  findUserDto(@RequestBody Crm_UserDTO crmUser){
        List<Crm_UserDTO> list = userService.findUserDto(crmUser);
        if (list.size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result("NOT_FOUND","Không tồn tại đối tượng cần tìm",""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result("OK","Tìm kiếm thành công",list));
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
    public ResponseEntity<Result>  updateCrmUser(@PathVariable("userId") Long userId, @RequestBody CrmUser crmUser){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result("OK", "Update thành công",userService.updateCrmUser(userId, crmUser)));
    };

    @GetMapping("/{userId}")
    public ResponseEntity<Result>  findByCrmUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK", " Tìm kiếm thành công",userService.findByCrmUserId(userId)));
    }

    @GetMapping("/user-detail/{userId}")
    public ResponseEntity<Result>  getUserDetail(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK", " Tìm kiếm thành công",userService.getUserDetail(userId)));
    };

    @PostMapping("/find-user-by-func/{funcId}")
    public ResponseEntity<Result>  findUserByFunc(@PathVariable Long funcId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK", " Tìm kiếm thành công",userService.findUserByFunc(funcId)));
    };

    @GetMapping("/find-by-roleid/{roleId}")
    public ResponseEntity<Result> findCrmUserDtoByRoleId(@PathVariable Long roleId){
        return ResponseEntity.status(HttpStatus.OK)
            .body(new Result("OK", " Tìm kiếm thành công",userService.findCrmUserDtoByRoleId(roleId)));
    };
}
