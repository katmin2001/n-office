package com.fis.crm.web.rest;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.RoleService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.response.UpdateUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;

    private final RoleService roleService;

    private final ActionLogService actionLogService;

    public PublicUserResource(UserService userService, RoleService roleService, ActionLogService actionLogService) {
        this.userService = userService;
        this.roleService = roleService;
        this.actionLogService = actionLogService;
    }

    /**
     * {@code GET /users} : get all users with only the public informations - calling this are allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    @PutMapping(value = "/users", params = "userId")
    public ResponseEntity<UpdateUserResponse> editUser(@RequestBody UserDTO userDTO, HttpServletRequest httpServletRequest) {
        log.debug("REST request to get all public User names");
        try {
            Long id = Long.parseLong(httpServletRequest.getParameter("userId"));
            userDTO.setId(id);
            boolean res = userService.editPublicUsers(userDTO, id);
            if (res == true) {
                UpdateUserResponse updateUserResponse = new UpdateUserResponse();
                updateUserResponse.setMsgCode("200");
                updateUserResponse.setMessage("Success");
                String lst = "Cấp quyền:";
                String lst2 = "Thu quyền:";
                if (userDTO.isCreateTicket()){
                    lst += " Tiếp nhận sự vụ,";
                } else {
                    lst2 += " Tiếp nhận sự vụ,";
                }
                if (userDTO.isProcessTicket()){
                    lst += " Xử lý,";
                } else {
                    lst2 += " Xử lý,";
                }
                if (userDTO.isConfirmTicket()){
                    lst += " Xác nhận khách hàng";
                } else {
                    lst2 += " Xác nhận khách hàng";
                }
                Optional<User> userLog = userService.getUserWithAuthorities();
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    null, String.format("Thay đổi Phân quyền VOC: [%s]-[%s - %s]", userDTO.getFullName(), lst, lst2),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.configVOC, "CONFIG_MENU_ITEM"));
                return ResponseEntity.ok().body(updateUserResponse);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "/users/query")
    public ResponseEntity<List<UserDTO>> getUserByKeyword(@RequestBody UserDTO userRequest, Pageable pageable) throws Exception {
        log.debug("REST request to get User By keyword");
        Page<UserDTO> userDTO = userService.searchUser(userRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), userDTO);
        return ResponseEntity.ok().headers(headers).body(userDTO.getContent());
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @GetMapping("/authorities")
    public ResponseEntity<List<RoleDTO>> getAuthorities() {
        return new ResponseEntity<>(roleService.getAllRoleGroups(), HttpStatus.OK);
    }

    @GetMapping("/users/{username}/authorities")
    public ResponseEntity<MessageResponse<List<RoleDTO>>> getRoleGroupsOfUser(@PathVariable("username") String username) {
        try {
            List<RoleDTO> dtos = roleService.getRoleGroupsOfUser(username);
            return new ResponseEntity<>(new MessageResponse<>(null, dtos), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{username}/authorities")
    public ResponseEntity<MessageResponse<String>> updateRoleGroupsOfUser(@PathVariable("username") String username,
                                                                                 @RequestBody RoleGroupsDTO roles) {
        try {
            roleService.updateRoleGroupsOfUser(roles, username);
            return new ResponseEntity<>(new MessageResponse<>(null, null), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
