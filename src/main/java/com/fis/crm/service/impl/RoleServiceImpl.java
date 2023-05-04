package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.EvaluateResultDetail;
import com.fis.crm.domain.Role;
import com.fis.crm.domain.User;
import com.fis.crm.domain.UserRole;
import com.fis.crm.repository.RoleRepository;
import com.fis.crm.repository.UserRoleRepository;
import com.fis.crm.service.RoleService;
import com.fis.crm.service.UserRoleService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.RoleDTO;
import com.fis.crm.service.dto.RoleGroupsDTO;
import com.fis.crm.service.mapper.RoleMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Implementation for managing {@link EvaluateResultDetail}.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final UserRoleRepository userRoleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, UserService userService,
                           UserRoleService userRoleService,
                           UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> getRole(RoleDTO roleDTO, Pageable pageable) {
        String createDate = null;
        if (roleDTO.getCreateDatetime() != null) {
            createDate = DateUtil.dateToString(roleDTO.getCreateDatetime(), Constants.DATE_FORMAT_DDMMYYY);
        }
        Page<Object[]> page = roleRepository.getRole(DataUtil.makeLikeQuery(roleDTO.getName()), DataUtil.makeLikeQuery(roleDTO.getCreateUserName()),
            createDate,  roleDTO.getTotalUser() == null ? -1 : roleDTO.getTotalUser(), pageable);
        List<Object[]> objectLst = page.getContent();
        List<RoleDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            RoleDTO tmp = new RoleDTO();
            int index = -1;
            tmp.setId(DataUtil.safeToLong(object[++index]));
            tmp.setName(DataUtil.safeToString(object[++index]));
            tmp.setTotalUser(DataUtil.safeToLong(object[++index]));
            tmp.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            tmp.setCreateUserName(DataUtil.safeToString(object[++index]));
            tmp.setStatus(DataUtil.safeToString(object[++index]));
            lstResults.add(tmp);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    public boolean createRole(RoleDTO roleDTO, Consumer consumer) {
        validateBeforeRole(roleDTO);
        Role role = roleMapper.toEntity(roleDTO);
        Long userId = userService.getUserIdLogin();
        role.setStatus(Constants.STATUS_ACTIVE);
        Date createDatetime = new Date();
        role.setCreateDatetime(createDatetime);
        role.setCreateUser(userId);
        role = roleRepository.save(role);
        if(consumer != null) {
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.INSERT + "",
                role.getId(), String.format("Thêm mới nhóm quyền: [%s]", role.getName()), createDatetime, Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE,
                "ROLE"));
        }
        return true;
    }

    @Override
    public boolean actionRole(RoleDTO roleDTO, Consumer consumer) {
        Role role = roleRepository.findById(roleDTO.getId()).orElseThrow(() ->
            new BusinessException("101", Translator.toLocale("role.not.found")));
        String oldStatus = role.getStatus();
        if(Constants.STATUS_ACTIVE.equals(role.getStatus())) {
            role.setStatus(String.valueOf(Constants.STATUS_INACTIVE));
        } else {
            role.setStatus(Constants.STATUS_ACTIVE);
        }
        Long userId = userService.getUserIdLogin();
        Date date = new Date();
        role.setUpdateUser(userId);
        role.setUpdateDatetime(date);
        roleRepository.save(role);
        if(consumer != null) {
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.STATE_CHANGE,
                role.getId(), String.format("Chuyển trạng thái nhóm quyền [%s]: [%s] thành [%s]", role.getName(), oldStatus, role.getStatus()), date,
                Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE,"ROLE"));
        }
        return true;
    }

    @Override
    public boolean updateRole(RoleDTO roleDTO, Consumer consumer) {
        validateBeforeRole(roleDTO);
        Role role = roleRepository.findById(roleDTO.getId()).orElseThrow(()-> new BusinessException("101", Translator.toLocale("role.not.found")));
        String nameOld = role.getName();
        role.setName(roleDTO.getName());
        Long userId = userService.getUserIdLogin();
        role.setUpdateUser(userId);
        Date date = new Date();
        role.setUpdateDatetime(date);
        roleRepository.save(role);
        if(consumer != null) {
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.UPDATE+"",
                role.getId(), String.format("Cập nhật nhóm quyền: [%s] thành [%s]", nameOld,role.getName()), date, Constants.MENU_ID.SYSTEM_MANAGEMENT,
                Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "ROLE"));
        }
        return true;
    }

    @Override
    public List<RoleDTO> getAllRoleGroups() {
        List<Role> roles = roleRepository.getAllRoleGroup();
        return roleMapper.toDto(roles);

    }

    @Override
    public List<RoleDTO> getRoleGroupsOfUser(String username) throws NotFoundException {
        Optional<User> user = userService.findOneByLogin(username);
        if(!user.isPresent()){
            throw new NotFoundException(Translator.toLocale("login.username.notExist"));
        }
        List<Role> roles = roleRepository.getRoleGroupsOfUser(user.get().getId());
        return roleMapper.toDto(roles);
    }

    @Transactional
    @Override
    public void updateRoleGroupsOfUser(RoleGroupsDTO rolegroups, String username) throws NotFoundException {
        Optional<User> user = userService.findOneByLogin(username);
        if(!user.isPresent()){
            throw new NotFoundException(Translator.toLocale("login.username.notExist"));
        }
        List<UserRole> urs = userRoleRepository.findByUserId(user.get().getId());
        userRoleRepository.deleteAll(urs);
//        userRoleRepository.deleteByUserId(user.get().getId());
//        List<Role> roles = roleRepository.getRoleGroupsOfUser(user.get().getId()); //roles of user
//        List<Long> roleIds = roles.stream().map(x->x.getId()).collect(Collectors.toList()); //roles id
//        for(Role r:roles){
//            if(!rolegroups.getRoleGroups().contains(r.getId())){
//                List<UserRole> urs = userRoleRepository.findByUserIdAndRoleId(r.getId(), user.get().getId());
//                for(UserRole ur: urs){
//                    userRoleRepository.delete(ur);
//                }
//            }
//        }
        for (Long rolegroup : rolegroups.getRoleGroups()) {
            Role role = roleRepository.getOne(rolegroup);
            userRoleService.updateUserRole(user.get(),role,"1");
        }
    }

    boolean validateBeforeRole(RoleDTO roleDTO) {
        if(!StringUtils.hasLength(roleDTO.getName())) {
            throw new BusinessException("101", Translator.toLocale("role.name.empty"));
        }
        if(roleDTO.getId() == null) {
            Optional<Role> optionalRoles = roleRepository.findByName(roleDTO.getName());
            if(optionalRoles.isPresent()) {
                throw new BusinessException("101", Translator.toLocale("config.name.exist"));
            }
        }
        return true;
    }
}
