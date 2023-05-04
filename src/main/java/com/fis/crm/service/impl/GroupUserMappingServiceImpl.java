package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.GroupUser;
import com.fis.crm.domain.GroupUserMapping;
import com.fis.crm.domain.GroupUser_;
import com.fis.crm.repository.GroupUserMappingRepository;
import com.fis.crm.repository.GroupUserRepository;
import com.fis.crm.repository.impl.GroupUserMappingCustomRepository;
import com.fis.crm.service.GroupUserMappingService;
import com.fis.crm.service.GroupUserService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.GroupUserDTO;
import com.fis.crm.service.dto.GroupUserMappingDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.GroupUserMapper;
import com.fis.crm.service.mapper.GroupUserMappingMapper;
import com.fis.crm.service.mapper.UserMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import io.undertow.util.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupUserMappingServiceImpl implements GroupUserMappingService {

    final UserService userService;

    final
    GroupUserRepository groupUserRepository;

    final
    GroupUserMappingRepository groupUserMappingRepository;

    final
    GroupUserMappingMapper groupUserMappingMapper;

    final
    GroupUserService groupUserService;

    final
    EntityManager entityManager;

    final
    GroupUserMapper groupUserMapper;

    final
    ActionLogServiceImpl actionLogService;

    final
    UserMapper userMapper;

    final
    GroupUserMappingCustomRepository groupUserMappingCustomRepository;


    public GroupUserMappingServiceImpl(UserService userService, GroupUserMappingRepository groupUserMappingRepository, GroupUserMappingMapper groupUserMappingMapper, GroupUserService groupUserService, EntityManager entityManager, GroupUserMapper groupUserMapper, GroupUserRepository groupUserRepository, UserMapper userMapper, GroupUserMappingCustomRepository groupUserMappingCustomRepository, ActionLogServiceImpl actionLogService) {
        this.userService = userService;
        this.groupUserMappingRepository = groupUserMappingRepository;
        this.groupUserMappingMapper = groupUserMappingMapper;
        this.groupUserService = groupUserService;
        this.entityManager = entityManager;
        this.groupUserMapper = groupUserMapper;
        this.groupUserRepository = groupUserRepository;
        this.userMapper = userMapper;
        this.groupUserMappingCustomRepository = groupUserMappingCustomRepository;
        this.actionLogService = actionLogService;
    }

    @Override
    public Long countUserInGroup(Long id) {
        return groupUserMappingRepository.countUserInGroup(id);
    }

    @Override
    public Page<GroupUserDTO> findAllGroupUser(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GroupUser> criteriaQuery = cb.createQuery(GroupUser.class);
        Root<GroupUser> root = criteriaQuery.from(GroupUser.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get(GroupUser_.STATUS), Constants.STATUS_ACTIVE));
        criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        criteriaQuery.orderBy(cb.asc(root.get(GroupUser_.NAME)));
        List<GroupUser> rs = entityManager.createQuery(criteriaQuery)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<GroupUser> rootCount = countQuery.from(GroupUser.class);
        countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<GroupUserDTO> rsDTOs = rs.stream().map(groupUserMapper::toDto).collect(Collectors.toList());
        for (GroupUserDTO groupUserDTO : rsDTOs) {
            Long numberUser = countUserInGroup(groupUserDTO.getId());
            groupUserDTO.setNumberData(numberUser);
        }
        return new PageImpl<>(rsDTOs, pageable, count);
    }

    @Override
    public GroupUserDTO createGroupUser(GroupUserDTO groupUserDTO) {
        GroupUser groupUser = groupUserMapper.toEntity(groupUserDTO);
        groupUser.setStatus(Constants.STATUS_ACTIVE);
        GroupUser groupUser1 = groupUserRepository.save(groupUser);
        Long userId = userService.getUserIdLogin();
        actionLogService.saveActionLog(userId,
            Constants.ACTION_LOG_TYPE.INSERT.toString(),
            groupUser1.getId(),
            "GROUP_USER",
            "Thêm mới nhóm gọi ra: " + groupUser1.getName(),
            Instant.now()
        );
        return groupUserMapper.toDto(groupUser1);
    }

    @Override
    public GroupUserMappingDTO addUserToGroup(GroupUserMappingDTO groupUserMappingDTO) {
        groupUserMappingDTO.setStatus(Constants.STATUS_ACTIVE);
        StringBuilder login = new StringBuilder();
        for (Long userId : groupUserMappingDTO.getUserIds()) {
            groupUserMappingDTO.setUserId(userId);
            GroupUserMapping groupUserMapping = groupUserMappingMapper.toEntity(groupUserMappingDTO);
            groupUserMappingRepository.save(groupUserMapping);
            String name = (String) groupUserMappingCustomRepository.getUserLoginById(userId).get(0);
            login.append(" ").append(name);
        }
        Long userId = userService.getUserIdLogin();
        actionLogService.saveActionLog(userId,
            Constants.ACTION_LOG_TYPE.INSERT.toString(),
            userId,
            "GROUP_USER_MAPPING",
            "Thêm mới thành viên nhóm " + groupUserMappingDTO.getGroupId() + " các " + login,
            Instant.now()
        );
        return new GroupUserMappingDTO();
    }

    @Override
    public Long isExistGroupUserName(String groupUserName) {
        return groupUserMappingRepository.countGroupUserByName(groupUserName);
    }

    @Override
    public GroupUserDTO updateGroupUser(GroupUserDTO groupUserDTO) {
        GroupUser groupUser = groupUserRepository.getOne(groupUserDTO.getId());
        groupUser.setName(groupUserDTO.getName());
        Long userId = userService.getUserIdLogin();
        actionLogService.saveActionLog(userId,
            Constants.ACTION_LOG_TYPE.UPDATE.toString(),
            groupUser.getId(),
            "GROUP_USER",
            "Sửa nhóm gọi ra: "
                + groupUser.getName() + " thành " + groupUserDTO.getName(),
            Instant.now());
        return groupUserMapper.toDto(groupUser);
    }

    @Override
    public List<UserDTO> findAllUserNotInGroup(Long groupId) {
        return groupUserMappingCustomRepository.findAllUserNotInGroup(groupId);
    }

    @Override
    public List<UserDTO> findAllUserInGroup(Long groupId) {
        return groupUserMappingCustomRepository.findAllUserInGroup(groupId);
    }

    @Override
    public ResponseEntity<?> removeUserFromGroup(Long userId, Long groupId) throws BadRequestException {
        List<Long> campaignList = groupUserMappingRepository.getCampaignListOfUser(userId, groupId);
        if (campaignList.size() > 0) {
            String result = "Thành viên chưa bàn giao nhóm dữ liệu mới chiến dịch ";
            for(Long c:campaignList){
                if(campaignList.indexOf(c)==0) {
                    result= result.concat(c+"");
                }else{
                    result = result.concat(", "+c);
                }
            }
            result = result.concat(". Không thể xoá");
            throw new BadRequestException(result);
        }
        GroupUserMapping groupUserMapping = groupUserMappingRepository.findByGroupIdAndUserId(groupId, userId);
        groupUserMappingRepository.deleteById(groupUserMapping.getId());
        Long currentUser = userService.getUserIdLogin();
        GroupUserDTO groupUserDTO = groupUserService.findOne(groupId).get();
        String name = (String) groupUserMappingCustomRepository.getUserLoginById(userId).get(0);
        actionLogService.saveActionLog(currentUser,
            Constants.ACTION_LOG_TYPE.UPDATE.toString(),
            groupUserMapping.getId(),
            "GROUP_USER_MAPPING",
            "Xoá thành viên khỏi nhóm: "
                + groupUserDTO.getName() + ": " + name,
            Instant.now());
        return ResponseEntity.ok().body(null);
    }

    @Override
    public List<Long> countCampaignUseGroup(Long groupId) {
        return groupUserMappingRepository.getCampaignUseGroup(groupId);
    }

}
