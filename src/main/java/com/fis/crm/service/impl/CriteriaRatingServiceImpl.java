package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.CriteriaRating;
import com.fis.crm.domain.User;
import com.fis.crm.repository.CriteriaRatingRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CriteriaRatingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CriteriaRatingDTO;
import com.fis.crm.service.mapper.CriteriaRatingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CriteriaRatingServiceImpl implements CriteriaRatingService {

    private final Logger log = LoggerFactory.getLogger(CriteriaRatingServiceImpl.class);

    private final CriteriaRatingRepository criteriaRatingRepository;

    private final CriteriaRatingMapper criteriaRatingMapper;

    private final UserService userService;
    private final ActionLogService actionLogService;

    public CriteriaRatingServiceImpl(CriteriaRatingRepository criteriaRatingRepository, CriteriaRatingMapper criteriaRatingMapper, UserService userService, ActionLogService actionLogService) {
        this.criteriaRatingRepository = criteriaRatingRepository;
        this.criteriaRatingMapper = criteriaRatingMapper;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }


    @Override
    public CriteriaRatingDTO save(CriteriaRatingDTO criteriaRatingDTO) {
        log.debug("Request to save Criteria rating ", criteriaRatingDTO);
        Instant instant = Instant.now();
        criteriaRatingDTO.setCreateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaRatingDTO.setCreateUser(user.get().getId());
        criteriaRatingDTO.setStatus("1");
        CriteriaRating criteriaRating = criteriaRatingMapper.toEntity(criteriaRatingDTO);
        criteriaRating = criteriaRatingRepository.save(criteriaRating);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.INSERT + "",
            null, String.format("Thêm mới: Cấu hình xếp loại đánh giá [%s]", criteriaRatingDTO.getName()),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_rating, "CONFIG_MENU_ITEM"));
        return criteriaRatingMapper.toDto(criteriaRating);
    }

    @Override
    public CriteriaRatingDTO update(CriteriaRatingDTO criteriaRatingDTO) {
        log.debug("Request to update Criteria rating ", criteriaRatingDTO);
        Instant instant = Instant.now();
        criteriaRatingDTO.setUpdateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaRatingDTO.setUpdateUser(user.get().getId());
        criteriaRatingDTO.setStatus("1");
        CriteriaRating criteriaRating = criteriaRatingMapper.toEntity(criteriaRatingDTO);
        criteriaRating = criteriaRatingRepository.save(criteriaRating);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Cập nhật: Cấu hình xếp loại đánh giá [%s]", criteriaRatingDTO.getName()),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_rating, "CONFIG_MENU_ITEM"));
        return criteriaRatingMapper.toDto(criteriaRating);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CriteriaRatingDTO> findAll(Pageable pageable) {
        log.debug("Request to find all Criteria Rating");
        return criteriaRatingRepository.findAll(pageable)
            .map(criteriaRatingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CriteriaRatingDTO> findOne(Long id) {
        log.debug("Request to find one Criteria Rating ");
        return criteriaRatingRepository.findById(id)
            .map(criteriaRatingMapper::toDto);
    }

    @Override
    public CriteriaRatingDTO delete(CriteriaRatingDTO criteriaRatingDTO) {
        log.debug("Request to delete Criteria Rating");
        Instant instant = Instant.now();
        criteriaRatingDTO.setUpdateDatetime(instant);
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaRatingDTO.setUpdateUser(user.get().getId());
        criteriaRatingDTO.setStatus("2");
        CriteriaRating criteriaRating = criteriaRatingMapper.toEntity(criteriaRatingDTO);
        criteriaRating = criteriaRatingRepository.save(criteriaRating);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.DELETE + "",
            null, String.format("Xóa: Cấu hình xếp loại đánh giá [%s]", criteriaRatingDTO.getName()),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.criteria_rating, "CONFIG_MENU_ITEM"));
        return criteriaRatingMapper.toDto(criteriaRating);
    }

    @Override
    public boolean checkValue(CriteriaRatingDTO criteriaRatingDTO) {
        if (criteriaRatingDTO.getFromScores() > criteriaRatingDTO.getToScores()) {
            return true;
        }
//        List<CriteriaRatingDTO> criteriaRatingDTOS = criteriaRatingRepository.findAll()
//            .stream()
//            .map(criteriaRating -> criteriaRatingMapper.toDto(criteriaRating))
//            .collect(Collectors.toList());
//        if (criteriaRatingDTO.getId() != null) {
//            System.out.println("a" + criteriaRatingDTOS.toString());
//            List<CriteriaRatingDTO> toRemove = new ArrayList<>();
//            for (CriteriaRatingDTO dto : criteriaRatingDTOS) {
//                if (dto != null && dto.getId().equals(criteriaRatingDTO.getId())) {
//                    toRemove.add(dto);
//                }
//            }
//
//            if (!toRemove.isEmpty()) {
//                criteriaRatingDTOS.removeAll(toRemove);
//            }
//
//            criteriaRatingDTOS.remove(criteriaRatingDTO);
//            System.out.println("b" + criteriaRatingDTOS.toString());
//        }
//        for (CriteriaRatingDTO criteriaRatingDTO1 : criteriaRatingDTOS) {
//            if (criteriaRatingDTO.getName().equalsIgnoreCase(criteriaRatingDTO1.getName())) {
//                return true;
//            } else {
//                if (criteriaRatingDTO.getFromScores() >= criteriaRatingDTO1.getFromScores() && criteriaRatingDTO.getFromScores() <= criteriaRatingDTO1.getToScores()
//                    || criteriaRatingDTO.getToScores() >= criteriaRatingDTO1.getFromScores() && criteriaRatingDTO.getToScores() <= criteriaRatingDTO1.getToScores()
//                    || criteriaRatingDTO.getFromScores() <= criteriaRatingDTO1.getFromScores() && criteriaRatingDTO.getToScores() >= criteriaRatingDTO1.getToScores()) {
//                    return true;
//                } else {
//                    if (criteriaRatingDTO.getFromScores() > criteriaRatingDTO1.getToScores() || criteriaRatingDTO.getToScores() < criteriaRatingDTO1.getFromScores()) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return false;
        return checkDuplicatevalue(criteriaRatingDTO.getId(), criteriaRatingDTO.getFromScores(), criteriaRatingDTO.getToScores());
    }

    @Override
    public Integer checkValid(CriteriaRatingDTO criteriaRatingDTO) {

        List<CriteriaRating> criteriaRatings = criteriaRatingRepository.findAll();
        if (criteriaRatingDTO.getId() != null) {
            List<CriteriaRating> toRemove = new ArrayList<>();
            for (CriteriaRating ett : criteriaRatings) {
                if (ett != null && ett.getId().equals(criteriaRatingDTO.getId())) {
                    toRemove.add(ett);
                }
            }
            if (!toRemove.isEmpty()) {
                criteriaRatings.removeAll(toRemove);
            }
            criteriaRatings.remove(criteriaRatingDTO);

            for (int i = 0; i < criteriaRatings.size() - 1; i++) {
                if (criteriaRatingDTO.getName().equalsIgnoreCase(criteriaRatings.get(i).getName())) {
                    return 4;
                }
            }
        }
        if (criteriaRatings.size() < 1) {
            return 3;
        } else {
            if (criteriaRatingDTO.getToScores() < criteriaRatings.get(0).getFromScores()) {
                return 3;
            }
            if (criteriaRatingDTO.getFromScores() > criteriaRatings.get(criteriaRatings.size() - 1).getToScores()) {
                return 3;
            }
            for (int i = 0; i < criteriaRatings.size(); i++) {
                if (criteriaRatingDTO.getFromScores() >= criteriaRatings.get(i).getFromScores()
                    && criteriaRatingDTO.getFromScores() <= criteriaRatings.get(i).getToScores()) {
                    return 1;
                }
                if (criteriaRatingDTO.getToScores() >= criteriaRatings.get(i).getFromScores()
                    && criteriaRatingDTO.getToScores() <= criteriaRatings.get(i).getToScores()) {
                    return 2;
                }
            }
            for (int i = 0; i < criteriaRatings.size() - 1; i++) {
                if (criteriaRatingDTO.getToScores() >= criteriaRatings.get(i).getToScores()
                    && criteriaRatingDTO.getToScores() <= criteriaRatings.get(i + 1).getFromScores()
                    && criteriaRatingDTO.getFromScores() >= criteriaRatings.get(i).getToScores()
                    && criteriaRatingDTO.getToScores() <= criteriaRatings.get(i + 1).getFromScores()) {
                    return 3;
                }
            }
            return 1;
        }
    }

    public Integer checkFromTo() {

        return null;
    }

    //true: pass - false: failed
    public Boolean checkDuplicatevalue(Long criteriaRatingId, Double fromScore, Double toScore) {
        List<CriteriaRating> crLst = criteriaRatingRepository.checkDuplicateValue(fromScore, toScore);
        if (crLst.size() == 0) {
            return true;
        }
        if (crLst.size() >= 2) {
            return false;
        }
        if (criteriaRatingId == null) {
            return false;
        }
        CriteriaRating cr = crLst.get(0);
        if (cr.getId() == criteriaRatingId) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkName(String name) {
        CriteriaRating criteriaRating = criteriaRatingRepository.findByName(name);
        if (criteriaRating == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<List<CriteriaRatingDTO>> findAllCriteriaRating() {
        return criteriaRatingRepository.findByStatusOrderByNameAsc(Constants.STATUS_ACTIVE).map(criteriaRatingMapper::toDto);
    }
}
