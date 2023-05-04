package com.fis.crm.service.impl;

import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Notification;
import com.fis.crm.domain.User;
import com.fis.crm.repository.NotificationRepository;
import com.fis.crm.service.NotificationService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.NotificationDTO;
import com.fis.crm.service.mapper.NotificationMapper;
import java.util.Optional;

import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

  private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

  private final NotificationRepository notificationRepository;

  private final NotificationMapper notificationMapper;
  private final UserService userService;

  public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserService userService) {
    this.notificationRepository = notificationRepository;
    this.notificationMapper = notificationMapper;
      this.userService = userService;
  }

  @Override
  public NotificationDTO save(NotificationDTO notificationDTO) {
    log.debug("Request to save Notification : {}", notificationDTO);
    Notification notification = notificationMapper.toEntity(notificationDTO);
    notification = notificationRepository.save(notification);
    return notificationMapper.toDto(notification);
  }

  @Override
  public Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO) {
    log.debug("Request to partially update Notification : {}", notificationDTO);
    Optional<User> user = userService.getUserWithAuthorities();
    if(!user.isPresent()) {
        throw new BadRequestAlertException(Translator.toLocale("notification.haveNoPermissionNotification"), "notification", "notification.haveNoPermissionNotification");
    }
    return notificationRepository
      .findById(notificationDTO.getId())
      .map(
        existingNotification -> {
          notificationMapper.partialUpdate(existingNotification, notificationDTO);
          if(!user.get().getId().equals(existingNotification.getUserId())) {
              throw new BadRequestAlertException(Translator.toLocale("notification.haveNoPermissionNotification"),"notification", "notification.haveNoPermissionNotification");
          }
          return existingNotification;
        }
      )
      .map(notificationRepository::save)
      .map(notificationMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<NotificationDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Notifications");
    Optional<User> user = userService.getUserWithAuthorities();
    if(!user.isPresent()) {
        throw new BadRequestAlertException(Translator.toLocale("notification.haveNoPermissionReadNotification"), "notification", "notification.haveNoPermissionReadNotification");
    }
    return notificationRepository.findAllByUserIdAndStatusOrderByCreateDatetimeDesc(user.get().getId(),
        Constants.STATUS_ACTIVE_STR, pageable).map(notificationMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<NotificationDTO> findOne(Long id) {
    log.debug("Request to get Notification : {}", id);
    return notificationRepository.findById(id).map(notificationMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Notification : {}", id);
    notificationRepository.deleteById(id);
  }
}
