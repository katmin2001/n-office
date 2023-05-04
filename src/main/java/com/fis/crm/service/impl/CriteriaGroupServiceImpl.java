package com.fis.crm.service.impl;

import com.fis.crm.commons.CheckCharacterUtil;
import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Criteria;
import com.fis.crm.domain.CriteriaGroup;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CriteriaDetailRepository;
import com.fis.crm.repository.CriteriaGroupRepository;
import com.fis.crm.repository.CriteriaRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CriteriaGroupService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CriteriaDetailMapper;
import com.fis.crm.service.mapper.CriteriaGroupMapper;
import com.fis.crm.service.mapper.CriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CriteriaGroupServiceImpl implements CriteriaGroupService {

    private final Logger log = LoggerFactory.getLogger(CriteriaGroupServiceImpl.class);

    private final CriteriaGroupRepository criteriaGroupRepository;

    private final CriteriaGroupMapper criteriaGroupMapper;

    private final CriteriaMapper criteriaMapper;

    private final CriteriaRepository criteriaRepository;

    private final CriteriaDetailRepository criteriaDetailRepository;

    private final CriteriaDetailMapper criteriaDetailMapper;

    private final UserService userService;
    private final ActionLogService actionLogService;

    public CriteriaGroupServiceImpl(CriteriaGroupRepository criteriaGroupRepository, CriteriaGroupMapper criteriaGroupMapper, CriteriaMapper criteriaMapper, CriteriaRepository criteriaRepository, CriteriaDetailRepository criteriaDetailRepository, CriteriaDetailMapper criteriaDetailMapper, UserService userService, ActionLogService actionLogService) {
        this.criteriaGroupRepository = criteriaGroupRepository;
        this.criteriaGroupMapper = criteriaGroupMapper;
        this.criteriaMapper = criteriaMapper;
        this.criteriaRepository = criteriaRepository;
        this.criteriaDetailRepository = criteriaDetailRepository;
        this.criteriaDetailMapper = criteriaDetailMapper;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }


    @Override
    public CriteriaGroupDTO save(CriteriaGroupDTO criteriaGroupDTO) {
        log.debug("Request to save Criteria group ", criteriaGroupDTO);
        Optional<User> user = userService.getUserWithAuthorities();
        CriteriaGroup criteriaGroup = criteriaGroupMapper.toEntity(criteriaGroupDTO);
        criteriaGroup = criteriaGroupRepository.save(criteriaGroup);
        Optional<User> userLog = userService.getUserWithAuthorities();
        if ("2".equals(criteriaGroupDTO.getStatus())){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
                null, String.format("Xóa: Cấu hình nhóm tiêu chí [%s]", criteriaGroupDTO.getName()),
                new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_group, "CONFIG_MENU_ITEM"));
        } else {
            if (criteriaGroupDTO.getId() != null){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    null, String.format("Cập nhật: Cấu hình nhóm tiêu chí [%s]", criteriaGroupDTO.getName()),
                    new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_group, "CONFIG_MENU_ITEM"));
            } else {
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                    null, String.format("Thêm mới: Cấu hình nhóm tiêu chí [%s]", criteriaGroupDTO.getName()),
                    new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_group, "CONFIG_MENU_ITEM"));
            }
        }
        return criteriaGroupMapper.toDto(criteriaGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CriteriaGroupDTO> findAll(String search, Pageable pageable) {
        log.debug("Request to get all Criteria group");
        if (search == null) {
            return criteriaGroupRepository.findAll(pageable)
                .map(criteriaGroupMapper::toDto);
        }
        if (CheckCharacterUtil.checkNumber(search)) {
            return criteriaGroupRepository.searchCriteriaGroup("%" + search + "%", Double.valueOf(search), pageable)
                .map(criteriaGroupMapper::toDto);
        }
        return criteriaGroupRepository.searchCriteriaGroup("%" + search + "%", (double) 0, pageable)
            .map(criteriaGroupMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public List<CriteriaGroupDTO> findAll() {
        log.debug("Request to get all Criteria group");
        return criteriaGroupRepository.findAllByStatus().stream()
            .map(criteriaGroup -> criteriaGroupMapper.toDto(criteriaGroup))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CriteriaGroupDTO> findOne(Long id) {
        log.debug("Request to get one CriteriaGroup", id);
        return criteriaGroupRepository.findById(id)
            .map(criteriaGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CriteriaGroup");
        criteriaGroupRepository.deleteById(id);
    }

    @Override
    public boolean checkName(String name) {
        CriteriaGroup criteriaGroup = criteriaGroupRepository.findByName(name);
        if (criteriaGroup == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CriteriaGroupDTO>> findCriteriaGroupDetail() {
        Optional<List<CriteriaGroupDTO>> optionalCriteriaGroupDTOS = criteriaGroupRepository.findAllByStatus(Constants.STATUS_ACTIVE).map(criteriaGroupMapper::toDto);

        if (optionalCriteriaGroupDTOS.isPresent()) {
            List<Long> lstCriteriaGroupId = optionalCriteriaGroupDTOS.get().stream().map(criteriaGroupDTO -> criteriaGroupDTO.getId()).collect(Collectors.toList());
            Optional<List<CriteriaDTO>> criteriaDTOS = criteriaRepository.findByCriteriaGroupIdInAndStatus(lstCriteriaGroupId, Constants.STATUS_ACTIVE).map(criteriaMapper::toDto);
            Optional<List<CriteriaDetailDTO>> optionalCriteriaDetails = Optional.empty();
            if (criteriaDTOS.isPresent()) {
                List<Long> lstCriteriaId = criteriaDTOS.get().stream().map(criteriaDTO -> criteriaDTO.getId()).collect(Collectors.toList());
                optionalCriteriaDetails = criteriaDetailRepository.findByCriteriaIdInAndStatus(lstCriteriaId, Constants.STATUS_ACTIVE).map(criteriaDetailMapper::toDto);
            }
            Optional<List<CriteriaDetailDTO>> finalOptionalCriteriaDetails = optionalCriteriaDetails;
            return Optional.of(optionalCriteriaGroupDTOS.get().stream().map(criteriaGroupDTO -> {
                criteriaGroupDTO.setLstCriteriaDTO(criteriaDTOS.orElse(new ArrayList<>()).stream().filter(criteriaDTO -> criteriaDTO.getCriteriaGroupId().equals(criteriaGroupDTO.getId())).collect(Collectors.toList()));
                if (finalOptionalCriteriaDetails.isPresent()) {
                    criteriaGroupDTO.getLstCriteriaDTO().forEach(criteriaDTO -> {
                        criteriaDTO.setCriteriaDetailDTOList(finalOptionalCriteriaDetails.orElse(new ArrayList<>()).stream().filter(
                            criteriaDetailDTO -> criteriaDetailDTO.getCriteriaId().equals(criteriaDTO.getId())).collect(Collectors.toList()));
                    });
                }
                return criteriaGroupDTO;
            }).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    public Double getRemainingScore(Long criteriaGroupId, Long criteriaId) {
        List<Criteria> criteriaList = criteriaRepository.findByCriteriaGroupIdAndStatus(criteriaGroupId, Constants.STATUS_STILL_VALIDATED);
        List<Criteria> criteriaListExpire = criteriaRepository.findByCriteriaGroupIdAndStatus(criteriaGroupId, Constants.STATUS_EXPIRE);
        Optional<CriteriaGroup> criteriaGroupOptional = criteriaGroupRepository.findById(criteriaGroupId);
        DecimalFormat f = new DecimalFormat("##.0000");
        if (criteriaGroupOptional.isPresent()) {
            Double score = criteriaGroupOptional.get().getScores();
            if (!criteriaList.isEmpty()) {
                for (Criteria criteria : criteriaList) {
                    if (DataUtil.isNullOrEmpty(criteriaId)) {
                        score -= criteria.getScores();
                    } else {
                        if (!criteria.getId().equals(criteriaId)) {
                            score -= criteria.getScores();
                        }
                    }
                }
            }
//            if (!criteriaListExpire.isEmpty()) {
//                for (Criteria criteria : criteriaListExpire) {
//                    if (DataUtil.isNullOrEmpty(criteriaId)) {
//                        score -= criteria.getScores();
//                    } else {
//                        if (!criteria.getId().equals(criteriaId)) {
//                            score -= criteria.getScores();
//                        }
//                    }
//                }
//            }
            return Double.valueOf(f.format(score));
        }
        return null;
    }

    @Override
    public List<CriteriaGroupLoadDTO> loadToCbx() {
        List<Object[]> lst = new ArrayList<Object[]>();
        List<CriteriaGroupLoadDTO> lstResult = new ArrayList<>();
        try {
            lst = criteriaGroupRepository.loadCriteriaGroupToCbx();
            for (Object[] obj1 : lst) {
                CriteriaGroupLoadDTO criteriaGroupLoadDTO = new CriteriaGroupLoadDTO();
                criteriaGroupLoadDTO.setId(DataUtil.safeToLong(obj1[0]));
                criteriaGroupLoadDTO.setName(DataUtil.safeToString(obj1[1]));
                lstResult.add(criteriaGroupLoadDTO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstResult;
    }
}
