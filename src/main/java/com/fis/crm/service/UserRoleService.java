package com.fis.crm.service;

import com.fis.crm.domain.EvaluateResultDetail;
import com.fis.crm.domain.Role;
import com.fis.crm.domain.User;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.dto.UserRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Service Interface for managing {@link EvaluateResultDetail}.
 */
public interface UserRoleService {

    Page<UserDTO> getUserRole(UserRoleDTO userRoleDTO, Pageable pageable);

    Boolean createUserRole(UserRoleDTO userRoleDTO, Consumer consumer);

    boolean removeUserInRole(UserRoleDTO userRoleDTO, Consumer consumer);

    void updateUserRole(User user, Role role, String status);

    void deleteByUserId(Long userId);

    List<String> getByUser(Long userId);
}
