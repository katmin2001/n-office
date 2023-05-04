package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.domain.TicketRequest;
import com.fis.crm.domain.User;
import com.fis.crm.repository.TicketRequestRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.TicketRequestService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.dto.TicketRequestDTO;
import com.fis.crm.service.dto.TicketRequestExtDTO;
import com.fis.crm.service.mapper.TicketRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link TicketRequest}.
 */
@Service
@Transactional
public class TicketRequestServiceImpl implements TicketRequestService {

    private final Logger log = LoggerFactory.getLogger(TicketRequestServiceImpl.class);

    private final TicketRequestRepository ticketRequestRepository;

    private final TicketRequestMapper ticketRequestMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public TicketRequestServiceImpl(TicketRequestRepository ticketRequestRepository, TicketRequestMapper ticketRequestMapper, UserService userService, UserRepository userRepository) {
        this.ticketRequestRepository = ticketRequestRepository;
        this.ticketRequestMapper = ticketRequestMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public TicketRequestDTO save(TicketRequestDTO ticketRequestDTO) {
        log.debug("Request to save TicketRequest : {}", ticketRequestDTO);
        TicketRequest ticketRequest = ticketRequestMapper.toEntity(ticketRequestDTO);
        ticketRequest = ticketRequestRepository.save(ticketRequest);
        return ticketRequestMapper.toDto(ticketRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketRequests");
        return ticketRequestRepository.findAll(pageable).map(ticketRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketRequestDTO> findOne(Long id) {
        log.debug("Request to get TicketRequest : {}", id);
        return ticketRequestRepository.findById(id).map(ticketRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketRequest : {}", id);
        ticketRequestRepository.deleteById(id);
    }

    @Override
    public List<TicketRequestExtDTO> getTicketRequestByTicketId(TicketDTO ticketDTO) {
        List<TicketRequestExtDTO> ticketRequestExtDTOS = ticketRequestRepository.getTicketRequestByTicketId(ticketDTO.getTicketId()).stream().map(TicketRequestExtDTO::new).collect(Collectors.toList());
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        ticketRequestExtDTOS.forEach(ticketRequestExtDTO -> {
            if (null == user.getProcessTicket() || !user.getProcessTicket()) {
                ticketRequestExtDTO.setDisableCloseSave(true);
            } else if (ticketRequestExtDTO.getDepartmentId() == null) {
                User userCreate = userRepository.findById(ticketRequestExtDTO.getCreateUser()).orElse(null);
                if(userCreate != null && !DataUtil.isNullOrEmpty(userCreate.getDepartments()) && !DataUtil.isNullOrEmpty(user.getDepartments())) {
//                    List<String> userCreateDepartment = Arrays.asList(userCreate.getDepartments().split(","));
//                    List<String> currentUserDepartment = Arrays.asList(user.getDepartments().split(","));
//                    Stream departmentCreateUser = Stream.of(userCreate.getDepartments().split(","))
//                        .filter(s -> s.trim().length()>0);
//                    if(Stream.of(user.getDepartments().split(",")).filter(s -> s.trim().length()>0)
//                        .noneMatch(s -> departmentCreateUser.anyMatch(tmp -> s.equals(tmp)))) {
//                        ticketRequestExtDTO.setDisableCloseSave(true);
//                    }
                    List<String> createDepartments = Arrays.asList(ticketRequestExtDTO.getCreateDepartments().split(","));
                    List<String> userDepartments = Arrays.asList(user.getDepartments().split(","));
                    int check =0;
                    for(String d:createDepartments){
                        if(userDepartments.contains(d)) check = 1;
                    }
                    if (check == 0) ticketRequestExtDTO.setDisableCloseSave(true);
                } else {
                    ticketRequestExtDTO.setDisableCloseSave(true);
                }

            } else {
                if (!(","+ DataUtil.safeToString(user.getDepartments())+",").contains(","+ticketRequestExtDTO.getDepartmentId().toString()+",")) {
                    ticketRequestExtDTO.setDisableCloseSave(true);
                }
            }
        });
        if(ticketRequestExtDTOS!=null && ticketRequestExtDTOS.size()>0){
            ticketRequestExtDTOS.sort(Comparator.comparing(TicketRequestExtDTO::getTicketRequestId));
        }
        return ticketRequestExtDTOS;
    }

}
