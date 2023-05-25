package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserRoleDTO;
import com.fis.crm.crm_entity.DTO.RegisterUserRoleDTO;
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
import java.util.Optional;

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
    public CrmUserRole deleteUserRoleForUser(CrmUserRoleDTO userRoleDTO) {
        //tìm user theo userid lấy ra từ userroledto
        CrmUser user = userRepo.findCrmUserByUserid(userRoleDTO.getUserId());
        //tìm role theo roleid lấy ra từ userroledto
        CrmRole role = roleRepo.findCrmRoleByRoleid(userRoleDTO.getRoleId());
        //tìm userrole theo user và role
        CrmUserRole userRole = userRoleRepo.findCrmUserRoleByUserIdAndRoleId(userRoleDTO.getUserId(), userRoleDTO.getRoleDTOS().)
        if (userRole!=null){
            userRoleRepo.delete(userRole);
        }
        throw new NullPointerException();
    }

    @Override
    public CrmUserRole updateUserRole(UpdateNewRoleForUser newRoleUser) {
        CrmUserRole userRole = userRoleRepo
            .findCrmUserRoleByUserIdAndRoleName(newRoleUser.getUserId(), newRoleUser.getOldRoleName());
        if (userRole==null){
            CrmUserRole crmUserRole = new CrmUserRole();
            crmUserRole.setUser(userRepo.findCrmUserByUserid(newRoleUser.getUserId()));
            crmUserRole.setRole(roleRepo.findCrmRoleByRolename(newRoleUser.getNewRolename()));
            return userRoleRepo.save(crmUserRole);
        }else {
            userRole.setRole(roleRepo.findCrmRoleByRolename(newRoleUser.getNewRolename()));
            return userRoleRepo.save(userRole);
        }
    }

    @Override
    public String addUserRole(RegisterUserRoleDTO userRoleDTO) {
        Optional<CrmUser> user = userRepo.findById(userRoleDTO.getUserId());
        if (!user.isPresent()){
            return "User cần set role không tồn tại";
        }
            for (Long value: userRoleDTO.getRoleId()){
                CrmUserRole userRole =
                    userRoleRepo.findCrmUserRoleByUserIdAndRoleId(userRoleDTO.getUserId(), value);
                if (userRole!=null){
                    log.info( "đã tồn tại cho tài khoản có roleID = " + value);
                }else {
                    Optional<CrmRole> role = roleRepo.findById(value);
                    if (!role.isPresent()){
                        log.info( "Không tồn tại roleId = " + value);
                    }
                    CrmUserRole newUserRole = new CrmUserRole();
                    newUserRole.setUser(user.get());
                    newUserRole.setRole(role.get());
                    userRoleRepo.save(newUserRole);
                    log.info("Thêm mới thành công với userID = "  +userRoleDTO.getUserId()+" và "+ value );
                }
            }
        return " Đã thêm Role cho User";
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
