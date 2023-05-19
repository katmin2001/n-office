package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_service.IUserService;
import com.fis.crm.crm_util.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CrmIUserServiceImpl implements IUserService {
    @Autowired
    IUserRepo IUserRepo;
    private final UserDtoMapper mapper = new UserDtoMapper();

    @Override
    public CrmUser registerUser(CrmUser user) {
        return IUserRepo.save(user);
    }

    @Override
    public CrmUser updateUser(Long userId,CrmUser user) {
        CrmUser crmUser = IUserRepo.findById(userId).orElse(null);
        if (crmUser==null){
            return null;
        }
        crmUser.setUsername(user.getUsername());
        crmUser.setFullname(user.getFullname());
        crmUser.setAddress(user.getAddress());
        crmUser.setBirthday(user.getBirthday());
        crmUser.setCreatedate(user.getCreatedate());
        crmUser.setPhone(user.getPhone());
        crmUser.setStatus(user.getStatus());
        return IUserRepo.save(crmUser);
    }

    @Override
    public List<CrmUser> findUserDto(String fullName, Date create_date, String phone, Date birthday, String address, String status, int roleId) {
        return null;
    }

    @Override
    public List<Crm_UserDTO> findAllUserDto() {
        List<Crm_UserDTO> list = new ArrayList<>();
        List<CrmUser> users = IUserRepo.findAll();
        for (CrmUser user : users){
            Crm_UserDTO userDTO = mapper.userDtoMapper(user);
            list.add(userDTO);
        }
        return list;
    }

    @Override
    public Crm_UserDTO getUserDetail(Long userId) {
        CrmUser user = IUserRepo.findById(userId).orElse(null);
        if (user==null){
            return null;
        }
        return mapper.userDtoMapper(user);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public CrmUser getUserById(Long userId) {
        return IUserRepo.findCrmUserByUserid(userId);
    }

    @Override
    public List<Crm_UserDTO> findUserByFunc(Long funcId) {
        return null;
    }

    @Override
    public CrmUserRole updateUserRole(Long userId, Long roleId) {
        return null;
    }

    @Override
    public CrmUser changePassword(Long userId, String newPassword) {
        CrmUser user = IUserRepo.findById(userId).orElse(null);
        if (user==null){
            return null;
        }
        user.setPassword(newPassword);
        return IUserRepo.save(user);
    }
}
