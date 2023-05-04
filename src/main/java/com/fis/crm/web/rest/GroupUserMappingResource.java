package com.fis.crm.web.rest;

import com.fis.crm.service.GroupUserMappingService;
import com.fis.crm.service.dto.GroupUserDTO;
import com.fis.crm.service.dto.GroupUserMappingDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping(value = "/api/group-user-mapping")
public class GroupUserMappingResource {

    final
    GroupUserMappingService groupUserMappingService;

    public GroupUserMappingResource(GroupUserMappingService groupUserMappingService) {
        this.groupUserMappingService = groupUserMappingService;
    }

    @GetMapping("find-all-group-user")
    public ResponseEntity<List<GroupUserDTO>> findAllGroupUser(Pageable pageable) {
        Page<GroupUserDTO> page = groupUserMappingService.findAllGroupUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("find-user-not-in-group")
    public ResponseEntity<List<UserDTO>> findAllUserNotInGroup(@RequestParam("groupId") Long groupId) {
        return ResponseEntity.ok().body(groupUserMappingService.findAllUserNotInGroup(groupId));
    }

    @GetMapping("find-user-in-group")
    public ResponseEntity<List<UserDTO>> findAllUserInGroup(@RequestParam("groupId") Long groupId) {
        return ResponseEntity.ok().body(groupUserMappingService.findAllUserInGroup(groupId));
    }

    @PostMapping("create-group-user")
    public ResponseEntity<GroupUserDTO> createGroupUSer(@RequestBody GroupUserDTO groupUserDTO) {
        try {
            if (groupUserMappingService.isExistGroupUserName(groupUserDTO.getName().trim()) != 0) {
                throw new BadRequestAlertException("Thông tin đã được sử dụng, vui lòng kiểm tra lại", "", "");
            }
            return ResponseEntity.ok().body(groupUserMappingService.createGroupUser(groupUserDTO));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @PostMapping
    public ResponseEntity<?> addUSerToGroup(@RequestBody GroupUserMappingDTO groupUserMappingDTO) {
        try {
            return ResponseEntity.ok().body(groupUserMappingService.addUserToGroup(groupUserMappingDTO));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }


    @PutMapping("update-group-user/{id}")
    public ResponseEntity<GroupUserDTO> updateGroupUSer(@PathVariable(value = "id") Long id, @RequestBody GroupUserDTO groupUserDTO) {
        try {
            if (groupUserMappingService.isExistGroupUserName(groupUserDTO.getName()) != 0) {
                throw new BadRequestAlertException("Tên nhóm đã tồn tại", "", "");
            }
            groupUserDTO.setId(id);
            return ResponseEntity.ok().body(groupUserMappingService.updateGroupUser(groupUserDTO));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserFromGroup(@RequestParam(value = "userId") Long userId,
                                                 @RequestParam(value = "groupId") Long groupId) {
        try {
            return groupUserMappingService.removeUserFromGroup(userId, groupId);
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }
}
