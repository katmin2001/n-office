package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.SendEmailOne;
import com.fis.crm.domain.User;
import com.fis.crm.repository.SendEmailOneRepository;
import com.fis.crm.service.MailService;
import com.fis.crm.service.SendEmailOneService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.SendEmailOneDTO;
import com.fis.crm.service.mapper.SendEmailOneMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SendEmailOneServiceImpl implements SendEmailOneService {

    private final SendEmailOneRepository sendEmailOneRepository;
    private final SendEmailOneMapper sendEmailOneMapper;
    private final UserService userService;
    private final MailService mailService;

    public SendEmailOneServiceImpl(SendEmailOneRepository sendEmailOneRepository, SendEmailOneMapper sendEmailOneMapper, UserService userService, MailService mailService) {
        this.sendEmailOneRepository = sendEmailOneRepository;
        this.sendEmailOneMapper = sendEmailOneMapper;
        this.userService = userService;
        this.mailService = mailService;
    }

    @Override
    public List<SendEmailOneDTO> findAll() {
        return sendEmailOneRepository.findAll().stream().map(sendEmailOneMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void save(SendEmailOneDTO sendEmailOneDTO) {
//        mailService.sendEmail(sendEmailOneDTO.getToEmail(), sendEmailOneDTO.getTitle(), sendEmailOneDTO.getContent(), false, false);
        Optional<User> user = userService.getUserWithAuthorities();
        Instant currentDate = Instant.now();
        SendEmailOne sendEmailOne = sendEmailOneMapper.toEntity(sendEmailOneDTO);
        sendEmailOne.setCreateDateTime(currentDate);
        sendEmailOne.setCreateUser(user.get().getId());
        sendEmailOne.setStatus(Constants.STATUS_ACTIVE);
        sendEmailOne.setSendDate(currentDate);
        sendEmailOneRepository.save(sendEmailOne);
    }
}
