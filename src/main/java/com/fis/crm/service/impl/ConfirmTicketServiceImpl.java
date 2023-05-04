package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;

import com.fis.crm.repository.*;

import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ConfirmTicketService;

import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;

import com.fis.crm.service.mapper.ConfirmTicketAttachmentMapper;
import com.fis.crm.service.mapper.ConfirmTicketMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ConfirmTicket}.
 */
@Service
@Transactional
public class ConfirmTicketServiceImpl implements ConfirmTicketService {

    private final Logger log = LoggerFactory.getLogger(ConfirmTicketServiceImpl.class);

    private final ConfirmTicketRepository confirmTicketRepository;

    private final ConfirmTicketMapper confirmTicketMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ConfirmTicketAttachmentMapper confirmTicketAttachmentMapper;

    @Autowired
    private ConfirmTicketAttachmentRepository confirmTicketAttachmentRepository;

    private final DepartmentRepository departmentRepository;

    private final UserService userService;

    private final ActionLogService actionLogService;

    public ConfirmTicketServiceImpl(ConfirmTicketRepository confirmTicketRepository,
                                    ConfirmTicketMapper confirmTicketMapper, DepartmentRepository departmentRepository, UserService userService, ActionLogService actionLogService) {
        this.confirmTicketRepository = confirmTicketRepository;
        this.confirmTicketMapper = confirmTicketMapper;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public ServiceResult save(ConfirmTicketDTO confirmTicketDTO) {
        log.debug("Request to save ConfirmTicket : {}", confirmTicketDTO);
        ServiceResult result = new ServiceResult();

        Optional<Ticket> ticket = ticketRepository.findById(confirmTicketDTO.getTicketId());
        if (ticket.isPresent()) {
            if (ticket.get().getStatus().equalsIgnoreCase("5")) {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(Translator.toLocale("request-close1"));
                return result;
            }
        }

        try {
            //save confirm ticket
            createConfirmTicket(confirmTicketDTO);
            //update ticket
            Ticket oldTicket = updateCurrentTicket(confirmTicketDTO);
            //save confirm ticket attachment
            saveConfirmTicketAttachment(confirmTicketDTO, oldTicket);
            result.setStatus(ServiceResult.Status.SUCCESS);
            result.setMessage(Translator.toLocale("create.confirm.ticket.success"));
            Optional<Ticket> ticket1 = ticketRepository.findById(confirmTicketDTO.getTicketId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            if ("4".equals(confirmTicketDTO.getStatus())){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    confirmTicketDTO.getTicketId(), String.format("Lưu xác nhận: [%s]", ticket1.get().getTicketCode()),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
            }
            if ("5".equals(confirmTicketDTO.getStatus())){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    confirmTicketDTO.getTicketId(), String.format("Đóng sự vụ: [%s]", ticket1.get().getTicketCode()),
                    new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.event_receive, "CONFIG_MENU_ITEM"));
            }
            return result;
        } catch (Exception e) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(Translator.toLocale("error.confirm.create.ticket"));
            return result;
        }
    }

    private void saveConfirmTicketAttachment(ConfirmTicketDTO confirmTicketDTO, Ticket oldTicket) {
        List<ConfirmTicketAttachment> listForUpdate = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(confirmTicketDTO.getListConfirmTicketAttachmentDTOs())) {
            for (ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO : confirmTicketDTO.getListConfirmTicketAttachmentDTOs()) {
                confirmTicketAttachmentDTO.setCreateDatetime(Instant.now());
                confirmTicketAttachmentDTO.setCreateUser(oldTicket.getCreateUser());
                confirmTicketAttachmentDTO.setStatus(Constants.STATUS_ACTIVE_STR);
                confirmTicketAttachmentDTO.setTicketId(oldTicket.getTicketId());
                ConfirmTicketAttachment confirmTicketAttachment = confirmTicketAttachmentMapper.toEntity(confirmTicketAttachmentDTO);
                listForUpdate.add(confirmTicketAttachment);
            }
        }
        confirmTicketAttachmentRepository.saveAll(listForUpdate);
    }

    private Ticket updateCurrentTicket(ConfirmTicketDTO confirmTicketDTO) {
        Ticket oldTicket = ticketRepository.findById(confirmTicketDTO.getTicketId()).get();
        oldTicket.setStatus(confirmTicketDTO.getStatus());
        oldTicket.setConfirmUser(confirmTicketDTO.getCreateUser());
        oldTicket.setConfirmDatetime(new Date());
        oldTicket.setSatisfied(confirmTicketDTO.getSatisfied());
        oldTicket = ticketRepository.save(oldTicket);
        return oldTicket;
    }

    private void createConfirmTicket(ConfirmTicketDTO confirmTicketDTO) {
        ConfirmTicket confirmTicket = confirmTicketMapper.toEntity(confirmTicketDTO);
        confirmTicket.setCreateDatetime(Instant.now());
        confirmTicketRepository.save(confirmTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfirmTicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfirmTickets");
        return confirmTicketRepository.findAll(pageable).map(confirmTicketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfirmTicketDTO> findOne(Long id) {
        log.debug("Request to get ConfirmTicket : {}", id);
        return confirmTicketRepository.findById(id).map(confirmTicketMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfirmTicket : {}", id);
        confirmTicketRepository.deleteById(id);
    }

    @Override
    public List<ConfirmTicketDTO> getListHistoryConfirmTickets(Long ticketId) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<ConfirmTicketDTO> lstResult = new ArrayList<ConfirmTicketDTO>();
        try {
            lst = confirmTicketRepository.getListHistoryConfirmTickets(ticketId);
            for (Object[] obj1 : lst) {
                ConfirmTicketDTO confirmTicketDTO = new ConfirmTicketDTO();
                confirmTicketDTO.setConfirmTicketId(DataUtil.safeToLong(obj1[0]));
                confirmTicketDTO.setTicketId(DataUtil.safeToLong(obj1[1]));
                confirmTicketDTO.setContent(DataUtil.safeToString(obj1[2]));
                confirmTicketDTO.setUserName(DataUtil.safeToString(obj1[3]));
                confirmTicketDTO.setCreateDatetime(DataUtil.safeToInstant(obj1[4]));
                confirmTicketDTO.setDepartmentId(DataUtil.safeToLong(obj1[5]));
                List<Object[]> departmentByDepartmentId = departmentRepository.findDepartmentByDepartmentId(confirmTicketDTO.getDepartmentId());
                confirmTicketDTO.setDepartmentName(DataUtil.safeToString(departmentByDepartmentId.get(0)[1]));
                lstResult.add(confirmTicketDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }

    public List<ConfirmTicketDTO> getAllByTicketId(Long ticketId) {
        return confirmTicketRepository.getAllByTicketId(ticketId).stream().map(confirmTicketMapper::toDto).collect(Collectors.toList());
    }
}
