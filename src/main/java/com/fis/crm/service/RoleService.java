package com.fis.crm.service;

import com.fis.crm.domain.EvaluateResultDetail;
import com.fis.crm.service.dto.RoleDTO;
import com.fis.crm.service.dto.RoleGroupsDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Service Interface for managing {@link EvaluateResultDetail}.
 */
public interface RoleService {

    Page<RoleDTO> getRole(RoleDTO roleDTO, Pageable pageable);

    boolean createRole(RoleDTO roleDTO, Consumer consumer);

    boolean actionRole(RoleDTO roleDTO, Consumer consumer);

    boolean updateRole(RoleDTO roleDTO, Consumer consumer);

    List<RoleDTO> getAllRoleGroups();

    List<RoleDTO> getRoleGroupsOfUser(String username) throws NotFoundException;

    void updateRoleGroupsOfUser(RoleGroupsDTO roles, String username) throws NotFoundException;
}
