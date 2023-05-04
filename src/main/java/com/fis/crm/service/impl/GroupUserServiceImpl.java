package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.GroupUser;
import com.fis.crm.repository.CampaignRepository;
import com.fis.crm.repository.GroupUserRepository;
import com.fis.crm.service.GroupUserService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.GroupUserDTO;
import com.fis.crm.service.mapper.GroupUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupUser}.
 */
@Service
@Transactional
public class GroupUserServiceImpl implements GroupUserService {

    private final Logger log = LoggerFactory.getLogger(GroupUserServiceImpl.class);

    private final GroupUserRepository groupUserRepository;

    private final CampaignRepository campaignRepository;

    private final GroupUserMapper groupUserMapper;

    private final ActionLogServiceImpl actionLogServiceImpl;

    final UserService userService;

    public GroupUserServiceImpl(GroupUserRepository groupUserRepository, CampaignRepository campaignRepository, GroupUserMapper groupUserMapper, ActionLogServiceImpl actionLogServiceImpl, UserService userService) {
        this.groupUserRepository = groupUserRepository;
        this.campaignRepository = campaignRepository;
        this.groupUserMapper = groupUserMapper;
        this.actionLogServiceImpl = actionLogServiceImpl;
        this.userService = userService;
    }

    @Override
    public GroupUserDTO save(GroupUserDTO groupUserDTO) {
        log.debug("Request to save GroupUser : {}", groupUserDTO);
        GroupUser groupUser = groupUserMapper.toEntity(groupUserDTO);
        groupUser = groupUserRepository.save(groupUser);
        return groupUserMapper.toDto(groupUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupUsers");
        return groupUserRepository.findAll(pageable)
            .map(groupUserMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupUserDTO> findOne(Long id) {
        log.debug("Request to get GroupUser : {}", id);
        return groupUserRepository.findById(id)
            .map(groupUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupUser : {}", id);
        GroupUser groupUser = groupUserRepository.getOne(id);
        Long userId = userService.getUserIdLogin();
        actionLogServiceImpl.saveActionLog(userId, Constants.ACTION_LOG_TYPE.DELETE.toString(),
            id, "GROUP_USER",
            "Xóa nhóm gọi ra: " + groupUser.getName(), Instant.now());
        groupUserRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<GroupUserDTO>> getAllGroupUsersByCampaignId(Long campaignId) throws Exception {
        return groupUserRepository.getAllGroupUsersByCampaignId(campaignId).map(groupUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupUserDTO> getAllGroupUsersByCampaignId2(Long campaignId) throws Exception {
        List<GroupUserDTO> groupUserDTOS = new ArrayList<>();
        String spilitID = campaignRepository.listGroup(campaignId);
        String[] list = spilitID.split(",");
        for (int i = 0; i< list.length; i++){
            Long id = Long.parseLong(list[i]);
            GroupUser groupUser = groupUserRepository.getGroupUser(id);
            groupUserDTOS.add(groupUserMapper.toDto(groupUser));
        }
        return groupUserDTOS;
    }

}
