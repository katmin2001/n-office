package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.Crm_UserDTO;
import com.fis.crm.crm_repository.IRoleFuncRepo;
import com.fis.crm.crm_repository.IRoleRepo;
import com.fis.crm.crm_repository.IUserRepo;
import com.fis.crm.crm_repository.IUserRoleRepo;
import com.fis.crm.crm_service.IRoleFuncService;
import com.fis.crm.crm_service.IUserService;
import com.fis.crm.crm_util.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class CrmUserServiceImpl implements IUserService {
    @Autowired
    IUserRepo IUserRepo;
    @Autowired
    IRoleFuncRepo roleFuncRepo;
    @Autowired
    IUserRoleRepo userRoleRepo;
    @Qualifier("crmRoleFuncServiceImpl")
    @Autowired
    IRoleFuncService roleFuncService;
    @Autowired
    IRoleRepo roleRepo;
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(CrmUserServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();

    @Override
    public CrmUser registerUser(Crm_UserDTO userDTO, String password) {
        //kiem tra username da ton tai chua
        CrmUser user = IUserRepo.findCrmUserByUsername(userDTO.getUsername());
        if (user!=null){
            return null;
        }
        CrmUser newUser = new CrmUser();
        String encryptPassword = passwordEncoder.encode(password);
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(encryptPassword);
        newUser.setFullname(userDTO.getFullName());
        newUser.setCreatedate(userDTO.getCreateDate());
        newUser.setPhone(userDTO.getPhone());
        newUser.setBirthday(userDTO.getBirthday());
        newUser.setAddress(userDTO.getAddress());
        newUser.setStatus(userDTO.getStatus());
        return IUserRepo.save(newUser);
    }

    @Override
    public CrmUser updateCrmUser(Long userId,CrmUser user) {
        CrmUser crmUser = IUserRepo.findCrmUserByUserid(userId);
        crmUser.setFullname(user.getFullname());
        crmUser.setAddress(user.getAddress());
        crmUser.setBirthday(user.getBirthday());
        crmUser.setCreatedate(user.getCreatedate());
        crmUser.setPhone(user.getPhone());
        crmUser.setStatus(user.getStatus());
        return IUserRepo.save(crmUser);
    }

    @Override
    public Crm_UserDTO findByCrmUserId(Long userId) {
        CrmUser crmUser = IUserRepo.findById(userId).orElseThrow(NullPointerException::new);
        return mapper.userDtoMapper(crmUser);
    }

    @Override
    public List<Crm_UserDTO> findUserDto(Crm_UserDTO crmUser) {
        String fullname = crmUser.getFullName();
        Date createdate = crmUser.getCreateDate();
        String phone = crmUser.getPhone();
        Date birthday = crmUser.getBirthday();
        String address = crmUser.getAddress();
        String status = crmUser.getStatus();
        List<CrmUser> crmUsers = IUserRepo.findCrmUsersByKeyword(fullname,createdate,phone,birthday,address,status);
        List<Crm_UserDTO> list = new ArrayList<>();
        for (CrmUser user : crmUsers){
            list.add(mapper.userDtoMapper(user));
        }
        return list;
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
        CrmUser user = IUserRepo.findById(userId).orElseThrow(NullPointerException::new);
        return mapper.userDtoMapper(user);
    }

    @Override
    public Set<Crm_UserDTO> findUserByFunc(Long funcId) {
        Set<Crm_UserDTO> setCrmUser = new HashSet<>();
        Set<Crm_UserDTO> set = new HashSet<>();
        Set<String> setUsername = new HashSet<>();
        //lấy ra set roleId từ funcId
        Set<CrmRole> crmRoleSet = roleFuncService.findRoleByFunc(funcId);
        //lấy ra roleId
        Set<Long> setRoleId = new HashSet<>();
        for (CrmRole role : crmRoleSet){
            setRoleId.add(role.getRoleid());
        }
        //lấy ra các set user từ set roleid và thêm vào setCrmUsers
        for (Long roleId : setRoleId){
            Set<Crm_UserDTO> crmUsers = findCrmUserDtoByRoleId(roleId);
            setCrmUser.addAll(crmUsers);
        }
        //loại bỏ giá trị trùng lặp qua username
        for(Crm_UserDTO value : setCrmUser){
            if(!setUsername.contains(value.getUsername())){
                setUsername.add(value.getUsername());
                set.add(value);
            }
        }
        return set;
    }

    @Override
    public Set<Crm_UserDTO> findCrmUserDtoByRoleId(Long roleId) {
        Set<Crm_UserDTO> set = new HashSet<>();
        //tạo list lấy ra toàn bộ
        List<CrmUserRole> listUserRole = userRoleRepo.findAll();
        //dùng vòng lặp lấy ra tất cả đối tượng trong listUserRole có roleId giống tham số
        for (CrmUserRole userRole : listUserRole){
            if (userRole.getRole().getRoleid().equals(roleId)){
                set.add(mapper.userDtoMapper(userRole.getUser()));
            }
        }
        return set;
    }

}
