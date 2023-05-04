package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;

import com.fis.crm.repository.*;

import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.*;

import com.fis.crm.service.dto.*;

import com.fis.crm.service.mapper.ProcessTicketAttachmentMapper;
import com.fis.crm.service.mapper.ProcessTicketMapper;

import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing {@link ProccessTicket}.
 */
@Service
@Transactional
public class ProcessTicketServiceImpl implements ProcessTicketService {

    private final Logger log = LoggerFactory.getLogger(ProcessTicketServiceImpl.class);

    private final ProcessTicketRepository processTicketRepository;

    private final ProcessTicketMapper processTicketMapper;

    @PersistenceContext
    EntityManager entityManager;

    private final TicketRequestRepository ticketRequestRepository;

    private final ProcessTicketAttachmentRepository processTicketAttachmentRepository;

    private final ProcessTicketAttachmentMapper processTicketAttachmentMapper;

    private final UserService userService;

    private final TicketService ticketService;

    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    private final ConfirmTicketService confirmTicketService;

    private final ActionLogService actionLogService;

    public ProcessTicketServiceImpl(ProcessTicketRepository processTicketRepository,
                                    ProcessTicketMapper processTicketMapper, UserService userService, ProcessTicketAttachmentMapper processTicketAttachmentMapper, TicketRequestRepository ticketRequestRepository, ProcessTicketAttachmentRepository processTicketAttachmentRepository, TicketService ticketService, UserRepository userRepository, DepartmentRepository departmentRepository, ConfirmTicketService confirmTicketService, ActionLogService actionLogService) {
        this.processTicketRepository = processTicketRepository;
        this.processTicketMapper = processTicketMapper;
        this.userService = userService;
        this.processTicketAttachmentMapper = processTicketAttachmentMapper;
        this.ticketRequestRepository = ticketRequestRepository;
        this.processTicketAttachmentRepository = processTicketAttachmentRepository;
        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.confirmTicketService = confirmTicketService;
        this.actionLogService = actionLogService;
    }

