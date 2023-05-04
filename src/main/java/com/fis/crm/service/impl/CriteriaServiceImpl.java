package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Criteria;
import com.fis.crm.domain.CriteriaDetail;
import com.fis.crm.domain.CriteriaGroup;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CriteriaDetailRepository;
import com.fis.crm.repository.CriteriaGroupRepository;
import com.fis.crm.repository.CriteriaRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CriteriaGroupService;
import com.fis.crm.service.CriteriaService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CriteriaDTO;
import com.fis.crm.service.dto.CriteriaDetailDTO;
import com.fis.crm.service.mapper.CriteriaDetailMapper;
import com.fis.crm.service.mapper.CriteriaMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaRepository criteriaRepository;
    private final CriteriaMapper criteriaMapper;
    private final CriteriaDetailRepository criteriaDetailRepository;
    private final CriteriaDetailMapper criteriaDetailMapper;
    private final UserRepository userRepository;
    private final CriteriaGroupRepository criteriaGroupRepository;
    private final CriteriaGroupService criteriaGroupService;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public CriteriaServiceImpl(CriteriaRepository criteriaRepository,
                               CriteriaMapper criteriaMapper,
                               CriteriaDetailRepository criteriaDetailRepository,
                               CriteriaDetailMapper criteriaDetailMapper,
                               UserRepository userRepository, CriteriaGroupRepository criteriaGroupRepository, CriteriaGroupService criteriaGroupService, UserService userService, ActionLogService actionLogService) {
        this.criteriaRepository = criteriaRepository;
        this.criteriaMapper = criteriaMapper;
        this.criteriaDetailRepository = criteriaDetailRepository;
        this.criteriaDetailMapper = criteriaDetailMapper;
        this.userRepository = userRepository;
        this.criteriaGroupRepository = criteriaGroupRepository;
        this.criteriaGroupService = criteriaGroupService;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<CriteriaDTO> search(String status, Long criteriaGroupId, String keyWord, Pageable pageable) {
        try {
            Page<Object[]> page = criteriaRepository.search(status, criteriaGroupId, keyWord, pageable);
            List<Object[]> objectLst = page.getContent();
            List<CriteriaDTO> criteriaDTOList = new ArrayList<>();
            for (Object[] object : objectLst) {
                CriteriaDTO dto = new CriteriaDTO();
                int index = -1;
                dto.setId(DataUtil.safeToLong(object[++index]));
                dto.setCriteriaGroupId(DataUtil.safeToLong(object[++index]));
                dto.setName(DataUtil.safeToString(object[++index]));
                dto.setScores(DataUtil.safeToDouble(object[++index]));
                dto.setStatus(DataUtil.safeToString(object[++index]));
                dto.setCreateDatetime(DataUtil.safeToInstant(object[++index]));
                dto.setCreateUser(DataUtil.safeToLong(object[++index]));
                dto.setUpdateDatetime(DataUtil.safeToInstant(object[++index]));
                dto.setUpdateUser(DataUtil.safeToLong(object[++index]));
                List<CriteriaDetail> criteriaDetailList = criteriaDetailRepository.findByCriteriaIdAndStatus(dto.getId(), Constants.STATUS_STILL_VALIDATED);
                List<CriteriaDetailDTO> criteriaDetailDTOList = criteriaDetailMapper.toDto(criteriaDetailList);
                dto.setCriteriaDetailDTOList(criteriaDetailDTOList);
                criteriaDTOList.add(dto);
            }

            return new PageImpl<>(criteriaDTOList, pageable, page.getTotalElements());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CriteriaDTO save(CriteriaDTO criteriaDTO) {
        Boolean isUpdate;
        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        Long userId = null;

        if (optionalUser.isPresent()) {
            userId = optionalUser.get().getId();
        }
        if (DataUtil.isNullOrEmpty(criteriaDTO.getId()) || criteriaDTO.getId().equals(-1L)) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        Long check;
        if (isUpdate) {
            check = criteriaRepository.checkExistsCriteria(criteriaDTO.getCriteriaGroupId(), criteriaDTO.getName().trim(), Math.toIntExact(criteriaDTO.getId()));
        } else {
            check = criteriaRepository.checkExistsCriteria(criteriaDTO.getCriteriaGroupId(), criteriaDTO.getName().trim(), -1);
        }
        if (check > 0) {
            throw new BadRequestAlertException("Tiêu chí đã tồn tại", "", "");
        }
        Criteria criteria = criteriaMapper.toEntity(criteriaDTO);
        if (isUpdate) {
            Optional<Criteria> optional = criteriaRepository.findById(criteriaDTO.getId());
            if (optional.isPresent()) {
                criteria.setCreateUser(optional.get().getCreateUser());
                criteria.setCreateDatetime(optional.get().getCreateDatetime());
            }
            criteria.setStatus(Constants.STATUS_STILL_VALIDATED);
            criteria.setUpdateDatetime(Instant.now());
            criteria.setUpdateUser(userId);
        } else {
            criteria.setStatus(Constants.STATUS_STILL_VALIDATED);
            criteria.setCreateDatetime(Instant.now());
            criteria.setCreateUser(userId);
        }
        try {
            criteria = criteriaRepository.save(criteria);
            Optional<User> userLog = userService.getUserWithAuthorities();
            if (criteriaDTO.getId() != null){
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                    null, String.format("Cập nhật: Tiêu chí đánh giá [%s]", criteriaDTO.getName()),
                    new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
            } else {
                actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
                    null, String.format("Thêm mới: Tiêu chí đánh giá [%s]", criteriaDTO.getName()),
                    new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
            }

            return criteriaMapper.toDto(criteria);
        } catch (Exception e) {
            if (isUpdate) {
                throw new BadRequestAlertException("Cập nhật thất bại", "", "");
            } else {
                throw new BadRequestAlertException("Thêm mới thất bại", "", "");
            }
        }
    }

    @Override
    public CriteriaDTO changeStatus(Long id, Boolean isDelete) {
        Optional<Criteria> criteriaOptional = criteriaRepository.findById(id);
        Optional<User> optionalUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        Long userId = null;
        if (optionalUser.isPresent()) {
            userId = optionalUser.get().getId();
        }
        if (criteriaOptional.isPresent()) {
            if (isDelete) {
                criteriaOptional.get().setStatus(Constants.STATUS_INACTIVE.toString());
                criteriaOptional.get().setUpdateDatetime(Instant.now());
                criteriaOptional.get().setUpdateUser(userId);
            } else if (criteriaOptional.get().getStatus().equals(Constants.STATUS_STILL_VALIDATED)) {
                criteriaOptional.get().setStatus(Constants.STATUS_EXPIRE);
                criteriaOptional.get().setUpdateDatetime(Instant.now());
                criteriaOptional.get().setUpdateUser(userId);
            } else if (criteriaOptional.get().getStatus().equals(Constants.STATUS_EXPIRE)) {
                Double score = criteriaOptional.get().getScores();
                Optional<CriteriaGroup> criteriaGroupOptional = criteriaGroupRepository.findById(criteriaOptional.get().getCriteriaGroupId());
                if (criteriaGroupOptional.isPresent()) {
                    Double scoreGroup = criteriaGroupService.getRemainingScore(criteriaGroupOptional.get().getId(), null);
                    if (score <= scoreGroup) {
                        criteriaOptional.get().setStatus(Constants.STATUS_STILL_VALIDATED);
                        criteriaOptional.get().setUpdateDatetime(Instant.now());
                        criteriaOptional.get().setUpdateUser(userId);
                    } else {
                        throw new BadRequestAlertException("Chuyển hiệu lực thất bại", "", "");
                    }
                } else {
                    throw new BadRequestAlertException("Chuyển hiệu lực thất bại", "", "");
                }
            }
        }
        Criteria criteria = criteriaRepository.save(criteriaOptional.get());
        Optional<User> userLog = userService.getUserWithAuthorities();
        if (isDelete){
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
                null, String.format("Xóa: Tiêu chí đánh giá [%s]", criteria.getName()),
                new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
        } else {
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Chuyển hiệu lực: Tiêu chí đánh giá [%s]", criteria.getName()),
                new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria, "CONFIG_MENU_ITEM"));
        }
        return criteriaMapper.toDto(criteria);
    }

    @Override
    public List<CriteriaDTO> listCriteria() {
        return criteriaRepository.findAll().stream()
            .map(criteria -> criteriaMapper.toDto(criteria))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CriteriaDTO> findOne(Long id) {
        return criteriaRepository.findById(id)
            .map(criteria -> criteriaMapper.toDto(criteria));
    }

    @Override
    public List<CriteriaDTO> getListCriteriaByCriteriaGroupId(Long id) {
        List<CriteriaDTO> list = criteriaRepository.findByCriteriaGroupId(id).stream()
            .map(criteria -> criteriaMapper.toDto(criteria))
            .collect(Collectors.toList());

        for(CriteriaDTO dto:list){
            List<CriteriaDetail> criteriaDetailList = criteriaDetailRepository.findByCriteriaId(dto.getId());
            List<CriteriaDetailDTO> criteriaDetailDTOList = criteriaDetailMapper.toDto(criteriaDetailList);
            dto.setCriteriaDetailDTOList(criteriaDetailDTOList);
        }
        return list;
    }
}
