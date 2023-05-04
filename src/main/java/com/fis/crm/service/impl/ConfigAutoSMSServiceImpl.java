package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.ConfigAutoSMS;
import com.fis.crm.domain.ConfigAutoSMS_;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ConfigAutoSMSRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ConfigAutoSMSService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ConfigAutoSMSDTO;
import com.fis.crm.service.dto.UserDTO;
import com.fis.crm.service.mapper.ConfigAutoSMSMapper;
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
public class ConfigAutoSMSServiceImpl implements ConfigAutoSMSService {

    final
    ConfigAutoSMSRepository configAutoSMSRepository;

    final
    ConfigAutoSMSMapper configAutoSMSMapper;

    final EntityManager entityManager;

    final UserService userService;
    private final ActionLogService actionLogService;

    public ConfigAutoSMSServiceImpl(ConfigAutoSMSRepository configAutoSMSRepository,
                                    ConfigAutoSMSMapper configAutoSMSMapper,
                                    EntityManager entityManager,
                                    UserService userService, ActionLogService actionLogService) {
        this.configAutoSMSRepository = configAutoSMSRepository;
        this.configAutoSMSMapper = configAutoSMSMapper;
        this.entityManager = entityManager;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ConfigAutoSMSDTO> findAllByCreateDateDesc(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConfigAutoSMS> criteriaQuery = cb.createQuery(ConfigAutoSMS.class);
        Root<ConfigAutoSMS> root = criteriaQuery.from(ConfigAutoSMS.class);
        criteriaQuery.orderBy(cb.desc(root.get(ConfigAutoSMS_.CREATE_DATE_TIME)));

        List<ConfigAutoSMS> rs = entityManager.createQuery(criteriaQuery)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ConfigAutoSMS> rootCount = countQuery.from(ConfigAutoSMS.class);
        countQuery.select(cb.count(rootCount));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        List<ConfigAutoSMSDTO> rsDTOs = rs.stream().map(configAutoSMSMapper::toDto).collect(Collectors.toList());
        rsDTOs.forEach(configAutoSMSDTO -> {
            Optional<UserDTO> userDTO = userService.findFirstById(configAutoSMSDTO.getCreateUser());
            userDTO.ifPresent(dto -> configAutoSMSDTO.setCreateUserName(dto.getFullName()));
        });
        return new PageImpl<>(rsDTOs, pageable, count);
    }


    @Override
    public ConfigAutoSMSDTO save(ConfigAutoSMSDTO configAutoSMSDTO) {
        if (configAutoSMSDTO.getId() == null) {
            // Toa moi ban ghi
            Optional<User> userOptional = userService.getUserWithAuthorities();
            userOptional.ifPresent(user -> configAutoSMSDTO.setCreateUser(user.getId()));
            configAutoSMSDTO.setCreateDateTime(new Date());
            ConfigAutoSMS configAutoSMS = configAutoSMSRepository.save(configAutoSMSMapper.toEntity(configAutoSMSDTO));
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới: Cấu hình gửi SMS tự động [%s]", configAutoSMSDTO.getName()),
                new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.config_auto_sms, "CONFIG_MENU_ITEM"));
            return configAutoSMSMapper.toDto(configAutoSMS);
        } else {
            // Chinh sua ban ghi
            ConfigAutoSMS configAutoSMS = configAutoSMSRepository.getOne(configAutoSMSDTO.getId());
            configAutoSMSMapper.partialUpdate(configAutoSMS, configAutoSMSDTO);
            configAutoSMS.setDateOfWeek(configAutoSMSDTO.getDateOfWeek());
            configAutoSMS.setDateSend(configAutoSMSDTO.getDateSend());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật: Cấu hình gửi SMS tự động [%s]", configAutoSMSDTO.getName()),
                new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.config_auto_sms, "CONFIG_MENU_ITEM"));
            return configAutoSMSMapper.toDto(configAutoSMS);
        }
    }

    @Override
    public ConfigAutoSMSDTO delete(Long id) {
        configAutoSMSRepository.deleteById(id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Cấu hình gửi SMS tự động"),
            new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.config_auto_sms, "CONFIG_MENU_ITEM"));
        return new ConfigAutoSMSDTO();
    }


}