    @Override
    public ServiceResult save(ProcessTicketDTO processTicketDTO) {
        log.debug("Request to save ProcessTicket : {}", processTicketDTO);
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (!user.getProcessTicket()) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
        }
        ServiceResult result = new ServiceResult();
//        User user = userRepository.findById(processTicketDTO.getCreateUser()).get();
//        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (validateCreateTicket(processTicketDTO, result, user)) return result;
        try {
            //save process ticket
            ProccessTicket proccessTicket = createProcessTicket(processTicketDTO);
            //update ticket request
            TicketRequest oldRequestTicket = updateRequestTicket(processTicketDTO, user);
            //save process ticket attachments
            updateFileAttachMent(processTicketDTO);


            Ticket ticket = new Ticket();
            ticket.setTicketId(processTicketDTO.getTicketId());
            ticketService.updateTicketConfirmDateAndStatus(ticket);
            result.setStatus(ServiceResult.Status.SUCCESS);
            Optional<TicketDTO> ticket1 = ticketService.findOne(processTicketDTO.getTicketId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            if (processTicketDTO.getCheck() == 1){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    processTicketDTO.getTicketId(), String.format("Lưu sử lý sự vụ: [%s]", ticket1.get().getTicketCode()),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
            }
            if (processTicketDTO.getCheck() == 2){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    processTicketDTO.getTicketId(), String.format("Đóng sử lý sự vụ: [%s]", ticket1.get().getTicketCode()),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
            }
            return result;
        } catch (Exception e) {
            log.debug("Error name: ", e.getMessage());
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("error.process.create.ticket"));
            return result;
        }
    }

    private void updateTicketStatus(ProcessTicketDTO processTicketDTO, ServiceResult result, ProccessTicket proccessTicket) {
        if (processTicketDTO.getCheck() == 1) {
            result.setMessage(Translator.toLocale("create.process.ticket.success"));
        } else {
            //call procedure update ticket status
            Connection connection = null;
            CallableStatement callableStatement = null;
            try {
                EntityManagerFactoryInfo infor = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
                DataSource dataSource = infor.getDataSource();
                connection = dataSource.getConnection();

                String strSQL = "{call pck_util.update_ticket_status(?,null) }";
                callableStatement = connection.prepareCall(strSQL);
                callableStatement.setLong(1, proccessTicket.getTicketId().longValue());
                callableStatement.execute();

            } catch (Exception e) {
            } finally {
                try {
                    if (connection != null)
                        connection.close();

                    if (callableStatement != null)
                        callableStatement.close();
                } catch (Exception ex) {
                }
            }
            result.setMessage(Translator.toLocale("close.process.ticket.success"));
        }
    }

    private void updateFileAttachMent(ProcessTicketDTO processTicketDTO) {
        List<ProccessTicketAttachment> listForUpdate = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(processTicketDTO.getListProcessTicketAttachmentDTOS())) {
            for (ProcessTicketAttachmentDTO processTicketAttachmentDTO : processTicketDTO.getListProcessTicketAttachmentDTOS()) {
                processTicketAttachmentDTO.setFillNameEncrypt(processTicketAttachmentDTO.getFillNameEncrypt());
                processTicketAttachmentDTO.setCreateDatetime(Instant.now());
                processTicketAttachmentDTO.setCreateUser(processTicketDTO.getCreateUser());
                processTicketAttachmentDTO.setStatus(Constants.STATUS_ACTIVE_STR);
                ProccessTicketAttachment proccessTicketAttachment = processTicketAttachmentMapper.toEntity(processTicketAttachmentDTO);
                listForUpdate.add(proccessTicketAttachment);
            }
            processTicketAttachmentRepository.saveAll(listForUpdate);
        }
    }

    private TicketRequest updateRequestTicket(ProcessTicketDTO processTicketDTO, User user) {
        TicketRequest oldRequestTicket = ticketRequestRepository.findById(processTicketDTO.getTicketRequestId()).get();
        oldRequestTicket.setUpdateDatetime(Instant.now());
        oldRequestTicket.setUpdateUser(user.getId());
        oldRequestTicket.setStatus(processTicketDTO.getStatus());
        ticketRequestRepository.save(oldRequestTicket);
        return oldRequestTicket;
    }

    private ProccessTicket createProcessTicket(ProcessTicketDTO processTicketDTO) {
        processTicketDTO.setCreateDatetime(Instant.now());
        ProccessTicket proccessTicket = processTicketMapper.toEntity(processTicketDTO);
        proccessTicket = processTicketRepository.save(proccessTicket);
        return proccessTicket;
    }

    private boolean validateCreateTicket(ProcessTicketDTO processTicketDTO, ServiceResult result, User user) {

        if (!user.getProcessTicket() || null == user.getProcessTicket()) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("user.has.no.right.to.process.ticket"));
            return true;
        }

        if (null == processTicketDTO.getTicketRequestId()) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("ticket.request.cant.be.null"));
            return true;
        }

        TicketRequest tkrq = ticketRequestRepository.findById(processTicketDTO.getTicketRequestId()).get();
        if (tkrq.getStatus().equalsIgnoreCase("3")) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("request-close"));
            return true;
        }
        if(tkrq.getDepartmentId()==null)
        {
            if(!user.getId().equals(tkrq.getCreateUser()))
            {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(Translator.toLocale("user.has.no.right.to.close.process.ticket"));
                return true;
            }
        }
        else {
            List<String> departmentsOfUser = Arrays.asList(user.getDepartments().split(","));
            if (!departmentsOfUser.contains(tkrq.getDepartmentId().toString())) {
                result.setStatus(ServiceResult.Status.FAILED);
                if (processTicketDTO.getCheck().equals(1L)) {
                    result.setMessage(Translator.toLocale("user.has.no.right.to.process.ticket"));
                } else {
                    result.setMessage(Translator.toLocale("user.has.no.right.to.close.process.ticket"));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessTicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessTickets");
        return processTicketRepository.findAll(pageable).map(processTicketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessTicketDTO> findOne(Long id) {
        log.debug("Request to get ProcessTicket : {}", id);
        return processTicketRepository.findById(id).map(processTicketMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessTicket : {}", id);
        processTicketRepository.deleteById(id);
    }

    @Override
    public List<ProcessTicketDTO> getListHistoryProcessTickets(ProcessTicketDTO processTicketDTO2) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<ProcessTicketDTO> lstResult = new ArrayList<ProcessTicketDTO>();
        try {
            lst = processTicketRepository.getListHistoryProcessTickets(processTicketDTO2.getTicketRequestId());
            for (Object[] obj1 : lst) {
                ProcessTicketDTO processTicketDTO = new ProcessTicketDTO();
                processTicketDTO.setProcessTicketId(DataUtil.safeToLong(obj1[0]));
                processTicketDTO.setTicketRequestId(DataUtil.safeToLong(obj1[1]));
                processTicketDTO.setContent(DataUtil.safeToString(obj1[2]));
                processTicketDTO.setUserName(DataUtil.safeToString(obj1[3]));
                processTicketDTO.setCreateDatetime(DataUtil.safeToInstant(obj1[4]));
                processTicketDTO.setDepartmentId(DataUtil.safeToLong(obj1[5]));
                List<Object[]> departmentByDepartmentId = departmentRepository.findDepartmentByDepartmentId(processTicketDTO.getDepartmentId());
                processTicketDTO.setDepartmentName(DataUtil.safeToString(departmentByDepartmentId.get(0)[1]));
                lstResult.add(processTicketDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    @Override
    public ServiceResult saveFRC(TicketProcessDTO ticketProcessDTO){
        ServiceResult result = new ServiceResult();
        result = ticketService.save(ticketProcessDTO.getTicketDTO());
        Long ticketId = result.getTicketId();
        List<ProcessTicketDTO> processTicketDTOList = ticketProcessDTO.getListProcessTicketDTO();
        int i = 0;
        List<Long> listTicketRequestId = result.getListTicketRequestIds();
        for (ProcessTicketDTO processTicketDTO : processTicketDTOList) {
            processTicketDTO.setTicketId(result.getTicketId());
            Long ticketRequestId = listTicketRequestId.get(i);
            processTicketDTO.setTicketRequestId(ticketRequestId);
            User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
            if (!user.getProcessTicket()) {
                throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
            }
            if (validateCreateTicket(processTicketDTO, result, user)) return result;
            try {
                //save process ticket
                processTicketDTO.setCreateDatetime(Instant.now());
                ProccessTicket proccessTicket = processTicketMapper.toEntity(processTicketDTO);
                proccessTicket = processTicketRepository.save(proccessTicket);
                //update ticket request
                TicketRequest oldRequestTicket = ticketRequestRepository.findById(processTicketDTO.getTicketRequestId()).get();
                oldRequestTicket.setUpdateDatetime(Instant.now());
                oldRequestTicket.setUpdateUser(user.getId());
                oldRequestTicket.setStatus(processTicketDTO.getStatus());
                ticketRequestRepository.save(oldRequestTicket);
                //save process ticket attachments
                updateFileAttachMent(processTicketDTO);

                Ticket ticket = new Ticket();
                ticket.setTicketId(processTicketDTO.getTicketId());
                ticketService.updateTicketConfirmDateAndStatus(ticket);
                result.setStatus(ServiceResult.Status.SUCCESS);
                Optional<TicketDTO> ticket1 = ticketService.findOne(processTicketDTO.getTicketId());
                Optional<User> userLog = userService.getUserWithAuthorities();
                if (processTicketDTO.getCheck() == 1){
                    actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                        processTicketDTO.getTicketId(), String.format("Lưu sử lý sự vụ: [%s]", ticket1.get().getTicketCode()),
                        new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
                }
                if (processTicketDTO.getCheck() == 2){
                    actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                        processTicketDTO.getTicketId(), String.format("Đóng sử lý sự vụ: [%s]", ticket1.get().getTicketCode()),
                        new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
                }
            } catch (Exception e) {
                log.debug("Error name: ", e.getMessage());
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(Translator.toLocale("error.process.create.ticket"));
            }
            i++;
        }
        ConfirmTicketDTO confirmTicketDTO = ticketProcessDTO.getConfirmTicketDTO();
        confirmTicketDTO.setTicketId(ticketId);
        result = confirmTicketService.save(confirmTicketDTO);
        result.setTicketId(ticketId);
        result.setListTicketRequestIds(listTicketRequestId);
        return result;
    }
}
