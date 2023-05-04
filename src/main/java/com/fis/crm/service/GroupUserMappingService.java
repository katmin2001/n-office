package com.fis.crm.service;

import com.fis.crm.domain.Campaign;
import com.fis.crm.service.dto.GroupUserDTO;
import com.fis.crm.service.dto.GroupUserMappingDTO;
import com.fis.crm.service.dto.UserDTO;
import io.undertow.util.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupUserMappingService {

    Long countUserInGroup(Long id);

    Page<GroupUserDTO> findAllGroupUser(Pageable pageable);

    GroupUserDTO createGroupUser(GroupUserDTO groupUserDTO);

    GroupUserMappingDTO addUserToGroup(GroupUserMappingDTO groupUserMappingDTO);

    Long isExistGroupUserName(String groupUserName);

    GroupUserDTO updateGroupUser(GroupUserDTO groupUserDTO);

    List<UserDTO> findAllUserNotInGroup(Long groupId);

    List<UserDTO> findAllUserInGroup(Long groupId);

    ResponseEntity<?> removeUserFromGroup(Long userId, Long groupId) throws BadRequestException;

    List<Long> countCampaignUseGroup(Long groupId);
}
