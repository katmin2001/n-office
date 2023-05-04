package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CriteriaDetail;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CriteriaDetailRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CriteriaDetailService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CriteriaDetailDTO;
import com.fis.crm.service.dto.CriteriaScoresDTO;
import com.fis.crm.service.mapper.CriteriaDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CriteriaDetailServiceImpl implements CriteriaDetailService {

    private final Logger log = LoggerFactory.getLogger(CriteriaDetailServiceImpl.class);

    private final CriteriaDetailRepository criteriaDetailRepository;

    private final CriteriaDetailMapper criteriaDetailMapper;

    private final UserService userService;
    private final ActionLogService actionLogService;

    public CriteriaDetailServiceImpl(CriteriaDetailRepository criteriaDetailRepository, CriteriaDetailMapper criteriaDetailMapper, UserService userService, ActionLogService actionLogService) {
        this.criteriaDetailRepository = criteriaDetailRepository;
        this.criteriaDetailMapper = criteriaDetailMapper;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public CriteriaDetailDTO save(CriteriaDetailDTO criteriaDetailDTO) {
        log.debug("Request to save Criteria Detail ", criteriaDetailDTO);
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        criteriaDetailDTO.setCreateDatetime(instant);
        criteriaDetailDTO.setUpdateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaDetailDTO.setCreateUser(user.get().getId());
        criteriaDetailDTO.setUpdateUser(user.get().getId());
        criteriaDetailDTO.setStatus("1");
        CriteriaDetail criteriaDetail = criteriaDetailMapper.toEntity(criteriaDetailDTO);
        criteriaDetail = criteriaDetailRepository.save(criteriaDetail);
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (criteriaDetailDTO.getId() != null){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Cập nhật: Nội dung đánh giá"),
                new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                null, String.format("Thêm mới: Nội dung đánh giá"),
                new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
        }
        return criteriaDetailMapper.toDto(criteriaDetail);
    }

    @Override
    public CriteriaDetailDTO update(CriteriaDetailDTO criteriaDetailDTO) {
        log.debug("Request to update Criteria Detail ", criteriaDetailDTO);
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        criteriaDetailDTO.setUpdateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaDetailDTO.setUpdateUser(user.get().getId());
        criteriaDetailDTO.setStatus("1");
        CriteriaDetail criteriaDetail = criteriaDetailMapper.toEntity(criteriaDetailDTO);
        criteriaDetail = criteriaDetailRepository.save(criteriaDetail);
        return criteriaDetailMapper.toDto(criteriaDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CriteriaDetailDTO> findAll(Pageable pageable) {
        log.debug("Request to find all CriteriaDetail");
        return criteriaDetailRepository.findAll(pageable)
            .map(criteriaDetailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CriteriaDetailDTO> findOne(Long id) {
        log.debug("Request to find one Criteria Detail");
        return criteriaDetailRepository.findById(id)
            .map(criteriaDetailMapper::toDto);
    }

    @Override
    public CriteriaDetailDTO findOneByName(String name) {
        log.debug("Request to find one Criteria Detail");
        CriteriaDetail criteriaDetail = criteriaDetailRepository.findByContent(name);
        CriteriaDetailDTO criteriaDetailDTO = criteriaDetailMapper.toDto(criteriaDetail);
        return criteriaDetailDTO;
    }

    @Override
    public CriteriaDetailDTO delete(CriteriaDetailDTO criteriaDetailDTO) {
        log.debug("Request to update Criteria Detail ", criteriaDetailDTO);
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        criteriaDetailDTO.setUpdateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaDetailDTO.setUpdateUser(user.get().getId());
        criteriaDetailDTO.setStatus("2");
        CriteriaDetail criteriaDetail = criteriaDetailMapper.toEntity(criteriaDetailDTO);
        criteriaDetail = criteriaDetailRepository.save(criteriaDetail);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Nội dung đánh giá"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
        return criteriaDetailMapper.toDto(criteriaDetail);
    }

    @Override
    public boolean checkValue(String content) {

        CriteriaDetail criteriaDetail = criteriaDetailRepository.findByContent(content);
        if (criteriaDetail == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CriteriaScoresDTO getScores(Long id) {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<CriteriaScoresDTO> lstResult = new ArrayList<>();
        try {
            lst = criteriaDetailRepository.getScores(id);
            for (Object[] obj1 : lst){
                CriteriaScoresDTO criteriaScoresDTO = new CriteriaScoresDTO();
                criteriaScoresDTO.setScores(DataUtil.safeToDouble(obj1[0]));
                lstResult.add(criteriaScoresDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Double total = 0.0;
        CriteriaScoresDTO criteriaScoresDTO = new CriteriaScoresDTO();
        for (CriteriaScoresDTO scores: lstResult){
            total += scores.getScores();
        }
        criteriaScoresDTO.setScores(total);
        return criteriaScoresDTO;
    }
}
