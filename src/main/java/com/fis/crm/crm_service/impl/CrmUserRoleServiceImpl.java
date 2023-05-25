package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.*;
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
    public void deleteUserRoleForUser(DeleteUserRoleDTO userRoleDTO) {
        if (userRoleDTO.getUserName().isEmpty()){
            log.info("Username cần xoá role bị bỏ trống");
            throw new NullPointerException();
        }
        if (userRoleDTO.getRoleName()==null || userRoleDTO.getRoleName().size() == 0){
            log.info("Role cần xoá bị bỏ trống");
            throw new NullPointerException();
        }
        for (String roleName : userRoleDTO.getRoleName()){
            CrmUserRole userRole = userRoleRepo.findCrmUserRoleByUserNameAndRoleName(userRoleDTO.getUserName(),roleName);
            if(userRole==null){
                log.info("Không tồn tại userRole "+roleName+" cần xoá");
            }else {
                userRoleRepo.delete(userRole);
                log.info("Xoá thành công role : "+roleName);
            }
        }
    }

    @Override
    public CrmUserRole updateUserRole(UpdateNewRoleForUser newRoleUser) {
        if (newRoleUser.getUserId()==null){
            log.info("UserID cần cập nhật role bị bỏ trống");
            throw new NullPointerException();
        }
        if (newRoleUser.getOldRoleName().isEmpty()){
            log.info("Role cần cập nhật bị bỏ trống");
            throw new NullPointerException();
        }
        if (newRoleUser.getNewRolename().isEmpty()){
            log.info("Role mới bị bỏ trống");
            throw new NullPointerException();
        }
        CrmUserRole userRole = userRoleRepo
            .findCrmUserRoleByUserIdAndRoleName(newRoleUser.getUserId(), newRoleUser.getOldRoleName());
        if (userRole==null){
            CrmUserRole crmUserRole = new CrmUserRole();
            crmUserRole.setUser(userRepo.findCrmUserByUserid(newRoleUser.getUserId()));
            crmUserRole.setRole(roleRepo.findCrmRoleByRolename(newRoleUser.getNewRolename()));
            log.info("Role cho user cần cập nhập không tồn tại , đã thêm mới role cho user");
            return userRoleRepo.save(crmUserRole);
        }else {
            userRole.setRole(roleRepo.findCrmRoleByRolename(newRoleUser.getNewRolename()));
            log.info("Cập nhật role mới thành công");
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
        List<CrmUserRole> userRoles = userRoleRepo.findCrmUserRoleByUserId(userId);
        if (userRoles.size()==0){
            log.info("Không tìm thấy role nào cho user này");
            throw new NullPointerException();
        }
        List<CrmUserRoleDTO> list = new ArrayList<>();
        for (CrmUserRole value : userRoles){
            list.add(mapper.userRoleDTOMapper(value));
        }
        return list;
    }
}
