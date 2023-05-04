package com.fis.crm.web.rest;

import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.AdminUserDTO;
import com.fis.crm.service.dto.ChangePasswordDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.vm.LoginVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileResource {
    private final UserService userService;

    public ProfileResource(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/profile")
    public ResponseEntity<MessageResponse<AdminUserDTO>> updateProfile(@RequestBody AdminUserDTO userData) {
        AdminUserDTO user = userService.updateUser(userData).get();
        return new ResponseEntity<>(new MessageResponse<>("ok", user), HttpStatus.OK);
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<MessageResponse<Integer>> checkErrorLogin(@RequestBody LoginVM loginVM) {
        return new ResponseEntity<>(userService.checkErrorLogin(loginVM), HttpStatus.OK);
    }

    @PostMapping("/change-my-password")
    public ResponseEntity<MessageResponse<String>> changePassword(
        @RequestBody ChangePasswordDTO changePasswordDTO) {
        String result = userService.changeMyPassword(changePasswordDTO);
        return new ResponseEntity<>(new MessageResponse<>(result, null), HttpStatus.OK);
    }

    @PostMapping("/{login}/retrieve-my-password")
    public ResponseEntity<MessageResponse<String>> retrieveMyPassword(@PathVariable(name = "login") String login) {
        String result = userService.retrieveMyPassword(login);
        return new ResponseEntity<>(new MessageResponse<>(result, null), HttpStatus.OK);
    }

}
