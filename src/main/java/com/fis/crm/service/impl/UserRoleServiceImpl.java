package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Role;
import com.fis.crm.domain.User;
import com.fis.crm.domain.UserRole;
import com.fis.crm.repository.RoleRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.repository.UserRoleRepository;
import com.fis.crm.service.UserRoleService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.dto.UserRoleDTO;
import com.fis.crm.web.rest.errors.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Implementation for managing {@link UserRoleServiceImpl}.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, RoleRepository roleRepository, UserRepository userRepository, UserService userService) {
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getUserRole(UserRoleDTO userRoleDTO, Pageable pageable) {
        Page<Object[]> page = userRoleRepository.findAllByRoleIdAndSearch(userRoleDTO.getRoleId(),
            DataUtil.makeLikeQuery(userRoleDTO.getSearch()), pageable);

        List<Object[]> objectLst = page.getContent();
        List<UserDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            UserDTO userDTO = new UserDTO();
            int index = -1;
            userDTO.setLogin(DataUtil.safeToString(object[++index]));
            userDTO.setFullName(DataUtil.safeToString(object[++index]));
            userDTO.setId(DataUtil.safeToLong(object[++index]));
            lstResults.add(userDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    public Boolean createUserRole(UserRoleDTO userRoleDTO, Consumer consumer) {
        Role role = roleRepository.findById(userRoleDTO.getRoleId()).orElseThrow(() ->
            new BusinessException("101", Translator.toLocale("role.not.found")));
        List<User> users = userRepository.findAllById(userRoleDTO.getLstUserId());
        if (DataUtil.isNullOrEmpty(users)) {
            throw new BusinessException("101", Translator.toLocale("role.user.not.found"));
        }
        List<UserRole> userRoles = role.getUserRoles();
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();
        for (User user : users) {
            Optional<UserRole> optionalUserRole = userRoles.stream().filter(userRole -> userRole.getUser().getId().equals(user.getId())).findFirst();
            if (optionalUserRole.isPresent()) {
                UserRole userRole = optionalUserRole.get();
                if (!Constants.STATUS_INACTIVE.equals(userRole.getStatus())) {
                    stringBuilder.append(String.format("%s-%s,", user.getId(), user.getLogin()));
                    userRole.setStatus(Constants.STATUS_ACTIVE);
                    userRole.setUpdateDatetime(date);
                    userRole.setUpdateUser(userService.getUserIdLogin());
                }
            } else {
                stringBuilder.append(String.format("%s-%s,", user.getId(), user.getLogin()));
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                userRole.setUser(user);
                userRole.setCreateDatetime(date);
                userRole.setStatus(Constants.STATUS_ACTIVE);
                userRole.setCreateUser(userService.getUserIdLogin());
                userRoles.add(userRole);
            }
        }
        role.setUserRoles(userRoles);
        roleRepository.save(role);
        if (consumer != null) {
            Long userId = userService.getUserIdLogin();
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.INSERT + "",
                role.getId(), String.format("Thêm mới người sử dụng nhóm quyền [%s]: các [%s]", role.getName(), stringBuilder), new Date(),
                Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "JHI_USER_ROLE"));
        }
        return true;
    }

    @Override
    public boolean removeUserInRole(UserRoleDTO userRoleDTO, Consumer consumer) {
        Role role = roleRepository.findById(userRoleDTO.getRoleId()).orElseThrow(() ->
            new BusinessException("101", Translator.toLocale("role.not.found")));
        if (DataUtil.isNullOrEmpty(userRoleDTO.getLstUserRoleId())) {
            throw new BusinessException("101", Translator.toLocale("role.users.not.found"));
        }
        List<UserRole> userRoles = role.getUserRoles();
        if (DataUtil.isNullOrEmpty(userRoles)) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        userRoles.forEach(userRole -> {
            if (userRoleDTO.getLstUserRoleId().contains(userRole.getId())) {
                stringBuilder.append(String.format("%s, ", userRole.getUser().getLogin()));
                userRole.setStatus(Constants.STATUS_INACTIVE_STR);
                userRole.setUpdateDatetime(new Date());
                userRole.setUpdateUser(userService.getUserIdLogin());
            }
        });
        roleRepository.save(role);
        if (consumer != null) {
            Long userId = userService.getUserIdLogin();
            consumer.accept(new ActionLogDTO(userId, Constants.ACTION_LOG_TYPE.INSERT + "",
                role.getId(), String.format("Xoá người sử dụng nhóm quyền [%s]: [%s]", role.getName(), stringBuilder), new Date(),
                Constants.MENU_ID.SYSTEM_MANAGEMENT, Constants.MENU_ITEM_ID.MANAGEMENT_ROLE, "JHI_USER_ROLE"));
        }
        return true;
    }

    @Override
    public void updateUserRole(User user, Role role, String status) {
        List<UserRole> userRoles = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());
        if (userRoles == null || userRoles.size() == 0) {
            UserRole ur = new UserRole();
            ur.setUser(user);
            ur.setRole(role);
            ur.setCreateUser(userService.getUserIdLogin());
            ur.setCreateDatetime(new Date());
            ur.setStatus("1");
            userRoleRepository.save(ur);
        } else {
            UserRole ur = userRoles.get(0);
            ur.setRole(role);
            ur.setUpdateUser(userService.getUserIdLogin());
            ur.setUpdateDatetime(new Date());
            ur.setStatus(status);
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
//        userRoleRepository.deleteUserRoleByRoleIdAndUserId(roleId, userId);
        userRoleRepository.deleteByUserId(userId);
    }

    @Override
    public List<String> getByUser(Long userId) {
        List<UserRole> userRoleList = userRoleRepository.findByUserIdAndStatus(userId, Constants.STATUS_ACTIVE);
        if (!userRoleList.isEmpty()) {
            StringBuilder roles = new StringBuilder("");
            for (UserRole userRole : userRoleList) {
                roles.append(userRole.getRole().getName().toUpperCase() + ", ");
            }
            roles.deleteCharAt(roles.length()-2);
            roles.deleteCharAt(roles.length()-1);
            List<String> result = new ArrayList<>();
            result.add(roles.toString());
            return result;
        }
        return null;
    }
}
