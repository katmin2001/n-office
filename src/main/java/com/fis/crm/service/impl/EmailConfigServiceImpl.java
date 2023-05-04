package com.fis.crm.service.impl;

import com.fis.crm.security.EncodeAndDecode;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.EmailConfig;
import com.fis.crm.domain.EmailConfigHistory;
import com.fis.crm.domain.User;
import com.fis.crm.repository.EmailConfigHistoryRepository;
import com.fis.crm.repository.EmailConfigRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.EmailConfigService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.EmailConfigDTO;
import com.fis.crm.service.mapper.EmailConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmailConfigServiceImpl implements EmailConfigService {
    private final EmailConfigRepository emailConfigRepository;
    private final UserService userService;
    private final ActionLogService actionLogService;
    private final EmailConfigMapper emailConfigMapper;
    private final EmailConfigHistoryRepository emailConfigHistoryRepository;

    public EmailConfigServiceImpl(EmailConfigRepository emailConfigRepository, UserService userService, ActionLogService actionLogService, EmailConfigMapper emailConfigMapper, EmailConfigHistoryRepository emailConfigHistoryRepository) {
        this.emailConfigRepository = emailConfigRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.emailConfigMapper = emailConfigMapper;
        this.emailConfigHistoryRepository = emailConfigHistoryRepository;
    }

    @Override
    public List<EmailConfigDTO> getAll() {
        return emailConfigRepository.findAll().stream().map(emailConfigMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<EmailConfigDTO> findById(Long id) {
        return emailConfigRepository.findById(id).map(emailConfigMapper::toDto);
    }

    @Override
    public void save(EmailConfigDTO emailConfigDTO) throws Exception{
        Optional<User> user = userService.getUserWithAuthorities();
        Instant currentDate = Instant.now();
        EmailConfig emailConfig = emailConfigMapper.toEntity(emailConfigDTO);

        //ma hoa password
        String password = EncodeAndDecode.encrypt(emailConfigDTO.getPassword());
        emailConfig.setPassword(password);

        emailConfig.setCreateUser(user.get().getId());
        emailConfig.setCreateDateTime(currentDate);
        emailConfig.setSsl(Constants.STATUS_ACTIVE);
        emailConfigRepository.save(emailConfig);
    }

    @Override
    public void update(Long id, EmailConfigDTO emailConfigDTO) throws Exception{
        Optional<EmailConfig> emailConfig = emailConfigRepository.findById(id);
        Optional<User> user = userService.getUserWithAuthorities();
        Instant currentDate = Instant.now();

        EmailConfigHistory emailConfigHistory = new EmailConfigHistory();
        emailConfigHistory.setHost(emailConfig.get().getHost());
        emailConfigHistory.setPort(emailConfig.get().getPort());
        emailConfigHistory.setEmail(emailConfig.get().getEmail());
        emailConfigHistory.setSsl(emailConfig.get().getSsl());
        emailConfigHistory.setPassword(emailConfig.get().getPassword());
        emailConfigHistory.setCreateDateTime(currentDate);
        emailConfigHistory.setCreateUser(user.get().getId());
        emailConfigHistoryRepository.save(emailConfigHistory);

        if (emailConfig.isPresent()) {
            emailConfig.get().setHost(emailConfigDTO.getHost());
            emailConfig.get().setEmail(emailConfigDTO.getEmail());

            //ma hoa
            String password = EncodeAndDecode.encrypt(emailConfigDTO.getPassword());
            emailConfig.get().setPassword(password);
            emailConfig.get().setPassword(password);


            emailConfig.get().setSsl(emailConfigDTO.getSsl());
            emailConfig.get().setPort(emailConfigDTO.getPort());
            emailConfig.get().setUpdateDateTime(currentDate);
            emailConfig.get().setUpdateUser(user.get().getId());
        }
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật: Cấu hình mail server gửi đi"),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.email_config, "CONFIG_MENU_ITEM"));
        emailConfigRepository.save(emailConfig.get());
    }
}
