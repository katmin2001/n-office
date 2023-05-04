package com.fis.crm.service;

import com.fis.crm.commons.*;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.ApDomainRepository;
import com.fis.crm.repository.AuthorityRepository;
import com.fis.crm.repository.CampaignEmailBatchRepository;
import com.fis.crm.repository.DepartmentRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.AuthoritiesConstants;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.UserMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.vm.LoginVM;
import io.undertow.util.BadRequestException;
import org.h2.api.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tech.jhipster.security.RandomUtil;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    @Value("${3cx.authorization}")
    private String authorization;


    private String url="https://email-fis.ringbot.co:8050/pbx/UpdateUser";

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final UserMapper userMapper;

    private final DepartmentRepository departmentRepository;

    private final EntityManager entityManager;

    private final CurrentUser currentUser;

    private final CampaignEmailBatchRepository campaignEmailBatchRepository;

    private final ApDomainRepository apDomainRepository;

    private  final  SendAutoEmailAndSMSService sendAutoEmailAndSMSService;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager,
        UserMapper userMapper,
        DepartmentRepository departmentRepository,
        CurrentUser currentUser,
        CampaignEmailBatchRepository campaignEmailBatchRepository,
        EntityManager entityManager,
        ApDomainRepository apDomainRepository,
        SendAutoEmailAndSMSService sendAutoEmailAndSMSService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.userMapper = userMapper;
        this.entityManager = entityManager;
        this.departmentRepository = departmentRepository;
        this.currentUser = currentUser;
        this.campaignEmailBatchRepository = campaignEmailBatchRepository;
        this.apDomainRepository = apDomainRepository;
        this.sendAutoEmailAndSMSService=sendAutoEmailAndSMSService;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository
            .findOneByActivationKey(key)
            .map(
                user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    this.clearUserCaches(user);
                    log.debug("Activated user: {}", user);
                    return user;
                }
            );
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(
                user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                }
            );
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(User::isActivated)
            .map(
                user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    this.clearUserCaches(user);
                    return user;
                }
            );
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toUpperCase())
            .ifPresent(
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                }
            );
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(
                existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                }
            );
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toUpperCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(AdminUserDTO userDTO) throws BadRequestException {
        User user = new User();
        user.setLogin(userDTO.getLogin().toUpperCase());
        user.setFirstName(userDTO.getFirstName());
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
//        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        if (userDTO.getPassword() == null) {
            throw new NullPointerException("Password must not be null");
        }else{
            String checkNewPassword = this.checkPassword(userDTO.getPassword(), userDTO.getLogin());
            if(checkNewPassword!= null){
                throw new BadRequestException(checkNewPassword);
            }
        }
        if (userDTO.getType() == null) {
            user.setType("1");
        } else {
            user.setType(userDTO.getType());
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setInternalNumber(userDTO.getInternalNumber());
        user.setInternalNumberPassword(userDTO.getInternalNumberPassword());
        user.setServerVoice(userDTO.getServerVoice());
        user.setConnectionChannel(userDTO.getConnectionChannel());
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        user.setDepartmentId(userDTO.getDepartmentId());
        user.setMaxLoginFail(0);
        user.setExtendVal(userDTO.getPassword());


        //departments
        user.setDepartments(convertToDepartmentString(userDTO.getDepartments()));

        Set<Authority> authorities = new HashSet<>();
        if (userDTO.getAuthorities() != null) {
            authorities = userDTO
                .getAuthorities()
                .stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        } else {
            Authority userRole = new Authority();
            userRole.setName(AuthoritiesConstants.USER);
//            authorities.add(userRole);
//            user.setAuthorities(authorities);
            user.getAuthorities().add(userRole);
        }

        user = userRepository.save(user);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("Authorization", authorization);

        User3cx body = new User3cx();
        body.setEmail(userDTO.getEmail());
        body.setPassword(userDTO.getPassword());
        body.setType(Constants.TYPE_3cx.CREATE);
        body.setUsername(userDTO.getLogin());
        if(body.getType().equals("1")) {
            body.setRole("user");
        }
        else
        {
            body.setRole("manager");
        }

        HttpEntity request = new HttpEntity(body, headers);

        ResponseEntity<User3cxResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, User3cxResponse.class);
        User3cxResponse res=response.getBody();
        if(res.getResult().equals("ERROR"))
        {
            throw new BadRequestException(res.getMessage());
        }
        else {
            log.debug("Create user 3cx success!");
        }

        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    private String convertToDepartmentString(String[] departmentsDto) {
        String departments = "";
        if (departmentsDto != null && departmentsDto.length > 0) {
            for (String department : departmentsDto) {
                departments = departments.concat(department + ",");
            }
            departments = departments.substring(0, departments.length() - 1);
            return departments;
        }
        return null;
    }

    public static void main (String [] str)
    {
        try
        {
            String temp="<p>Toi day</p>";
            System.out.println(temp.replace("<p>",""));
            System.out.println(temp.replace("</p>",""));
            /*
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("Authorization", "Basic a214asdfgFFAAADDD1AB1ETGSSDFAKLJHGJAL");

            User3cx body = new User3cx();
            body.setEmail("em1@gmail.com");
            body.setPassword("1");
            body.setType("create");
            body.setUsername("em1");
            body.setRole("user");

            HttpEntity request = new HttpEntity(body, headers);

            ResponseEntity<User3cxResponse> response = restTemplate.exchange("https://email-fis.ringbot.co:8050/pbx/UpdateUser", HttpMethod.POST, request, User3cxResponse.class);
            User3cxResponse res=response.getBody();
            System.out.println(res.getMessage());
            System.out.println(res.getResult());
            (
             */
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
            .of(userRepository.findOneByLogin(userDTO.getLogin().toUpperCase()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(
                user -> {
                    this.clearUserCaches(user);
                    if (userDTO.getLogin() != null) user.setLogin(userDTO.getLogin().toUpperCase());
                    if (userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
                    if (userDTO.getEmail() != null) {
                        if (userRepository.checkEmailExists(user.getId(), userDTO.getEmail()) > 0) {
                            throw new BadRequestAlertException("Thông tin đã được sử dụng, vui lòng kiểm tra lại", "", "");
                        } else {
                            user.setEmail(userDTO.getEmail().toLowerCase());
                        }
                    }
                    if (userDTO.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    }
                    if (userDTO.getType() != null) user.setType(userDTO.getType());
                    if (userDTO.getImageUrl() != null) user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    if (userDTO.getInternalNumber() != null) user.setInternalNumber(userDTO.getInternalNumber());
                    if (userDTO.getInternalNumberPassword() != null && !userDTO.getInternalNumberPassword().isEmpty())
                        user.setInternalNumberPassword(userDTO.getInternalNumberPassword());
                    if (userDTO.getServerVoice() != null) user.setServerVoice(userDTO.getServerVoice());
                    if (userDTO.getConnectionChannel() != null)
                        user.setConnectionChannel(userDTO.getConnectionChannel());
                    if (userDTO.getLangKey() != null) user.setLangKey(userDTO.getLangKey());
                    if (userDTO.getDepartmentId() != null) user.setDepartmentId(userDTO.getDepartmentId());

                    //departments
                    user.setDepartments(convertToDepartmentString(userDTO.getDepartments()));

                    if (userDTO.isActivated()) user.setActivated(true);
                    else user.setActivated(false);
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    if (userDTO.getAuthorities() != null && !userDTO.getAuthorities().isEmpty()) {
                        managedAuthorities.clear();
                        userDTO
                            .getAuthorities()
                            .stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    }

//                    RestTemplate restTemplate = new RestTemplate();
//                    HttpHeaders headers = new HttpHeaders();
//
//                    headers.setContentType(MediaType.APPLICATION_JSON);
//                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//                    headers.set("Authorization", authorization);
//
//                    User3cx body = new User3cx();
//                    body.setEmail(userDTO.getEmail());
//                    body.setPassword(user.getExtendVal());
//                    body.setType(Constants.TYPE_3cx.UPDATE);
//                    body.setUsername(userDTO.getLogin());
//                    body.setRole("user");
//
//                    HttpEntity request = new HttpEntity(body, headers);
//
//                    ResponseEntity<User3cxResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, User3cxResponse.class);
//                    User3cxResponse res=response.getBody();
//                    if(res.getResult().equals("ERROR"))
//                    {
//                        throw new BadRequestAlertException(res.getMessage(),"","");
//                    }
//                    else {
//                        log.debug("Update user 3cx success!");
//                    }

                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                }
            )
            .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login.toUpperCase())
            .ifPresent(
                user -> {

                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();

                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                    headers.set("Authorization", authorization);

                    User3cx body = new User3cx();
                    body.setEmail(user.getEmail());
                    body.setPassword(user.getPassword());
                    body.setType(Constants.TYPE_3cx.UPDATE);
                    body.setUsername(user.getLogin());
                    body.setRole("user");

                    HttpEntity request = new HttpEntity(body, headers);

                    ResponseEntity<User3cxResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, User3cxResponse.class);
                    User3cxResponse res=response.getBody();
                    if(res.getResult().equals("ERROR"))
                    {
                        throw new BadRequestAlertException(res.getMessage(),"","");
                    }
                    else {
                        log.debug("Create user 3cx success!");
                    }

                    userRepository.delete(user);
                    this.clearUserCaches(user);
                    log.debug("Deleted User: {}", user);
                }
            );
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String email, String langKey, String imageUrl) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(
                user -> {
                    user.setFirstName(firstName);
                    if (email != null) {
                        user.setEmail(email.toLowerCase());
                    }
                    user.setLangKey(langKey);
                    user.setImageUrl(imageUrl);
                    this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                }
            );
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(
                user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                }
            );
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    //@Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(
                user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                }
            );
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    public boolean editPublicUsers(UserDTO userDTO, Long id) throws Exception {
        Optional<User> userID = userRepository.findById(id);
        if (!userID.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND_1, "user not found" + userID);
        } else {
            try {
                userRepository.updateUser(userDTO.isCreateTicket(), userDTO.isProcessTicket(), userDTO.isConfirmTicket(), id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(11111, "Loi");
            }
        }
    }

    public Page<UserDTO> searchUser(UserDTO userRequest, Pageable pageable) throws Exception {
        String keyword = userRequest.getLogin();
        if (!userRequest.isCreateTicket() && !userRequest.isProcessTicket() && !userRequest.isConfirmTicket()) {
            return userRepository.findUserByKeyword(keyword, pageable).map(UserDTO::new);
        } else if (userRequest.isCreateTicket() || userRequest.isProcessTicket() || userRequest.isConfirmTicket()) {
            return userRepository.findUserByKeywordAndAnother(keyword, userRequest.isCreateTicket(), userRequest.isProcessTicket(), userRequest.isConfirmTicket(), pageable).map(UserDTO::new);
        }
        return null;
    }

    public Optional<UserDTO> findFirstById(Long id) {
        return userRepository.findFirstById(id).map(bean -> userMapper.userToUserDTO(bean));
    }

    public Optional<User> findFirstUserById(Long id) {
        return userRepository.findFirstById(id);
    }

    public Optional<List<UserDTO>> getUserByGroupId(Long groupId) {
        return userRepository.getUserByGroupId(groupId).map(bean -> userMapper.usersToUserDTOs(bean));
    }

    public Optional<User> findOneByLogin(String login) {
        return userRepository.findOneByLogin(login.toUpperCase());
    }

    public UserDTO findOneByLoginDTO(String login) {
        Optional<User> user = userRepository.findOneByLogin(login);
        UserDTO userDTO = userMapper.userToUserDTO2(user.get());
        return userDTO;
    }

    public Optional<List<UserDTO>> getUserByType(String type) {
        return userRepository.getUserByType(type).map(bean -> userMapper.usersToUserDTOs(bean));
    }

    public List<UserDTO> getActivatedUserByType(String type) {
        return userMapper.usersToUserDTOs(userRepository.getActivatedUserByType(type));
    }

    public List<UserDTO> getTDV() {
        return userMapper.usersToUserDTOs(userRepository.getTDV());
    }

    public List<UserDTO> getLeadTDV() {
        return userMapper.usersToUserDTOs(userRepository.getLeadTDV());
    }

    public List<UserDTO> getActivatedUsers() {
        return userMapper.usersToUserDTOs(userRepository.getActivatedUsers());
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> findAllUser(String username, String name, String email, String departmentId,
                                          String type, String internalNumber, String internalNumberPassword,
                                          String serverVoice, String connectionChannel, Boolean activated, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AdminUserDTO> cq = cb.createQuery(AdminUserDTO.class);
        Root<User> root = cq.from(User.class);
        List<Predicate> conditions = new ArrayList<>();
        if (username != null && !username.isEmpty())
            conditions.add(cb.like(cb.upper(root.get(User_.LOGIN)), "%" + username.toUpperCase() + "%"));
        if (name != null && !name.isEmpty()) {
            conditions.add(cb.like(cb.lower(root.get(User_.FIRST_NAME)), "%" + name.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) conditions.add(cb.like(root.get(User_.EMAIL), "%" + email + "%"));

        //search theo department
        if (departmentId != null && !departmentId.isEmpty()) {
            Predicate p1 = cb.like(root.get(User_.DEPARTMENTS), "%," + departmentId + ",%");
            Predicate p2 = cb.like(root.get(User_.DEPARTMENTS), "%," + departmentId);
            Predicate p3 = cb.like(root.get(User_.DEPARTMENTS), departmentId + ",%");
            Predicate p4 = cb.equal(root.get(User_.DEPARTMENTS), departmentId);
            conditions.add(cb.or(p1, p2, p3, p4));
        }

        if (type != null && !type.isEmpty()) conditions.add(cb.like(root.get(User_.TYPE), type));
        if (internalNumber != null && !internalNumber.isEmpty())
            conditions.add(cb.like(root.get(User_.INTERNAL_NUMBER), internalNumber));
        if (serverVoice != null && !serverVoice.isEmpty())
            conditions.add(cb.like(cb.lower(root.get(User_.SERVER_VOICE)), serverVoice));
        if (connectionChannel != null && !connectionChannel.isEmpty())
            conditions.add(cb.like(cb.lower(root.get(User_.CONNECTION_CHANNEL)), connectionChannel));
        if (activated != null) conditions.add(cb.equal(root.get(User_.ACTIVATED), activated));

        cq.select(cb.construct(AdminUserDTO.class,
            root.get(User_.ID),
            root.get(User_.LOGIN),
            root.get(User_.FIRST_NAME),
            root.get(User_.EMAIL),
            root.get(User_.IMAGE_URL),
            root.get(User_.CREATED_BY),
            root.get(User_.CREATED_DATE),
            root.get(User_.LAST_MODIFIED_BY),
            root.get(User_.LAST_MODIFIED_DATE),
            root.get(User_.DEPARTMENT_ID),
            root.get(User_.TYPE),
            root.get(User_.ACTIVATED),
            root.get(User_.INTERNAL_NUMBER),
            root.get(User_.INTERNAL_NUMBER_PASSWORD),
            root.get(User_.SERVER_VOICE),
            root.get(User_.CONNECTION_CHANNEL),
            root.get(User_.DEPARTMENTS)
        )).orderBy(cb.desc(root.get("createdDate")));
        ;
        Predicate[] p = conditions.toArray(new Predicate[0]);
        if (conditions.size() > 0) cq.where(p);

        List<AdminUserDTO> dtoList;
        if (pageable != null) {
            dtoList = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        } else {
            dtoList = entityManager.createQuery(cq)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
        }

//        dtoList.forEach(x -> {
//            if (x.getDepartmentId() != null) {
//                Department department = departmentRepository.getOne(x.getDepartmentId());
//                x.setDepartmentName(department.getDepartmentName());
//            }
//        });

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> rootCount = countQuery.from(User.class);
        if (conditions.size() > 0) countQuery.select(cb.count(rootCount)).where(p);
        else countQuery.select(cb.count(rootCount));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<AdminUserDTO> dtoPage;
        if (pageable == null) {
            dtoPage = new PageImpl<>(dtoList);
        } else {
            dtoPage = new PageImpl<AdminUserDTO>(dtoList, pageable, count);
        }
        log.info(dtoPage.toString());
        return dtoPage;
    }

    public List<UserDTO> getByActivatedAndTypeOrderByLastNameAsc(String activated, String type) {
        Optional<List<User>> optionalUsers = userRepository
            .findAllByActivatedAndType(type);
        if (optionalUsers.isPresent()) {
            List<User> users = optionalUsers.get();
            return userMapper.usersToUserDTOs(users);
        }
        return null;
    }

    public Long getUserIdLogin() {
        return SecurityUtils.getIdCurrentUserLogin().get();
    }

    @Transactional
    public MessageResponse<Integer> checkErrorLogin(LoginVM loginVM) {
        Optional<User> user = userRepository.findOneByLogin(loginVM.getUsername().toUpperCase());
        if (!user.isPresent()) {
            return new MessageResponse<>(Translator.toLocale("login.username.notExist"), 1);
        } else {
            int maxLoginFail = user.get().getMaxLoginFail();
            if (!passwordEncoder.matches(loginVM.getPassword(), user.get().getPassword())) {
                int maxLogin = userRepository.getMaxLogin();
                if(maxLoginFail< maxLogin){
                    user.get().setMaxLoginFail(maxLoginFail + 1);
                    return new MessageResponse<>(Translator.toLocale("login.password.wrong"), 2);
                }
                return new MessageResponse<>(Translator.toLocale("login.password.wrongManyTime", Integer.toString(maxLogin)), 2);
            }
        }
        user.get().setMaxLoginFail(0);
        return new MessageResponse<>(null, 0);
    }

    @Transactional
    public String changeMyPassword(ChangePasswordDTO dto) {
        User user = currentUser.getCurrentUser();
        String login = user.getLogin();
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmNewPassword();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return Translator.toLocale("password.notify.oldPasswordWrong");
//                "Old password is wrong!";
        }

        String checkNewPassword = this.checkPassword(newPassword, login);
        if(checkNewPassword!=null) return checkNewPassword;

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return Translator.toLocale("password.notify.sameOldOne");
//            "Your new password is the same as your old password";
        }
        if (!newPassword.equals(confirmPassword)) {
            return Translator.toLocale("password.notify.different");
//                "New password and confirm new password is different";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        if (user.getActiveDatetime() == null) {
            user.setActiveDatetime(Instant.now());
            return "1"; // lan dau dang nhap
        }
        return null;
    }

    private String checkPassword(String password, String username){
        if (password.length() < 6) {
            return Translator.toLocale("password.notify.invalidLength");
//                "Length of password must be more than 6";
        }
        if (password.toUpperCase().equals(username.toUpperCase())) {
            return Translator.toLocale("password.notify.sameUsername");
//                "Your new password is the same as your username";
        }

        if (!CheckCharacterUtil.hasUppercaseCharacter(password)) {
            return Translator.toLocale("password.notify.haveUppercase");
//                "Password must have at least one uppercase character";
        }

        if (!CheckCharacterUtil.hasLowercaseCharacter(password)) {
            return Translator.toLocale("password.notify.haveLowercase");
//                "Password must have at least one lowercase character";
        }

        if (!CheckCharacterUtil.hasSpecialCharacter(password)) {
            return Translator.toLocale("password.notify.haveSpecial");
//                "Password must have at least one special character";
        }

        if (!CheckCharacterUtil.hasNumber(password)) {
            return Translator.toLocale("password.notify.haveNumber");
//                "Password must have at least one number";
        }
        return null;
    }

    @Transactional
    public String retrieveMyPassword(String login) {
        Optional<User> userOptional = userRepository.findOneByLogin(login.toUpperCase());
        if (!userOptional.isPresent()) {
            return Translator.toLocale("login.username.notExist");
        } else {
            User user = userOptional.get();
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return Translator.toLocale("retrieve-password.email.null");
            }
            String newPassword = this.sendEmailToRetrievePass(user.getEmail(), login);
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        return null;
    }

    @Transactional
    public String sendEmailToRetrievePass(String userEmail, String login) {
        Optional<User> userOptional = userRepository.findOneByLogin(login.toUpperCase());
        String firstName = "";
        if (userOptional.isPresent()) {
            firstName = userOptional.get().getFirstName();
        }
        String autoGenPassword = GenCodeUtils.genPassword();
        sendAutoEmailAndSMSService.resetPass(firstName,login,autoGenPassword,userEmail,userOptional.get().getId());
        return autoGenPassword;
    }

    @Transactional(readOnly = true)
    public Optional<List<UserDTO>> findAllUser() {
        return userRepository.findAllByActivated().map(users -> userMapper.usersToUserDTOs(users));
    }

}
