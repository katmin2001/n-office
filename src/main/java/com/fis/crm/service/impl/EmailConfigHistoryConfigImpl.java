package com.fis.crm.service.impl;

import com.fis.crm.domain.EmailConfig;
import com.fis.crm.domain.EmailConfigHistory;
import com.fis.crm.domain.User;
import com.fis.crm.repository.EmailConfigHistoryRepository;
import com.fis.crm.service.EmailConfigHistoryService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.EmailConfigHistoryDTO;
import com.fis.crm.service.mapper.EmailConfigHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmailConfigHistoryConfigImpl implements EmailConfigHistoryService {
    private final EmailConfigHistoryRepository emailConfigHistoryRepository;
    private final EmailConfigHistoryMapper emailConfigHistoryMapper;
    private final UserService userService;

    public EmailConfigHistoryConfigImpl(EmailConfigHistoryRepository emailConfigHistoryRepository, EmailConfigHistoryMapper emailConfigHistoryMapper, UserService userService) {
        this.emailConfigHistoryRepository = emailConfigHistoryRepository;
        this.emailConfigHistoryMapper = emailConfigHistoryMapper;
        this.userService = userService;
    }

    @Override
    public List<EmailConfigHistoryDTO> findAll() {
        return emailConfigHistoryRepository.findAll().stream().map(emailConfigHistoryMapper::toDto).collect(Collectors.toList());
    }
}
