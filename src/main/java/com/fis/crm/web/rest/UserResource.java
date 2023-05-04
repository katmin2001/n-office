package com.fis.crm.web.rest;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.AuthoritiesConstants;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.AuthorityService;
import com.fis.crm.service.MailService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.UserMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.EmailAlreadyUsedException;
import com.fis.crm.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link User} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api/admin")
public class UserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${rocket-chat.url}")
    private String rocketChatUrl;

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;

    private final AuthorityService authorityService;

    private final UserMapper userMapper;

    public UserResource(UserService userService, UserRepository userRepository,
                        MailService mailService,AuthorityService authorityService,
                        UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.authorityService = authorityService;
        this.userMapper=userMapper;
    }

    /**
     * {@code POST  /admin/users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MessageResponse<User>> createUser(@Valid @RequestBody AdminUserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toUpperCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userDTO.getEmail() != null && userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser;
            try {
                newUser = userService.createUser(userDTO);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
            }
            mailService.sendCreationEmail(newUser);
            return ResponseEntity
                .created(new URI("/api/admin/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
                .body(new MessageResponse<>(null, newUser));
        }
    }

    /**
     * {@code PUT /admin/users} : Updates an existing User.
     *
     * @param userDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
     */
    @PutMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminUserDTO> updateUser(@PathVariable(name = "login") String login,
                                                   @Valid @RequestBody AdminUserDTO userDTO) {
//        userDTO.setId(id);
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser;
        if (userDTO.getEmail() != null) {
            existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
            if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
                throw new EmailAlreadyUsedException();
            }
        }
        Optional<AdminUserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin())
        );
    }

    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(@RequestParam(name = "login", required = false) String username,
                                                          @RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "email", required = false) String email,
                                                          @RequestParam(name = "departmentId", required = false) String departmentId,
                                                          @RequestParam(name = "type", required = false) String type,
                                                          @RequestParam(name = "internalNumber", required = false) String internalNumber,
                                                          @RequestParam(name = "internalNumberPassword", required = false) String internalNumberPassword,
                                                          @RequestParam(name = "serverVoice", required = false) String serverVoice,
                                                          @RequestParam(name = "connectionChannel", required = false) String connectionChannel,
                                                          @RequestParam(name = "activated", required = true) Boolean activated,
                                                          Pageable pageable) {
        log.debug("REST request to get all User for an admin");
//        if (!onlyContainsAllowedProperties(pageable)) {
//            return ResponseEntity.badRequest().build();
//        }

//        final Page<AdminUserDTO> page = userService.getAllManagedUsers(pageable);
        Page<AdminUserDTO> page = userService.findAllUser(
            username, name, email, departmentId,
            type, internalNumber, internalNumberPassword,
            serverVoice, connectionChannel, activated, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * {@code GET /admin/users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminUserDTO> getUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(AdminUserDTO::new));
    }

    /**
     * {@code DELETE /admin/users/:login} : delete the "login" User.
     *
     * @param login the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login.toUpperCase());
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", login)).build();
    }

    @PostMapping("/usersByType")
    public ResponseEntity<List<UserDTO>> getUserByType(@RequestParam String type) {
        log.debug("REST request to getUserByType User: {}", type);
        return ResponseEntity.ok().body(userService.getUserByType(type).orElse(null));
    }

    @PostMapping("/getActivatedUserByType")
    public ResponseEntity<List<UserDTO>> getActivatedUserByType(@RequestParam String type) {
        log.debug("REST request to getActivatedUserByType User: {}", type);
        return ResponseEntity.ok().body(userService.getActivatedUserByType(type));
    }

    @PostMapping("/getTDV")
    public ResponseEntity<List<UserDTO>> getTDV(@RequestParam String type) {
        log.debug("REST request to getTDV User: {}", type);
        return ResponseEntity.ok().body(userService.getTDV());
    }
    @PostMapping("/getLeadTDV")
    public ResponseEntity<List<UserDTO>> getLeadTDV(@RequestParam String type) {
        log.debug("REST request to getLeadTDV User: {}", type);
        return ResponseEntity.ok().body(userService.getLeadTDV());
    }

    @PostMapping("/getTDVByRole")
    public List<UserDTO> getTDVByRole() {
        try{
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            List<UserDTO> lsLead=userService.getLeadTDV();
            //Neu la role TDV thi chi load minh TDV
            for(UserDTO l:lsLead)
            {
                if(l.getLogin().equals(user.getLogin()))
                {
                    return userService
                        .getByActivatedAndTypeOrderByLastNameAsc("1", "1");
                }
            }
            List<User> users = new ArrayList<User>();
            users.add(user);
            return userMapper.usersToUserDTOs(users);
        } catch (Exception e){
            throw new BadRequestAlertException("", "", "");
        }
    }
    @PostMapping("/checkRole")
    public String checkRole() {
        try{
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            List<UserDTO> lsLead=userService.getLeadTDV();
            //Neu la role TDV thi chi load minh TDV
            for(UserDTO l:lsLead)
            {
                if(l.getLogin().equals(user.getLogin()))
                {
                    return "1";
                }
            }
            return "0";
        } catch (Exception e){
            throw new BadRequestAlertException("", "", "");
        }
    }

    @PostMapping("/getActivatedUsers")
    public ResponseEntity<List<UserDTO>> getActivatedUsers() {
        log.debug("REST request to getActivatedUsers User: ");
        return ResponseEntity.ok().body(userService.getActivatedUsers());
    }

    @PutMapping("/users/{login}/changePassword")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<AdminUserDTO> changePassword(@PathVariable(name = "login") String login,
                                                       @Valid @RequestBody AdminUserDTO userDTO) {
//        userDTO.setId(id);
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser;
        //Kiem tra ume
        if (userDTO.getEmail() != null) {
            existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
            if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
                throw new EmailAlreadyUsedException();
            }
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toUpperCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<AdminUserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(
            updatedUser,
            HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin())
        );
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<String>> getAllAuthorities(){
        return new ResponseEntity<>(authorityService.getAllAuthorities(), HttpStatus.OK);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        Optional<List<UserDTO>> allUser = userService.findAllUser();
        return ResponseEntity.ok().body(allUser.orElse(null));
    }

    @GetMapping("/getOneUserAll")
    public ResponseEntity<MessageResponse> getOneUserAll(){
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.get().getLogin();
        UserDTO userDTO = userService.findOneByLoginDTO(login);
        return new ResponseEntity<>(new MessageResponse<>("message", userDTO), HttpStatus.OK);
    }

    @GetMapping("/getTokenLoginChat")
    public ResponseEntity<MessageResponse> getTokenLoginChat(){
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.get().getLogin();
        UserDTO userDTO = userService.findOneByLoginDTO(login);

        RestTemplate restTemplate = new RestTemplate();
        ChatRequest body = new ChatRequest();
        body.setUser(userDTO.getLogin());
        body.setPassword(userDTO.getExtendVal());
        HttpEntity request = new HttpEntity(body);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(rocketChatUrl, HttpMethod.POST, request, ChatResponse.class);

        return new ResponseEntity<>(new MessageResponse<>("message", response), HttpStatus.OK);
    }
}
