package com.fis.crm.web.rest;

import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.UserRoleService;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.dto.UserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private final UserRoleService userRoleService;
    private final ActionLogService actionLogService;

    public UserRoleResource(UserRoleService userRoleService, ActionLogService actionLogService) {
        this.userRoleService = userRoleService;
        this.actionLogService = actionLogService;
    }

    /**
     * Danh sach user nhom quyen
     * @param userRoleDTO
     * @param pageable
     * @return
     */
    @PostMapping("/user-role/role")
    public ResponseEntity<List<UserDTO>> getUserRole(@RequestBody UserRoleDTO userRoleDTO, Pageable pageable) {
        Page<UserDTO> page = userRoleService.getUserRole(userRoleDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Them danh sach user vao Nhom quyen
     * @param userRoleDTO
     * @return
     */
    @PostMapping("/user-role")
    public ResponseEntity<Boolean> createUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        Boolean result = userRoleService.createUserRole(userRoleDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     * Xoa user nhom quyen
     * @param userRoleDTO
     * @return
     */
    @PutMapping("/user-role")
    public ResponseEntity<Boolean> removeUserRole(@RequestBody UserRoleDTO userRoleDTO) {
        Boolean result = userRoleService.removeUserInRole(userRoleDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/user-role/get-by-user/{id}")
    public ResponseEntity<List<String>> getByUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok().body(userRoleService.getByUser(userId));
    }
}
