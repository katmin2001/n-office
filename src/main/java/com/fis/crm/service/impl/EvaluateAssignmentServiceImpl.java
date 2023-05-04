package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.EvaluateAssignment;
import com.fis.crm.domain.EvaluateAssignmentDetail;
import com.fis.crm.domain.User;
import com.fis.crm.repository.EvaluateAssignmentRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.EvaluateAssignmentService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.EvaluateAssignmentDTO;
import com.fis.crm.service.dto.EvaluateAssignmentDataDTO;
import com.fis.crm.service.dto.EvaluateAssignmentSearchDTO;
import com.fis.crm.service.mapper.EvaluateAssignmentDetailMapper;
import com.fis.crm.service.mapper.EvaluateAssignmentMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EvaluateAssignment}.
 */
@Service
@Transactional
public class EvaluateAssignmentServiceImpl implements EvaluateAssignmentService {

    private final Logger log = LoggerFactory.getLogger(EvaluateAssignmentServiceImpl.class);

    private final EvaluateAssignmentRepository evaluateAssignmentRepository;
    private final UserService userService;
    private final ActionLogService actionLogService;

    private final EvaluateAssignmentMapper evaluateAssignmentMapper;
    private final EvaluateAssignmentDetailMapper evaluateAssignmentDetailMapper;

