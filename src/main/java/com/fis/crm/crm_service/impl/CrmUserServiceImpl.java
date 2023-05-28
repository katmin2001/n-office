package com.fis.crm.crm_service.impl;

import com.fis.crm.crm_entity.CrmRole;
import com.fis.crm.crm_entity.CrmRoleFunction;
import com.fis.crm.crm_entity.CrmUser;
import com.fis.crm.crm_entity.CrmUserRole;
import com.fis.crm.crm_entity.DTO.CrmUserDTO;
import com.fis.crm.crm_repository.CrmRoleFuncRepo;
import com.fis.crm.crm_repository.CrmRoleRepo;
import com.fis.crm.crm_repository.CrmUserRepo;
import com.fis.crm.crm_repository.CrmUserRoleRepo;
import com.fis.crm.crm_service.CrmRoleFuncService;
import com.fis.crm.crm_service.CrmUserService;
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
public class CrmUserServiceImpl implements CrmUserService {
    @Autowired
    CrmUserRepo IUserRepo;
    @Autowired
    CrmRoleFuncRepo roleFuncRepo;
    @Autowired
    CrmUserRoleRepo userRoleRepo;
    @Qualifier("crmRoleFuncServiceImpl")
    @Autowired
    CrmRoleFuncService roleFuncService;
    @Autowired
    CrmRoleRepo roleRepo;
    @Autowired
    CrmUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(CrmUserServiceImpl.class);
    private final DtoMapper mapper = new DtoMapper();

    @Override
    public CrmUser registerUser(CrmUserDTO userDTO, String password) {
        //kiem tra username da ton tai chua
        CrmUser user = IUserRepo.findCrmUserByUsername(userDTO.getUsername());
        if (user!=null){
            log.error("User cần tạo đã tồn tại", new IllegalArgumentException());
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
        log.info("Tạo mới thành công");
        return IUserRepo.save(newUser);
    }

    @Override
    public CrmUser updateCrmUser(Long userId,CrmUser user) {
        CrmUser crmUser = IUserRepo.findCrmUserByUserid(userId);
        if (crmUser==null){
            log.error("Đối tượng cần cập nhật không tồn tại", new NullPointerException());
        }
        crmUser.setFullname(user.getFullname());
        crmUser.setAddress(user.getAddress());
        crmUser.setBirthday(user.getBirthday());
        crmUser.setCreatedate(user.getCreatedate());
        crmUser.setPhone(user.getPhone());
        crmUser.setStatus(user.getStatus());
        log.info(" Cập nhật thành công");
        return IUserRepo.save(crmUser);
    }

    @Override
    public CrmUserDTO findByCrmUserId(Long userId) {
        CrmUser crmUser = IUserRepo.findById(userId).orElseThrow(NullPointerException::new);
        return mapper.userDtoMapper(crmUser);
    }

    @Override
    public List<CrmUserDTO> findUserDto(CrmUserDTO crmUser) {
        String fullname = crmUser.getFullName();
        Date createdate = crmUser.getCreateDate();
        String phone = crmUser.getPhone();
        Date birthday = crmUser.getBirthday();
        String address = crmUser.getAddress();
        String status = crmUser.getStatus();
        List<CrmUser> crmUsers = IUserRepo.findCrmUsersByKeyword(fullname,createdate,phone,birthday,address,status);
        if (crmUsers.size()==0){
            log.error("Không tìm thấy đối tượng yêu cầu", new NullPointerException());
        }
        List<CrmUserDTO> list = new ArrayList<>();
        for (CrmUser user : crmUsers){
            list.add(mapper.userDtoMapper(user));
        }
        return list;
    }

    @Override
    public List<CrmUserDTO> findAllUserDto() {
        List<CrmUserDTO> list = new ArrayList<>();
        List<CrmUser> users = IUserRepo.findAll();
        if (users.size()==0){
            log.error("Không tìm thấy đối tượng yêu cầu", new NullPointerException());
        }
        for (CrmUser user : users){
            CrmUserDTO userDTO = mapper.userDtoMapper(user);
            list.add(userDTO);
        }
        return list;
    }

    @Override
    public CrmUserDTO getUserDetail(Long userId) {
        CrmUser user = IUserRepo.findById(userId).orElseThrow(NullPointerException::new);
        log.error("Không tìm thấy đối tượng yêu cầu");
        return mapper.userDtoMapper(user);
    }

    @Override
    public List<CrmUserDTO> findUserByFunc(Long funcId) {
//        Set<CrmUserDTO> setCrmUser = new HashSet<>();
//        //lấy ra set roleId từ funcId
//        Set<CrmRole> crmRoleSet = roleFuncService.findRoleByFunc(funcId);
//        //lấy ra roleId
//        Set<Long> setRoleId = new HashSet<>();
//        for (CrmRole role : crmRoleSet){
//            setRoleId.add(role.getRoleid());
//        }
//        //lấy ra các set user từ set roleid và thêm vào setCrmUsers
//        for (Long roleId : setRoleId){
//            Set<CrmUserDTO> crmUsers = findCrmUserDtoByRoleId(roleId);
//            setCrmUser.addAll(crmUsers);
//        }
//        //loại bỏ giá trị trùng lặp qua username
//        for(CrmUserDTO value : setCrmUser){
//            if(!setUsername.contains(value.getUsername())){
//                setUsername.add(value.getUsername());
//                set.add(value);
//            }
//        }
//        return set;
        List<CrmUserDTO> userDTOList = new ArrayList<>();
        Set<String> setUsername = new HashSet<>();
        List<CrmUserDTO> list = new ArrayList<>();
        List<CrmRoleFunction> roleFunctions = roleFuncRepo.findCrmRoleFunctionsByFunctionId(funcId);
        if (roleFunctions.size()==0){
            log.error("Không tồn tại rolefunc với funcid cho trước", new NullPointerException());
        }
        for (CrmRoleFunction value : roleFunctions){
            list.addAll(userService.findCrmUserDtoByRoleId(value.getRole().getRoleid()));
        }
        if (list.size()==0){
            log.error("Không tồn tại user với roleid cho trước", new NullPointerException());
        }
//        loại bỏ giá trị trùng lặp qua username
        for(CrmUserDTO value : list){
            if(!setUsername.contains(value.getUsername())){
                setUsername.add(value.getUsername());
                userDTOList.add(value);
            }
        }
        return userDTOList;
    }

    @Override
    public List<CrmUserDTO> findCrmUserDtoByRoleId(Long roleId) {
//        Set<CrmUserDTO> set = new HashSet<>();
//        //tạo list lấy ra toàn bộ
//        List<CrmUserRole> listUserRole = userRoleRepo.findAll();
//        //dùng vòng lặp lấy ra tất cả đối tượng trong listUserRole có roleId giống tham số
//        for (CrmUserRole userRole : listUserRole){
//            if (userRole.getRole().getRoleid().equals(roleId)){
//                set.add(mapper.userDtoMapper(userRole.getUser()));
//            }
//        }
//        return set;
        List<CrmUserRole> userRoles = userRoleRepo.findCrmUserRoleByRoleId(roleId);
        if (userRoles.size()==0){
            log.error("Không tồn tại userrole nào có role id này", new NullPointerException());
        }
        List<CrmUserDTO> userDTOS = new ArrayList<>();
        for (CrmUserRole value : userRoles){
            userDTOS.add(mapper.userDtoMapper(IUserRepo.findCrmUserByUsername(value.getUser().getUsername())));
        }
        return userDTOS;
    }

}
