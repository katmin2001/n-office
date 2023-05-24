package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.UpdateNewRoleForUser;
import com.fis.crm.crm_repository.CrmRoleRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmUserRoleRepo;
import com.fis.crm.crm_service.CrmUserRoleService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CrmUserRoleServiceImpl implements CrmUserRoleService {
    private final CrmUserRoleRepo userRoleRepo;
    private final CrmUserRepo userRepo;
    private final CrmRoleRepo roleRepo;

    public CrmUserRoleServiceImpl(CrmUserRoleRepo userRoleRepo, CrmUserRepo userRepo, CrmRoleRepo roleRepo) {
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    private final Logger log = LoggerFactory.getLogger(CrmUserRoleServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();

    @Override
    public CrmUserRole updateUserRole(UpdateNewRoleForUser newRoleForUser) {
        //tìm user theo userid lấy ra từ userroledto
        CrmUser user = userRepo.findCrmUserByUserid(newRoleForUser.getUserId());
        //tìm role theo roleid lấy ra từ userroledto
        CrmRole role = roleRepo.findCrmRoleByRoleid(newRoleForUser.getRoleId());
        //lay ra userrole can update
        CrmUserRole userRole = userRoleRepo.findCrmUserRoleByRoleAndUser(user, role);
        if (userRole==null){
            CrmUserRole newUserRole = new CrmUserRole();
            newUserRole.setUser(user);
            newUserRole.setRole(role);
            return userRoleRepo.save(newUserRole);
        }
        CrmRole newRole = roleRepo.findCrmRoleByRoleid(newRoleForUser.getNewRoleId());
        if (newRole==null){
            throw new NullPointerException();
        }
        userRole.setRole(newRole);
        return userRoleRepo.save(userRole);
    }

    @Override
    public CrmUserRole addUserRole(CrmUserRoleDTO userRoleDTO) {
        //tìm user theo userid lấy ra từ userroledto
        CrmUser user = userRepo.findCrmUserByUserid(userRoleDTO.getUserId());
        //tìm role theo roleid lấy ra từ userroledto
        CrmRole role = roleRepo.findCrmRoleByRoleid(userRoleDTO.getRoleId());
        //tìm userrole theo user và role
        CrmUserRole userRole = userRoleRepo.findCrmUserRoleByRoleAndUser(user,role);
        //nếu userrole đã tồn tại => return báo lỗi
        if (userRole!= null){
            throw new IllegalArgumentException();
        }
        //nếu không tồn tại thì tao mới 1 đối tươnng userrole, gán dữ liệu user và role cho newuserrole và lưu
        CrmUserRole newUserRole = new CrmUserRole();
        newUserRole.setUser(user);
        newUserRole.setRole(role);
        return userRoleRepo.save(newUserRole);
    }

    @Override
    public CrmUserRole deleteUserRoleForUser(CrmUserRoleDTO userRoleDTO) {
        //tìm user theo userid lấy ra từ userroledto
        CrmUser user = userRepo.findCrmUserByUserid(userRoleDTO.getUserId());
        //tìm role theo roleid lấy ra từ userroledto
        CrmRole role = roleRepo.findCrmRoleByRoleid(userRoleDTO.getRoleId());
        //tìm userrole theo user và role
        CrmUserRole userRole = userRoleRepo.findCrmUserRoleByRoleAndUser(user,role);
        if (userRole!=null){
            userRoleRepo.delete(userRole);
        }
        throw new NullPointerException();
    }

    @Override
    public List<CrmUserRoleDTO>  getAllUserRole() {
        List<CrmUserRole> list = userRoleRepo.findAll();
        List<CrmUserRoleDTO> dtoList = new ArrayList<>();
        for (CrmUserRole value : list){
            dtoList.add(mapper.userRoleDTOMapper(value));
        }
        return dtoList;
    }

    @Override
    public List<CrmUserRoleDTO> findUserByRole(Long roleId) {
        CrmRole role = roleRepo.findCrmRoleByRoleid(roleId);
        List<CrmUserRole> userRoles = userRoleRepo.findCrmUserRoleByRole(role);
        List<CrmUserRoleDTO> list = new ArrayList<>();
        for (CrmUserRole value : userRoles){
            list.add(mapper.userRoleDTOMapper(value));
        }
        if (list.size()==0){
            throw new NullPointerException();
        }
        return list;
    }

    @Override
    public List<CrmUserRoleDTO> findRoleByUser(Long userId) {
        CrmUser user = userRepo.findCrmUserByUserid(userId);
        List<CrmUserRole> userRoles = userRoleRepo.findCrmUserRoleByUser(user);
        List<CrmUserRoleDTO> list = new ArrayList<>();
        for (CrmUserRole value : userRoles){
            list.add(mapper.userRoleDTOMapper(value));
        }
        if (list.size()==0){
            throw new NullPointerException();
        }
        return list;
    }
}
