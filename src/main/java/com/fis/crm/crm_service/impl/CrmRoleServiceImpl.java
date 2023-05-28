package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.DTO.CrmRoleDTO;
import com.fis.crm.crm_repository.CrmRoleRepo;
import com.fis.crm.crm_service.CrmRoleService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrmRoleServiceImpl implements CrmRoleService {
    private CrmRoleRepo roleRepo;

    public CrmRoleServiceImpl(CrmRoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    private final Logger log = LoggerFactory.getLogger(CrmRoleServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();

    @Override
    public CrmRole registerRole(CrmRoleDTO roleDTO) {
        CrmRole crmRole = roleRepo.findCrmRoleByRolename(roleDTO.getRoleName());
        //kiểm tra xem đối tượng role tìm kiếm theo roleName có bị trùng với dữ liệu role truyền vào để tạo mới không
        if (crmRole!=null){
            log.error("Role cần tạo đã tồn tại", new IllegalArgumentException());
        }
        CrmRole role = new CrmRole();
        role.setRolename(roleDTO.getRoleName());
        log.info("Tạo mới thành công");
        return roleRepo.save(role);
    }

    @Override
    public List<CrmRoleDTO> getAllRoles() {
        List<CrmRoleDTO> list = new ArrayList<>();
        List<CrmRole> roles = roleRepo.findAll();
        if (roles.size()==0){
            log.error("Không có role nào tồn tại", new NullPointerException());
        }
        for (CrmRole role : roles){
            list.add(mapper.roleDtoMapper(role));
        }
        return list;
    }

    @Override
    public void deleteRoleByRoleName(CrmRoleDTO roleDTO) {
        CrmRole role = roleRepo.findCrmRoleByRolename(roleDTO.getRoleName());
        if (role==null){
            log.error("Không tồn tại role cần xoá", new NullPointerException());
        }
        roleRepo.delete(role);
        log.info("Xoá thành công");
    }
}
