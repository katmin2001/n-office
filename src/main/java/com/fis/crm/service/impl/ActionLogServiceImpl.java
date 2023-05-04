package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.domain.ActionLog;
import com.fis.crm.repository.ActionLogRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.mapper.ActionLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ActionLog}.
 */
@Service
@Transactional
public class ActionLogServiceImpl implements ActionLogService {

    private final Logger log = LoggerFactory.getLogger(ActionLogServiceImpl.class);

    private final ActionLogRepository actionLogRepository;
    private final ActionLogMapper actionLogMapper;


    private Consumer<ActionLogDTO> consumerWriteLog = ((actionLogDTO)-> {
        save(actionLogDTO);
    });

    public ActionLogServiceImpl(ActionLogRepository actionLogRepository, ActionLogMapper actionLogMapper) {
        this.actionLogRepository = actionLogRepository;
        this.actionLogMapper = actionLogMapper;
    }

    @Override
    public ActionLogDTO save(ActionLogDTO actionLogDTO) {
        log.debug("Request to save ConfigSchedule : {}", actionLogDTO);
        ActionLog actionLog = actionLogMapper.toEntity(actionLogDTO);
        actionLog = actionLogRepository.save(actionLog);
        return actionLogMapper.toDto(actionLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActionLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfigSchedules");
        return actionLogRepository.findAll(pageable)
            .map(actionLogMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActionLogDTO> findOne(Long id) {
        log.debug("Request to get ConfigSchedule : {}", id);
        return actionLogRepository.findById(id)
            .map(actionLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfigSchedule : {}", id);
        actionLogRepository.deleteById(id);
    }

    @Override
    public Consumer<ActionLogDTO> getConsumerWriteLog() {
        return consumerWriteLog;
    }

    @Override
    public void saveActionLog(ActionLogDTO actionLogDTO){
        save(actionLogDTO);
    }

    @Override
    public Page<ActionLogDTO> getActionLog(ActionLogDTO actionLogDTO, Pageable pageable) {
        Page<Object[]> page = actionLogRepository.getActionLog(actionLogDTO.getMenuId() == null ? -1 : actionLogDTO.getMenuId(),
            actionLogDTO.getMenuItemId() == null ? -1 : actionLogDTO.getMenuItemId(),
            actionLogDTO.getActionType() == null ? "-1" : actionLogDTO.getActionType(),
            actionLogDTO.getUserId() == null ? -1 : actionLogDTO.getUserId(),
            DateUtil.dateToStringDateVN(actionLogDTO.getStartDate()),
            DateUtil.dateToStringDateVN(actionLogDTO.getEndDate()),
            DataUtil.makeLikeQuery(actionLogDTO.getNote()), pageable);
        List<ActionLogDTO> lstResults = page.getContent().stream().map(object -> {
            ActionLogDTO tmp  = new ActionLogDTO();
            int index = -1;
            tmp.setMenuName(DataUtil.safeToString(object[++index]));
            tmp.setMenuItemName(DataUtil.safeToString(object[++index]));
            tmp.setUserName(DataUtil.safeToString(object[++index]));
            tmp.setNote(DataUtil.safeToString(object[++index]));
            tmp.setActionTypeName(DataUtil.safeToString(object[++index]));
            tmp.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            return tmp;
        }).collect(Collectors.toList());

        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    public List<ActionLogDTO> getAllActionLog(ActionLogDTO actionLogDTO) {
        List<Object[]> page = actionLogRepository.getAllActionLog(actionLogDTO.getMenuId() == null ? -1 : actionLogDTO.getMenuId(),
            actionLogDTO.getMenuItemId() == null ? -1 : actionLogDTO.getMenuItemId(),
            actionLogDTO.getActionType() == null ? "-1" : actionLogDTO.getActionType(),
            actionLogDTO.getUserId() == null ? -1 : actionLogDTO.getUserId(),
            DateUtil.dateToStringDateVN(actionLogDTO.getStartDate()),
            DateUtil.dateToStringDateVN(actionLogDTO.getEndDate()),
            DataUtil.makeLikeQuery(actionLogDTO.getNote()));
        List<ActionLogDTO> lstResults = page.stream().map(object -> {
            ActionLogDTO tmp  = new ActionLogDTO();
            int index = -1;
            tmp.setMenuName(DataUtil.safeToString(object[++index]));
            tmp.setMenuItemName(DataUtil.safeToString(object[++index]));
            tmp.setUserName(DataUtil.safeToString(object[++index]));
            tmp.setNote(DataUtil.safeToString(object[++index]));
            tmp.setActionTypeName(DataUtil.safeToString(object[++index]));
            tmp.setCreateDatetimeName(DataUtil.safeToString(object[++index]));
            return tmp;
        }).collect(Collectors.toList());

        return lstResults;
    }

    public void saveActionLog(Long userId, String actionType, Long objectId, String objectName, String note, Instant issueDateTime){
        ActionLogDTO actionLogDTO = new ActionLogDTO();
        actionLogDTO.setUserId(userId);
        actionLogDTO.setActionType(actionType);
        actionLogDTO.setObjectId(objectId);
        actionLogDTO.setObjectName(objectName);
        actionLogDTO.setNote(note);
        actionLogDTO.setIssueDateTime(issueDateTime);
        save(actionLogDTO);
    }
}
