package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigAutoEmail;
import com.fis.crm.domain.ConfigAutoEmail_;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ConfigAutoEmailRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ConfigAutoEmailService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ConfigAutoEmailDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.ConfigAutoEmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigAutoEmailServiceImpl implements ConfigAutoEmailService {

    private final Logger log = LoggerFactory.getLogger(ConfigAutoEmailServiceImpl.class);

    final
    ConfigAutoEmailRepository configAutoEmailRepository;

    final
    ConfigAutoEmailMapper configAutoEmailMapper;

    final
    EntityManager entityManager;

    final
    UserService userService;
    private final ActionLogService actionLogService;

    public ConfigAutoEmailServiceImpl(ConfigAutoEmailRepository configAutoEmailRepository,
                                      ConfigAutoEmailMapper configAutoEmailMapper, EntityManager entityManager, UserService userService, ActionLogService actionLogService) {
        this.configAutoEmailRepository = configAutoEmailRepository;
        this.configAutoEmailMapper = configAutoEmailMapper;
        this.entityManager = entityManager;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ConfigAutoEmailDTO> findAllByCreateDateDesc(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigAutoEmail> criteriaQuery = cb.createQuery(ConfigAutoEmail.class);
        Root<ConfigAutoEmail> root = criteriaQuery.from(ConfigAutoEmail.class);
        criteriaQuery.orderBy(cb.desc(root.get(ConfigAutoEmail_.CREATE_DATE_TIME)));

        List<ConfigAutoEmail> rs = entityManager.createQuery(criteriaQuery)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ConfigAutoEmail> rootCount = countQuery.from(ConfigAutoEmail.class);
        countQuery.select(cb.count(rootCount));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<ConfigAutoEmailDTO> rsDTOs = rs.stream().map(configAutoEmailMapper::toDto).collect(Collectors.toList());
        rsDTOs.forEach(configAutoEmailDTO -> {
            Optional<UserDTO> userDTO = userService.findFirstById(configAutoEmailDTO.getCreateUser());
            userDTO.ifPresent(dto -> configAutoEmailDTO.setCreateUserName(dto.getFullName()));
        });
        return new PageImpl<>(rsDTOs, pageable, count);
    }


    @Override
    public ConfigAutoEmailDTO save(ConfigAutoEmailDTO configAutoEmailDTO) {
        if (configAutoEmailDTO.getId() == null) {
            // Toa moi ban ghi
            Optional<User> userOptional = userService.getUserWithAuthorities();
            userOptional.ifPresent(user -> configAutoEmailDTO.setCreateUser(user.getId()));
            configAutoEmailDTO.setCreateDateTime(new Date());
            ConfigAutoEmail configAutoEmail = configAutoEmailRepository.save(configAutoEmailMapper.toEntity(configAutoEmailDTO));
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới: Cấu hình gửi email tự động [%s]" , configAutoEmailDTO.getName()),
                new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.email_config, "CONFIG_MENU_ITEM"));
            return configAutoEmailMapper.toDto(configAutoEmail);
        } else {
            // Chinh sua ban ghi
            ConfigAutoEmail configAutoEmail = configAutoEmailRepository.getOne(configAutoEmailDTO.getId());
            configAutoEmailMapper.partialUpdate(configAutoEmail, configAutoEmailDTO);
            configAutoEmail.setDateOfWeek(configAutoEmailDTO.getDateOfWeek());
            configAutoEmail.setDateSend(configAutoEmailDTO.getDateSend());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật: Cấu hình gửi email tự động [%s]" , configAutoEmailDTO.getName()),
                new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.email_config, "CONFIG_MENU_ITEM"));
            return configAutoEmailMapper.toDto(configAutoEmail);
        }
    }

    @Override
    public ConfigAutoEmailDTO delete(Long id) {
        configAutoEmailRepository.deleteById(id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Cấu hình gửi email tự động"),
            new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.email_config, "CONFIG_MENU_ITEM"));
        return new ConfigAutoEmailDTO();
    }


}
