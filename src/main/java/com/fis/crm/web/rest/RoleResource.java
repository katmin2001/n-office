package com.fis.crm.web.rest;

import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.RoleService;
import com.fis.crm.service.dto.RoleDTO;
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
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private final RoleService roleService;
    private final ActionLogService actionLogService;


    public RoleResource(RoleService roleService, ActionLogService actionLogService) {
        this.roleService = roleService;
        this.actionLogService = actionLogService;
    }

    /**
     * Danh sach nhom quyen he thong
     * @param roleDTO
     * @param pageable
     * @return
     */
    @PostMapping("/role/search")
    public ResponseEntity<List<RoleDTO>> getRole(@RequestBody RoleDTO roleDTO, Pageable pageable) {
        Page<RoleDTO> page = roleService.getRole(roleDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Tao nhom quyen he thong
     * @param roleDTO
     * @return
     */
    @PostMapping("/role")
    public ResponseEntity<Boolean> createRole(@RequestBody RoleDTO roleDTO) {
        boolean result = roleService.createRole(roleDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     * Update nhom quyen
     * @param roleDTO
     * @return
     */
    @PutMapping("/role")
    public ResponseEntity<Boolean> updateRole(@RequestBody RoleDTO roleDTO) {
        boolean result = roleService.updateRole(roleDTO,  actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     * active/inactive nhom quyen
     * @param roleDTO
     * @return
     */
    @PostMapping("/role/action")
    public ResponseEntity<Boolean> actionRole(@RequestBody RoleDTO roleDTO) {
        boolean result = roleService.actionRole(roleDTO,  actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }
}
