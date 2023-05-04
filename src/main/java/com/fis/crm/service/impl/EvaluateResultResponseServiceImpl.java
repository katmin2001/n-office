package com.fis.crm.service.impl;

import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.EvaluateResultResponse;
import com.fis.crm.domain.User;
import com.fis.crm.repository.EvaluateResultRepository;
import com.fis.crm.repository.EvaluateResultResponseRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.EvaluateResultResponseService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.EvaluateResultResponseDTO;
import com.fis.crm.service.mapper.EvaluateResultResponseMapper;
import com.fis.crm.service.mapper.UserMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EvaluateResultResponse}.
 */
@Service
@Transactional
public class EvaluateResultResponseServiceImpl implements EvaluateResultResponseService {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultResponseServiceImpl.class);

    private final EvaluateResultResponseRepository evaluateResultResponseRepository;

    private final EvaluateResultRepository evaluateResultRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ActionLogService actionLogService;

    private final EvaluateResultResponseMapper evaluateResultResponseMapper;

    public EvaluateResultResponseServiceImpl(EvaluateResultResponseRepository evaluateResultResponseRepository, EvaluateResultRepository evaluateResultRepository, UserRepository userRepository, UserService userService, UserMapper userMapper, ActionLogService actionLogService, EvaluateResultResponseMapper evaluateResultResponseMapper) {
        this.evaluateResultResponseRepository = evaluateResultResponseRepository;
        this.evaluateResultRepository = evaluateResultRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.actionLogService = actionLogService;
        this.evaluateResultResponseMapper = evaluateResultResponseMapper;
    }

    @Override
    public EvaluateResultResponseDTO save(EvaluateResultResponseDTO evaluateResultResponseDTO) {
        log.debug("Request to save EvaluateResultResponse : {}", evaluateResultResponseDTO);
        evaluateResultRepository.findById(evaluateResultResponseDTO.getEvaluateResultId()).orElseThrow(() ->
            new BusinessException("101", Translator.toLocale("evaluate.result.not.found")));

        EvaluateResultResponse evaluateResultResponse = evaluateResultResponseMapper.toEntity(evaluateResultResponseDTO);
        evaluateResultResponse.createDatetime(new Date());
        Optional<User> optionalUser = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        evaluateResultResponse.setUser(optionalUser.get());
        evaluateResultResponse = evaluateResultResponseRepository.save(evaluateResultResponse);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Phàn hồi: Danh sách cuộc gọi được giám sát"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_list, "CONFIG_MENU_ITEM"));
        return evaluateResultResponseMapper.toDto(evaluateResultResponse);
    }

    @Override
    public Page<EvaluateResultResponseDTO> getEvaluateResultResponseByEvaluateResult(Long evaluateResultId, Pageable pageable) {
        Page<EvaluateResultResponse> page = evaluateResultResponseRepository.findAllByEvaluateResultId(evaluateResultId, pageable);
        List<EvaluateResultResponse> evaluateResultResponses = page.getContent();
        List<EvaluateResultResponseDTO> lstResults = evaluateResultResponses.stream().map(evaluateResultResponse -> {
            EvaluateResultResponseDTO evaluateResultResponseDTO = evaluateResultResponseMapper.toDto(evaluateResultResponse);
            evaluateResultResponseDTO.setUserDTO(userMapper.userToUserDTO(evaluateResultResponse.getUser()));
            return evaluateResultResponseDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Page<EvaluateResultResponseDTO> findAll(Pageable pageable) {
//        log.debug("Request to get all EvaluateResultResponses");
//        return evaluateResultResponseRepository.findAll(pageable)
//            .map(evaluateResultResponseMapper::toDto);
//    }


//    @Override
//    @Transactional(readOnly = true)
//    public Optional<EvaluateResultResponseDTO> findOne(Long id) {
//        log.debug("Request to get EvaluateResultResponse : {}", id);
//        return evaluateResultResponseRepository.findById(id)
//            .map(evaluateResultResponseMapper::toDto);
//    }

//    @Override
//    public void delete(Long id) {
//        log.debug("Request to delete EvaluateResultResponse : {}", id);
//        evaluateResultResponseRepository.deleteById(id);
//    }
}