    public EvaluateAssignmentServiceImpl(EvaluateAssignmentRepository evaluateAssignmentRepository, UserService userService, ActionLogService actionLogService, EvaluateAssignmentMapper evaluateAssignmentMapper, EvaluateAssignmentDetailMapper evaluateAssignmentDetailMapper) {
        this.evaluateAssignmentRepository = evaluateAssignmentRepository;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.evaluateAssignmentMapper = evaluateAssignmentMapper;
        this.evaluateAssignmentDetailMapper = evaluateAssignmentDetailMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluateAssignments");
        return evaluateAssignmentRepository.findAll(pageable)
            .map(evaluateAssignmentMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluateAssignmentDTO> findOne(Long id) {
        log.debug("Request to get EvaluateAssignment : {}", id);
        EvaluateAssignmentDTO evaluateAssignmentDTO = null;
        Optional<EvaluateAssignment> optionalEvaluateAssignment = evaluateAssignmentRepository.findById(id);
        if(optionalEvaluateAssignment.isPresent()) {
            EvaluateAssignment evaluateAssignment = optionalEvaluateAssignment.get();
            List<EvaluateAssignmentDetail> evaluateAssignmentDetails = evaluateAssignment.getEvaluateAssignmentDetails();
            evaluateAssignmentDTO = evaluateAssignmentMapper.toDto(evaluateAssignment);
            evaluateAssignmentDTO.setEvaluateAssignmentDetailsDTO(evaluateAssignmentDetailMapper.toDto(evaluateAssignmentDetails));
        }
        return Optional.ofNullable(evaluateAssignmentDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluateAssignment : {}", id);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Phân công chấm điểm"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_assignment, "CONFIG_MENU_ITEM"));
        evaluateAssignmentRepository.deleteById(id);
    }

    @Override
    public Boolean createEvaluateAssignment(EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception {
        validateBeforeEvaluateAssignment(evaluateAssignmentDTO);
        Long userId = userService.getUserIdLogin();

        EvaluateAssignment evaluateAssignment = evaluateAssignmentMapper.toEntity(evaluateAssignmentDTO);
        evaluateAssignment.setEvaluateStatus("1");
        evaluateAssignment.setCreateDatetime(new Date());
        evaluateAssignment.setUpdateDatetime(new Date());
        evaluateAssignment.setCreateUser(userId);
        if(evaluateAssignmentDTO.getLstUserDTO() !=null && !evaluateAssignmentDTO.getLstUserDTO().isEmpty()) {
            List<EvaluateAssignmentDetail> lstEvaluateAssignmentDetail = evaluateAssignmentDTO.getLstUserDTO().stream().map(userDTO -> {
                EvaluateAssignmentDetail evaluateAssignmentDetail = new EvaluateAssignmentDetail();
                evaluateAssignmentDetail.setUserId(userDTO.getId());
                evaluateAssignmentDetail.setEvaluateAssignment(evaluateAssignment);
                return evaluateAssignmentDetail;
            }).collect(Collectors.toList());
            evaluateAssignment.setEvaluateAssignmentDetails(lstEvaluateAssignmentDetail);
        }
        evaluateAssignmentRepository.save(evaluateAssignment);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới: Phân công nhân sự gọi ra"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_assignment, "CONFIG_MENU_ITEM"));
        return true;
    }

    @Override
    public Boolean updateEvaluateAssignment(EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception {
        EvaluateAssignment evaluateAssignment = evaluateAssignmentRepository.findById(evaluateAssignmentDTO.getId()).orElseThrow(
            () -> new BusinessException("101", Translator.toLocale("evaluate.assign.not.found"))
        );
        validateBeforeEvaluateAssignment(evaluateAssignmentDTO);
        Long userId = userService.getUserIdLogin();
        evaluateAssignment.setTotalUserId(evaluateAssignmentDTO.getTotalUserId());
        evaluateAssignment.setTotalCall(evaluateAssignmentDTO.getTotalCall());
        evaluateAssignment.setStartDate(evaluateAssignmentDTO.getStartDate());
        evaluateAssignment.setEndDate(evaluateAssignmentDTO.getEndDate());
        evaluateAssignment.setBusinessTypeId(evaluateAssignmentDTO.getBusinessTypeId());
        evaluateAssignment.setUpdateDatetime(new Date());
        evaluateAssignment.setUpdateUser(userId);
        List<EvaluateAssignmentDetail> evaluateAssignmentDetailsNew = new ArrayList<>();
        List<EvaluateAssignmentDetail> evaluateAssignmentDetails = evaluateAssignment.getEvaluateAssignmentDetails();
        evaluateAssignmentDTO.getLstUserDTO().forEach(userDTO -> {
            EvaluateAssignmentDetail evaluateAssignmentDetail = evaluateAssignmentDetails.stream().filter(tmp -> tmp.getUserId().equals(userDTO.getId()))
                .findFirst().orElse(null);
            if(evaluateAssignmentDetail == null) {
                evaluateAssignmentDetail = new EvaluateAssignmentDetail();
                evaluateAssignmentDetail.setUserId(userDTO.getId());
                evaluateAssignmentDetail.setEvaluateAssignment(evaluateAssignment);
            }
            evaluateAssignmentDetailsNew.add(evaluateAssignmentDetail);
        });
        evaluateAssignmentDetails.clear();
        evaluateAssignmentDetails.addAll(evaluateAssignmentDetailsNew);
        evaluateAssignment.setEvaluateAssignmentDetails(evaluateAssignmentDetails);
        evaluateAssignmentRepository.save(evaluateAssignment);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật: Phân công nhân sự gọi ra"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_assignment, "CONFIG_MENU_ITEM"));
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateAssignmentDataDTO> getAllEvaluateAssignments(EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception {
        Page<Object[]> page = evaluateAssignmentRepository.getAllEvaluateAssignments(
            evaluateAssignmentDTO.getChannelId() == null ? "-1" : evaluateAssignmentDTO.getChannelId(),
            evaluateAssignmentDTO.getEvaluaterId() == null  ? -1 : evaluateAssignmentDTO.getEvaluaterId(),
            evaluateAssignmentDTO.getBusinessTypeId() == null ? -1 : evaluateAssignmentDTO.getBusinessTypeId(),
            evaluateAssignmentDTO.getUserId() == null  ? -1 : evaluateAssignmentDTO.getUserId(),
            evaluateAssignmentDTO.getStartDate() == null ? null : DateUtil.dateToStringDateVN(evaluateAssignmentDTO.getStartDate()),
            evaluateAssignmentDTO.getEndDate() == null ? null : DateUtil.dateToStringDateVN(evaluateAssignmentDTO.getEndDate()),
            pageable);
        List<Object[]> objectLst = page.getContent();
        List<EvaluateAssignmentDataDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateAssignmentDataDTO evaluateAssignmentDataDTO = new EvaluateAssignmentDataDTO();
            int index = -1;
            evaluateAssignmentDataDTO.setId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setChannelId(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setEvaluaterId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setBusinessTypeId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setTotalUserId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setEvaluaterName(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setNames(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setTotalCall(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setStartDate(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setEndDate(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setUpdateUser(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setCreateUserName(DataUtil.safeToString(object[++index]));
            lstResults.add(evaluateAssignmentDataDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    public Page<EvaluateAssignmentSearchDTO> search(EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception {
        Page<Object[]> page = evaluateAssignmentRepository.search(evaluateAssignmentDTO.getChannelId() == null ? "-1" : evaluateAssignmentDTO.getChannelId() ,
            evaluateAssignmentDTO.getEvaluaterId() == null  ? -1 : evaluateAssignmentDTO.getEvaluaterId(),
            evaluateAssignmentDTO.getUserId() == null  ? -1 : evaluateAssignmentDTO.getUserId(),
            evaluateAssignmentDTO.getCreateUser(),
            evaluateAssignmentDTO.getEvaluateStatus() == null  ? null : evaluateAssignmentDTO.getEvaluateStatus(),
            DateUtil.dateToStringDateVN(evaluateAssignmentDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateAssignmentDTO.getEndDate()),
            pageable);
        List<Object[]> objectLst = page.getContent();
        List<EvaluateAssignmentSearchDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateAssignmentSearchDTO evaluateAssignmentDataDTO = new EvaluateAssignmentSearchDTO();
            int index = -1;
            evaluateAssignmentDataDTO.setId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setChannelId(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setEvaluaterId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setIds(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setTotalUserId(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setEvaluaterName(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setNames(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setTotalCalled(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setEvaluateStatus(DataUtil.safeToString(object[++index]));
            evaluateAssignmentDataDTO.setTotalCall(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setStartDate(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setEndDate(DataUtil.safeToDate(object[++index]));
            evaluateAssignmentDataDTO.setUpdateUser(DataUtil.safeToLong(object[++index]));
            evaluateAssignmentDataDTO.setCreateUserName(DataUtil.safeToString(object[++index]));
            lstResults.add(evaluateAssignmentDataDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    public void validateBeforeEvaluateAssignment(EvaluateAssignmentDTO evaluateAssignmentDTO) {
        if(evaluateAssignmentDTO.getStartDate() == null) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.startDate.null"));
        }
        if(evaluateAssignmentDTO.getEndDate() == null) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.endDate.null"));
        }

        if(evaluateAssignmentDTO.getStartDate().after(evaluateAssignmentDTO.getEndDate())) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.startDate.after.endDate"));
        }
        if(evaluateAssignmentDTO.getChannelId() == null || (!Constants.CHANNEL_TYPE.IN.equals(evaluateAssignmentDTO.getChannelId()) &&
             !Constants.CHANNEL_TYPE.OUT.equals(evaluateAssignmentDTO.getChannelId()))) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.channelId.format"));
        }
        if(evaluateAssignmentDTO.getEvaluaterId() == null) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.evaluater.null"));
        }
        if(DataUtil.isNullOrEmpty(evaluateAssignmentDTO.getLstUserDTO())) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.listTDV.null"));
        }
        if(evaluateAssignmentDTO.getTotalUserId() == null) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.totalTDV.null"));
        }
        if(evaluateAssignmentDTO.getTotalUserId().intValue() !=  evaluateAssignmentDTO.getLstUserDTO().size()) {
            throw new BusinessException("101", Translator.toLocale("evaluate.assign.not.equal"));
        }
    }
}
