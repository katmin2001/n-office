package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.*;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.*;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CustomerMapper;
import com.fis.crm.service.mapper.TicketMapper;
import com.fis.crm.service.mapper.TicketRequestAttachmentMapper;
import com.fis.crm.service.mapper.TicketRequestMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Ticket}.
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    @Autowired
    private OptionSetValueRepository optionSetValueRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConfigScheduleService configScheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TicketRequestRepository ticketRequestRepository;

    @Autowired
    private TicketRequestMapper ticketRequestMapper;

    @Autowired
    private TicketRequestAttachmentRepository ticketRequestAttachmentRepository;

    @Autowired
    private TicketRequestAttachmentMapper ticketRequestAttachmentMapper;

    @Autowired
    private ConfirmTicketService confirmTicketService;

    @Autowired
    private ConfirmTicketAttachmentService confirmTicketAttachmentService;

    private final DataSource dataSource;

    private final ActionLogService actionLogService;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, DataSource dataSource, ActionLogService actionLogService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.dataSource = dataSource;
        this.actionLogService = actionLogService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        ServiceResult result = new ServiceResult();
        Ticket ticket = new Ticket();
        List<Long> listTicketRequestIds = null;
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (user.getCreateTicket() == null || !user.getCreateTicket()) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("user.has.no.right.to.process.ticket"));
            return null;
        }

        //HieuNT : Khi co su vu roi thi khong tao KH va ticket
        if (ticketDTO.getTicketId() == null) {
            //save customer
            Long customerId = createCustomer(ticketDTO);
            //save departments to ticketDTO
            ticketDTO.setDepartments(user.getDepartments() == null ? null : Arrays.asList(user.getDepartments().split(",")));
            //============save ticket=====================
            ticket = createTicket(ticketDTO, customerId);
        } else {
            Optional<Ticket> optional = ticketRepository.findById(ticketDTO.getTicketId());
            if (optional.isPresent()) {
                ticket = optional.get();
            }
        }
        //=========save ticketRequest=============
        ticketDTO.setDepartments(user.getDepartments() == null ? null : Arrays.asList(user.getDepartments().split(",")));
        listTicketRequestIds = saveTicketRequest(ticketDTO, ticket, Constants.TICKET_STATUS_PROCESSING);
        //PhuongNH : Them doan check quyen
        updateTicketConfirmDate(ticket);
        result.setTicketId(ticket.getTicketId());
        result.setListTicketRequestIds(listTicketRequestIds);
        result.setStatus(ServiceResult.Status.SUCCESS);
        result.setMessage(Translator.toLocale("create.ticket.success"));

        Optional<TicketDTO> ticket1 = findOne(ticket.getTicketId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (ticketDTO.getTicketId() != null){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                ticketDTO.getTicketId(), String.format("Cập nhật sự vụ: [%s]", ticketDTO.getTicketCode()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                ticketDTO.getTicketId(), String.format("Tạo mới sự vụ: [%s]", ticket1.get().getTicketCode()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
        }

        return result;
    }

    private List<Long> saveTicketRequest(TicketDTO ticketDTO, Ticket ticket, String status) {
        List<Long> listTicketRequestIds = new ArrayList<>();
        System.out.println("1111111111 "+ ticketDTO.getListTicketRequestDTOS().get(0).getConfirmDate());
        System.out.println("1111111111  222  "+ ticket.getConfirmDatetime());
        List<TicketRequestDTO> listTicketRequests = ticketDTO.getListTicketRequestDTOS();
        int i=0;
        for (TicketRequestDTO ticketRequestDTO : listTicketRequests) {
            i++;
            if (ticketRequestDTO.getTicketRequestId() == null) {
                //Set lai deadline
                ConfigScheduleDTO o = new ConfigScheduleDTO();
                o.setRequestType(ticketRequestDTO.getRequestType());
                o.setBussinessType(ticketRequestDTO.getBussinessType());
                ServiceResult sResult = configScheduleService.getProcessTime(o);
                ticketRequestDTO.setDeadline(sResult.getProcessDate().toInstant());
                ticketRequestDTO.setTicketRequestCode(getTicketRequestCode(ticket.getTicketCode(),i));
                ticketRequestDTO.setTicketId(ticket.getTicketId());
                if (null == ticketRequestDTO.getRequestType()) {
                    throw new BadRequestAlertException(Translator.toLocale("request.type.is.required"), "ticketDTO", "request.type.is.required");
                }
                if (null == ticketRequestDTO.getBussinessType()) {
                    throw new BadRequestAlertException(Translator.toLocale("business.type.is.required"), "ticketDTO", "business.type.is.required");
                }
                if (null == ticketRequestDTO.getPriority()) {
                    throw new BadRequestAlertException(Translator.toLocale("prioritize.is.required"), "ticketDTO", "prioritize.is.required");
                }
                ticketRequestDTO.setStatus(status);

                if (null == ticketRequestDTO.getContent()) {
                    throw new BadRequestAlertException(Translator.toLocale("content.is.required"), "ticketDTO", "content.is.required");
                }
                if (null == ticketRequestDTO.getDeadline()) {
                    throw new BadRequestAlertException(Translator.toLocale("deadline.is.required"), "ticketDTO", "deadline.is.required");
                } else {
                    //check han xu ly phai sau thoi điem tạo
                    int value = Instant.now().compareTo(ticketRequestDTO.getDeadline());
                    if (value >= 0) {
                        throw new BadRequestAlertException(Translator.toLocale("deadline.after.createDateTime"), "ticketDTO", "deadline.after.createDateTime");
                    }
                }
                ticketRequestDTO.setCreateDatetime(new Date().toInstant());
                List<Object[]> lst = ticketRequestRepository.getMaxNo(ticket.getTicketId());
                if (lst.size() > 0) {
                    for (Object[] obj1 : lst) {
                        ticketRequestDTO.setNo(DataUtil.safeToLong(obj1[0]) + 1);
                    }
                } else {
                    ticketRequestDTO.setNo((long) 1);
                }
                TicketRequest ticketRequest = ticketRequestMapper.toEntity(ticketRequestDTO);
                ticketRequest.setCreateDepartments(convertListToString(ticketDTO.getDepartments()));
                ticketRequest = ticketRequestRepository.save(ticketRequest);
                if (lst.size() > 0) {
                    String no = "";
                    if (ticketRequestDTO.getNo() < 10) {
                        no = no + "0" + ticketRequestDTO.getNo().toString();
                    }
                    ticketRequest.setTicketRequestCode(ticket.getTicketCode() + no);
                    ticketRequest.setNo(ticketRequestDTO.getNo());
                } else {
                    ticketRequest.setTicketRequestCode(ticket.getTicketCode() + "01");
                    ticketRequest.setNo((long) 1);
                }
                ticketRequest = ticketRequestRepository.save(ticketRequest);
                listTicketRequestIds.add(ticketRequest.getTicketRequestId());
                Long ticketRequestId = ticketRequest.getTicketRequestId();
                if (ticketRequestDTO.getListTicketRequestAttachmentDTOS() != null) {
                    List<TicketRequestAttachment> lstAttach = ticketRequestDTO.getListTicketRequestAttachmentDTOS().stream()
                        .map(ticketRequestAttachmentMapper::toEntity).peek(e ->
                            e.setTicketRequestId(ticketRequestId)
                        ).collect(Collectors.toList());
                    ticketRequestAttachmentRepository.saveAll(lstAttach);
                }
                updateTicketStatus(ticketRequest.getTicketId());
            } //End ticketRequestDTO.getTicketRequestId() == null
            else {
                Optional<TicketRequest> ticketRequestOptional = ticketRequestRepository.findById(ticketRequestDTO.getTicketRequestId());
                if (ticketRequestOptional.isPresent() && (ticketDTO.getStatus().equalsIgnoreCase(Constants.STATUS_NOT_IN_PROCESS) || ticketDTO.getStatus().equalsIgnoreCase(Constants.STATUS_ACTIVE))) {
                    ticketRequestOptional.get().setRequestType(ticketRequestDTO.getRequestType());
                    ticketRequestOptional.get().setBussinessType(ticketRequestDTO.getBussinessType());
                    ConfigScheduleDTO o = new ConfigScheduleDTO();
                    o.setRequestType(ticketRequestDTO.getRequestType());
                    o.setBussinessType(ticketRequestDTO.getBussinessType());
                    ServiceResult sResult = configScheduleService.getProcessTime(o);
                    ticketRequestOptional.get().setDeadline(sResult.getProcessDate().toInstant());
                    ticketRequestOptional.get().setContent(ticketRequestDTO.getContent());
                    Optional<User> userLog = userService.getUserWithAuthorities();
                    if (userLog.isPresent()) {
                        ticketRequestOptional.get().setUpdateUser(userLog.get().getId());
                    }
                    ticketRequestOptional.get().setUpdateDatetime(Instant.now());
                    ticketRequestOptional.get().setStatus(status);
                    updateTicketStatus(ticket.getTicketId());
                    ticketRequestRepository.save(ticketRequestOptional.get());
                }
                listTicketRequestIds.add(ticketRequestDTO.getTicketRequestId());
            }
        }
        return listTicketRequestIds;
    }

    private List<Long> saveTicketRequestInactive(TicketDTO ticketDTO, Ticket ticket, String status) {
        List<Long> listTicketRequestIds = new ArrayList<>();
        System.out.println("1111111111 "+ ticketDTO.getListTicketRequestDTOS().get(0).getConfirmDate());
        System.out.println("1111111111  222  "+ ticket.getConfirmDatetime());
        List<TicketRequestDTO> listTicketRequests = ticketDTO.getListTicketRequestDTOS();
        int i=0;
        for (TicketRequestDTO ticketRequestDTO : listTicketRequests) {
            i++;
            if (ticketRequestDTO.getTicketRequestId() == null) {
                //Set lai deadline
                ConfigScheduleDTO o = new ConfigScheduleDTO();
                o.setRequestType(ticketRequestDTO.getRequestType());
                o.setBussinessType(ticketRequestDTO.getBussinessType());
                ServiceResult sResult = configScheduleService.getProcessTime(o);
                if (!DataUtil.isNullOrEmpty(sResult.getProcessDate())) {
                    ticketRequestDTO.setDeadline(sResult.getProcessDate().toInstant());
                }
                ticketRequestDTO.setTicketRequestCode(getTicketRequestCode(ticket.getTicketCode(),i));
                ticketRequestDTO.setTicketId(ticket.getTicketId());
//                if (null == ticketRequestDTO.getRequestType()) {
//                    throw new BadRequestAlertException(Translator.toLocale("request.type.is.required"), "ticketDTO", "request.type.is.required");
//                }
//                if (null == ticketRequestDTO.getBussinessType()) {
//                    throw new BadRequestAlertException(Translator.toLocale("business.type.is.required"), "ticketDTO", "business.type.is.required");
//                }
//                if (null == ticketRequestDTO.getPriority()) {
//                    throw new BadRequestAlertException(Translator.toLocale("prioritize.is.required"), "ticketDTO", "prioritize.is.required");
//                }
                ticketRequestDTO.setStatus(status);

//                if (null == ticketRequestDTO.getContent()) {
//                    throw new BadRequestAlertException(Translator.toLocale("content.is.required"), "ticketDTO", "content.is.required");
//                }
//                if (null == ticketRequestDTO.getDeadline()) {
//                    throw new BadRequestAlertException(Translator.toLocale("deadline.is.required"), "ticketDTO", "deadline.is.required");
//                } else {
//                    //check han xu ly phai sau thoi điem tạo
//                    int value = Instant.now().compareTo(ticketRequestDTO.getDeadline());
//                    if (value >= 0) {
//                        throw new BadRequestAlertException(Translator.toLocale("deadline.after.createDateTime"), "ticketDTO", "deadline.after.createDateTime");
//                    }
//                }
                ticketRequestDTO.setCreateDatetime(Instant.now());
                List<Object[]> lst = ticketRequestRepository.getMaxNo(ticket.getTicketId());
                if (lst.size() > 0) {
                    for (Object[] obj1 : lst) {
                        ticketRequestDTO.setNo(DataUtil.safeToLong(obj1[0]) + 1);
                    }
                } else {
                    ticketRequestDTO.setNo((long) 1);
                }
                TicketRequest ticketRequest = ticketRequestMapper.toEntity(ticketRequestDTO);
                ticketRequest.setCreateDepartments(convertListToString(ticketDTO.getDepartments()));
                ticketRequest = ticketRequestRepository.save(ticketRequest);
                if (lst.size() > 0) {
                    String no = "";
                    if (ticketRequestDTO.getNo() < 10) {
                        no = no + "0" + ticketRequestDTO.getNo().toString();
                    }
                    ticketRequest.setTicketRequestCode(ticket.getTicketCode() + no);
                    ticketRequest.setNo(ticketRequestDTO.getNo());
                } else {
                    ticketRequest.setTicketRequestCode(ticket.getTicketCode() + "01");
                    ticketRequest.setNo((long) 1);
                }
                ticketRequest = ticketRequestRepository.save(ticketRequest);
                listTicketRequestIds.add(ticketRequest.getTicketRequestId());
                Long ticketRequestId = ticketRequest.getTicketRequestId();
                if (ticketRequestDTO.getListTicketRequestAttachmentDTOS() != null) {
                    List<TicketRequestAttachment> lstAttach = ticketRequestDTO.getListTicketRequestAttachmentDTOS().stream()
                        .map(ticketRequestAttachmentMapper::toEntity).peek(e ->
                            e.setTicketRequestId(ticketRequestId)
                        ).collect(Collectors.toList());
                    ticketRequestAttachmentRepository.saveAll(lstAttach);
                }
//                updateTicketStatus(ticketRequest.getTicketId());
            } //End ticketRequestDTO.getTicketRequestId() == null
            else {
                listTicketRequestIds.add(ticketRequestDTO.getTicketRequestId());
            }
        }
        return listTicketRequestIds;
    }

    public void updateTicketConfirmDate(Ticket ticket) {
        String sql = "update ticket set confirm_deadline=\n" +
            "\n( select max(confirm_time) from (\n" +
            "          select (select  case when CONFIRM_TIME_TYPE=1 then CREATE_TIMESTAMP +CONFIRM_TIME/24/60\n" +
            "            when CONFIRM_TIME_TYPE=2 then CREATE_TIMESTAMP +CONFIRM_TIME/24\n" +
            "            when CONFIRM_TIME_TYPE=3 then CREATE_TIMESTAMP +CONFIRM_TIME end confirm_time\n" +
            "            from config_schedule where request_type=t.request_type and\n" +
            "            bussiness_type=t.bussiness_type and rownum=1) confirm_time from ticket_request t\n" +
            "            where t.ticket_id=:ticketId ))  where ticket_id=:ticketId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("ticketId", ticket.getTicketId());
        query.executeUpdate();
    }

    public void updateTicketConfirmDateAndStatus(Ticket ticket) {
        String sql = "update ticket set status = (select min(tr.status) from ticket_request tr" +
            " where tr.ticket_id=:ticketId), confirm_deadline=\n" +
            "\n( select max(confirm_time) from (\n" +
            "          select (select  case when CONFIRM_TIME_TYPE=1 then CREATE_TIMESTAMP +CONFIRM_TIME/24/60\n" +
            "            when CONFIRM_TIME_TYPE=2 then CREATE_TIMESTAMP +CONFIRM_TIME/24\n" +
            "            when CONFIRM_TIME_TYPE=3 then CREATE_TIMESTAMP +CONFIRM_TIME end confirm_time\n" +
            "            from config_schedule where request_type=t.request_type and\n" +
            "            bussiness_type=t.bussiness_type and rownum=1) confirm_time from ticket_request t\n" +
            "            where t.ticket_id=:ticketId ))  where ticket_id=:ticketId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("ticketId", ticket.getTicketId());
        query.executeUpdate();
    }

    @Override
    public ServiceResult saveInactive(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        ServiceResult result = new ServiceResult();
        Ticket ticket = new Ticket();
        List<Long> listTicketRequestIds = null;
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (user.getCreateTicket() == null || !user.getCreateTicket()) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("user.has.no.right.to.process.ticket"));
            return null;
        }

        //HieuNT : Khi co su vu roi thi khong tao KH va ticket
        if (ticketDTO.getTicketId() == null) {
            //save customer
            Long customerId = createCustomer(ticketDTO);
            //save departments to ticketDTO
            ticketDTO.setDepartments(user.getDepartments() == null ? null : Arrays.asList(user.getDepartments().split(",")));
            //============save ticket=====================
            ticket = createTicketInactive(ticketDTO, customerId);
        } else {
            Optional<Ticket> optional = ticketRepository.findById(ticketDTO.getTicketId());
            if (optional.isPresent()) {
                ticket = optional.get();
            }
        }
        //=========save ticketRequest=============
        ticketDTO.setDepartments(user.getDepartments() == null ? null : Arrays.asList(user.getDepartments().split(",")));
        listTicketRequestIds = saveTicketRequestInactive(ticketDTO, ticket, Constants.STATUS_ACTIVE_STR);
        //PhuongNH : Them doan check quyen
        updateTicketConfirmDate(ticket);
        result.setTicketId(ticket.getTicketId());
        result.setListTicketRequestIds(listTicketRequestIds);
        result.setStatus(ServiceResult.Status.SUCCESS);
        result.setMessage(Translator.toLocale("create.ticket.success"));

        Optional<TicketDTO> ticket1 = findOne(ticket.getTicketId());
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (ticketDTO.getTicketId() != null){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                ticketDTO.getTicketId(), String.format("Cập nhật sự vụ: [%s]", ticketDTO.getTicketCode()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                ticketDTO.getTicketId(), String.format("Tạo mới sự vụ: [%s]", ticket1.get().getTicketCode()),
                new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
        }

        return result;
    }

    /**
     *
     * @param ticket
     */
    public void updateTicketStatus(Ticket ticket) {

        String sql = "UPDATE ticket t\n" +
            "           SET status = (select min(tr.status) from \n" +
            "           ticket_request tr where tr.ticket_id=:p_ticket_id)\n" +
            "         WHERE ticket_id = :p_ticket_id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("p_ticket_id", ticket.getTicketId());
        query.executeUpdate();
    }

    private Ticket createTicket(TicketDTO ticketDTO, Long customerId) {
        ticketDTO.setCustomerId(customerId);
        ticketDTO.setStatus(Constants.TICKET_STATUS_PROCESSING);
        ticketDTO.setCreateDatetime(Instant.now());
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket.setTicketStatus("1");
        ticket.setTicketRequestStatus("1");
        ticket = ticketRepository.save(ticket);
        Date date = Calendar.getInstance().getTime();
        String code = getTicketCode();
        ticket.setTicketCode(code);
        ticket.setCreateDepartments(convertListToString(ticketDTO.getDepartments()));
        ticket = ticketRepository.save(ticket);
        return ticket;
    }

    private Ticket createTicketInactive(TicketDTO ticketDTO, Long customerId) {
        ticketDTO.setCustomerId(customerId);
        ticketDTO.setStatus(Constants.STATUS_ACTIVE_STR);
        ticketDTO.setCreateDatetime(Instant.now());
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        Date date = Calendar.getInstance().getTime();
        String code = getTicketCode();
        ticket.setTicketCode(code);
        ticket.setCreateDepartments(convertListToString(ticketDTO.getDepartments()));
        ticket = ticketRepository.save(ticket);
        return ticket;
    }

    private String getTicketCode() {
        Connection connection = null;
        String sql = "{? = call pck_util.get_ticket_code}";
        CallableStatement statement = null;
        String ticketCode = "";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.VARCHAR);
            statement.executeQuery();
            ticketCode = (String) statement.getObject(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(statement);
            DbUtils.close(connection);
        }
        return ticketCode;
    }

    private String getTicketRequestCode(String ticketCode,int i) {
        if(i<10)
            return ticketCode+"0"+i;
        return ticketCode+""+i;
    }

    private String convertListToString(List<String> a) {
        String s = "";
        if (a != null && a.size() > 0) {
            for (String x : a) {
                s = s.concat(x + ",");
            }
            s = s.substring(0, s.length() - 1);
            return s;
        }
        return null;
    }


    private Long createCustomer(TicketDTO ticketDTO) {
        Long customerId = -1L;
        if (ticketDTO.getCustomerId() == 0) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setPhoneNumber(ticketDTO.getPhoneNumber());
            customerDTO.setName(ticketDTO.getName());
            customerDTO.setEmail(ticketDTO.getEmail());
            customerDTO.setContactPhone(ticketDTO.getContactPhone());
            customerDTO.setCreateDatetime(Instant.now());
            Customer customer = customerMapper.toEntity(customerDTO);
            customer = customerRepository.save(customer);
            customerId = customer.getCustomerId();
        } else {
            customerId = ticketDTO.getCustomerId();
        }
        return customerId;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tickets");
        return ticketRepository.findAll(pageable).map(ticketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketDTO> findOne(Long id) {
        log.debug("Request to get Ticket : {}", id);
        return ticketRepository.findById(id).map(ticketMapper::toDto);
    }

    public void updateForProcess(TicketDTO ticketDTO) {
        ticketDTO.setConfirmTicketDTOS(confirmTicketService.getListHistoryConfirmTickets(ticketDTO.getTicketId()));
        ticketDTO.setConfirmTicketAttachmentDTOS(confirmTicketAttachmentService.getAllByTicketId(ticketDTO.getTicketId()));

        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (null == user.getConfirmTicket() || !user.getConfirmTicket()) {
            ticketDTO.setDisableCloseSave(true);
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ticket : {}", id);
        ticketRepository.deleteById(id);
    }

    @Override
    public Page<TicketDTO> getListHistorySupports(TicketDTO ticketDTO, Pageable pageable) {
        Page<TicketDTO> ticketDTOS = ticketRepository.getListHistorySupports(ticketDTO.getCid(), pageable).map(ticketMapper::toDto);
        List<TicketDTO> ticketDTOS1 = ticketDTOS.getContent();
        for (TicketDTO ticketDTO2 : ticketDTOS1){
            Optional<User> user = userService.findFirstUserById(ticketDTO2.getCreateUser());
            ticketDTO2.setFirstName(user.get().getFirstName());
        }
        return new PageImpl<TicketDTO>(ticketDTOS1, pageable, ticketDTOS1.size());
    }

    public Page<TicketDTO> searchTicket(RequestSearchTicketDTO requestSearchTicketDTO, Pageable pageable) {
        List<Long> status = null;
        if (requestSearchTicketDTO.getStatus() == null) {
            status = getStatus(requestSearchTicketDTO);
        } else {
            status = Arrays.asList(requestSearchTicketDTO.getStatus());
        }
        String fromDate = null;
        if (requestSearchTicketDTO.getFromDate() != null) {
            fromDate = DateUtil.dateToString(requestSearchTicketDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY);
        }
        String toDate = null;
        if (requestSearchTicketDTO.getToDate() != null) {
            toDate = DateUtil.dateToString(requestSearchTicketDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY);
        }
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        //filter by departments
        List<String> departments = (user.getDepartments() == null || user.getDepartments().isEmpty()) ? null : Arrays.asList(user.getDepartments().split(","));

        if (departments != null) {
            Boolean isCCDepartment = true;
            List<OptionSetValue> osvLst = optionSetValueRepository.findOptSetValueByOptionSetCode("PHONG_BAN").get()
                .stream().sorted(Comparator.comparingInt(OptionSetValue::getOrd)).collect(Collectors.toList());
            if(osvLst!=null && osvLst.size()>0){
                List<OptionSetValue> findCCDepartment = osvLst.stream().filter(x-> x.getCode().equals("TT_CC")).collect(Collectors.toList());
                if(findCCDepartment!=null && findCCDepartment.size()>0){
                    String ccDepartment = findCCDepartment.get(0).getId().toString();
                    if(departments.contains(ccDepartment)){
                        isCCDepartment = true;
                    }else {
                        isCCDepartment = false;
                    }
                }else{
                    isCCDepartment = false;
                }
            }else {
                isCCDepartment = false;
            }
            if(!isCCDepartment) {
                List<TicketDTO> dtos = new ArrayList<>();
                List<TicketDTO> result = new ArrayList<>();
                List<TicketDTO> ticketList = ticketRepository.searchTicket(requestSearchTicketDTO.getChannel(),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getPhone()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getEmails()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getIdCode()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getReceiveUser()),
                    fromDate,
                    toDate,
                    status,
                    departments
                ).stream().map(TicketDTO::new).collect(Collectors.toList());

                ticketList.forEach(x -> {
                    int check = 0;
                    int noDepartmentId = 0;
                    List<TicketRequest> ticketRequests = ticketRequestRepository.findByTicketId(x.getTicketId());
                    if (ticketRequests != null && ticketRequests.size() > 0) {
                        for (TicketRequest y : ticketRequests) {
                            if (y.getDepartmentId() != null && departments.contains(y.getDepartmentId().toString()))
                                check = 1;
                            if (y.getDepartmentId() == null) noDepartmentId = 1;
                        }
                        if (check == 1) dtos.add(x);
                        else if (noDepartmentId == 1) {
                            List<String> createDepartments = x.getDepartments();
                            String processTicketDepartment=null;
                            for(OptionSetValue osv: osvLst){
                                if(processTicketDepartment==null && createDepartments != null && createDepartments.contains(osv.getId().toString())){
                                    processTicketDepartment = osv.getId().toString();
                                    log.debug("Phong ban xu ly su vu la: " + osv.getId() + " - " + osv.getName());
                                }
                            }
//                            if (createDepartments != null && createDepartments.size() > 0) {
                                for (String a : departments) {
                                    if (a.equals(processTicketDepartment)) check = 1;
                                }
//                            }
                            if (check == 1) dtos.add(x);
                        }
                    }
                });

                if ((pageable.getPageNumber() + 1) * pageable.getPageSize() > dtos.size()) {
                    result = dtos.subList(pageable.getPageNumber() * pageable.getPageSize(), dtos.size());
                } else
                    result = dtos.subList(pageable.getPageNumber() * pageable.getPageSize(), (pageable.getPageNumber() + 1) * pageable.getPageSize());
                return new PageImpl<>(result, pageable, dtos.size());
            }else{
                return ticketRepository.searchTicketByCC(requestSearchTicketDTO.getChannel(),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getPhone()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getEmails()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getIdCode()),
                    DataUtil.makeLikeParam(requestSearchTicketDTO.getReceiveUser()),
                    fromDate,
                    toDate,
                    status, pageable
                ).map(TicketDTO::new);
            }

        } else return null;
    }

    public List<TicketDTO> exportExcelTicket(RequestSearchTicketDTO requestSearchTicketDTO) {
        try {
            List<Long> status = getStatus(requestSearchTicketDTO);
            if(requestSearchTicketDTO.getStatus()!=null)
            {
                status=new ArrayList<Long>();
                status.add(requestSearchTicketDTO.getStatus());
            }
            String fromDate = null;
            if (requestSearchTicketDTO.getFromDate() != null) {
                fromDate = DateUtil.dateToString(requestSearchTicketDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY);
            }
            String toDate = null;
            if (requestSearchTicketDTO.getToDate() != null) {
                toDate = DateUtil.dateToString(requestSearchTicketDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY);
            }
            Optional<User> userLog = userService.getUserWithAuthorities();
            if (requestSearchTicketDTO.getTabType() == 1){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                    null, Translator.toLocale("export-search-ticket-unprocessed"),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.search_ticket, "CONFIG_MENU_ITEM"));
            }
            if (requestSearchTicketDTO.getTabType() == 2){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                    null, Translator.toLocale("export-search-ticket-confirming"),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.search_ticket, "CONFIG_MENU_ITEM"));
            }
            if (requestSearchTicketDTO.getTabType() == 3){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                    null, Translator.toLocale("export-search-ticket-complete"),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.search_ticket, "CONFIG_MENU_ITEM"));
            }

            return ticketRepository.exportExcelTicket(requestSearchTicketDTO.getChannel(),
                requestSearchTicketDTO.getPhone(),
                requestSearchTicketDTO.getEmails(),
                requestSearchTicketDTO.getIdCode(),
                requestSearchTicketDTO.getReceiveUser(),
                fromDate,
                toDate,
                status).stream().map(TicketDTO::new).collect(Collectors.toList());
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private List<Long> getStatus(RequestSearchTicketDTO requestSearchTicketDTO) {
        List<Long> status;
        if (requestSearchTicketDTO.getTabType().equals(Constants.TAB_PROCESSING)) {
            status = Arrays.asList(1L, 2L);
        } else if (requestSearchTicketDTO.getTabType().equals(Constants.TAB_DONE_CONFIRM)) {
            status = Arrays.asList(3L, 4L);
        } else {
            status = Arrays.asList(5L);
        }
        return status;
    }

    private void updateTicketStatus(Long ticketId) {
        String updateSql = "update ticket set status=2 where ticket_id=:ticketId";
        Query query = entityManager.createNativeQuery(updateSql);
        query.setParameter("ticketId", ticketId);
        query.executeUpdate();
    }
}
