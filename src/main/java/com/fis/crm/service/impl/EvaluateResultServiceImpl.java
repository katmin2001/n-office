package com.fis.crm.service.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.*;
import com.fis.crm.repository.CampaignResourceDetailRepository;
import com.fis.crm.repository.EvaluateResultRepository;
import com.fis.crm.repository.TicketRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.*;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.EvaluateResultDTO;
import com.fis.crm.service.dto.EvaluateResultDetailDTO;
import com.fis.crm.service.dto.EvaluateResultRatingDTO;
import com.fis.crm.service.mapper.EvaluateResultDetailMapper;
import com.fis.crm.service.mapper.EvaluateResultMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EvaluateResult}.
 */
@Service
@Transactional
public class EvaluateResultServiceImpl implements EvaluateResultService {

    private final CampaignResourceDetailRepository campaignResourceDetailRepository;

    private final TicketRepository ticketRepository;

    private final Logger log = LoggerFactory.getLogger(EvaluateResultServiceImpl.class);

    private final EvaluateResultRepository evaluateResultRepository;

    private final EvaluateResultMapper evaluateResultMapper;
    private final EvaluateResultDetailMapper evaluateResultDetailMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    private final EvaluateResultHistoryService evaluateResultHistoryService;
    private final InOutBoundFactory inOutBoundFactory;
    private final ActionLogService actionLogService;

    public EvaluateResultServiceImpl(EvaluateResultRepository evaluateResultRepository, EvaluateResultMapper evaluateResultMapper, EvaluateResultDetailMapper evaluateResultDetailMapper, UserService userService, UserRepository userRepository, EvaluateResultHistoryService evaluateResultHistoryService, InOutBoundFactory inOutBoundFactory, ActionLogService actionLogService, CampaignResourceDetailRepository campaignResourceDetailRepository, TicketRepository ticketRepository) {
        this.evaluateResultRepository = evaluateResultRepository;
        this.evaluateResultMapper = evaluateResultMapper;
        this.evaluateResultDetailMapper = evaluateResultDetailMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.evaluateResultHistoryService = evaluateResultHistoryService;
        this.inOutBoundFactory = inOutBoundFactory;
        this.actionLogService = actionLogService;
        this.campaignResourceDetailRepository = campaignResourceDetailRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluateResultDTO> findOne(Long id, String type) {
        Long userId = userService.getUserIdLogin();
        EvaluateResult evaluateResult = null;
        if ("1".equals(type)) { //Danh gia lai
            evaluateResult = evaluateResultRepository.findByIdAndEvaluaterIdAndEvaluateStatusInAndStatus(id,
                userId,
                Arrays.asList(Constants.EVALUATE_RESULT_STATUS.EVALUATED, Constants.EVALUATE_RESULT_STATUS.RE_EVALUATE),
                Constants.STATUS_ACTIVE).orElseThrow(() -> new BusinessException("101", Translator.toLocale("evaluate.result.evaluated")));
        } else if ("2".equals(type)) { //Phan hoi
            evaluateResult = evaluateResultRepository.findByIdAndEvaluaterIdOrEvaluatedIdAndEvaluateStatusInAndStatus(id,
                userId,
                userId,
                Arrays.asList(Constants.EVALUATE_RESULT_STATUS.EVALUATED, Constants.EVALUATE_RESULT_STATUS.RE_EVALUATE),
                Constants.STATUS_ACTIVE).orElseThrow(() -> new BusinessException("101", Translator.toLocale("evaluate.result.evaluated")));
        } else {
            evaluateResult = evaluateResultRepository.findById(id).orElseThrow(() -> new BusinessException("101", Translator.toLocale("evaluate.result.evaluated")));
        }

        List<EvaluateResultDetailDTO> evaluateResultDetailDTOS = evaluateResult.getEvaluateResultDetails().stream().map(evaluateResultDetailMapper::toDto).collect(Collectors.toList());
        EvaluateResultDTO evaluateResultDTO = evaluateResultMapper.toDto(evaluateResult);
        evaluateResultDTO.setEvaluaterName(SecurityUtils.getCurrentUserLogin().get());
        Optional<User> optionalUser = userRepository.findById(evaluateResult.getEvaluatedId());

        evaluateResultDTO.setEvaluatedName(optionalUser.get().getFirstName());

        evaluateResultDTO.setRecordLink("/record/mp3/test");
        evaluateResultDTO.setEvaluateResultDetailDTOS(evaluateResultDetailDTOS);
        return Optional.of(evaluateResultDTO);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluateResult : {}", id);
        evaluateResultRepository.deleteById(id);
    }

    @Override
    public Boolean createEvaluateResult(EvaluateResultDTO evaluateResultDTO) throws Exception {
        log.debug("Request to save EvaluateResult : {}", evaluateResultDTO);
        Long userId = userService.getUserIdLogin();
        EvaluateResult evaluateResult = evaluateResultRepository.findByIdAndEvaluaterIdAndEvaluateStatusAndStatus(evaluateResultDTO.getId(), userId,
            Constants.EVALUATE_RESULT_STATUS.YET_EVALUATE, Constants.STATUS_ACTIVE).orElseThrow(() -> new BusinessException("101", Translator.toLocale("evaluate.result.evaluated")));
        evaluateResult.setEvaluaterId(userId);
        evaluateResult.setError1(evaluateResultDTO.getError1());
        evaluateResult.setError2(evaluateResultDTO.getError2());
        evaluateResult.setCriteriaRatingId(evaluateResultDTO.getCriteriaRatingId());
        evaluateResult.setTotalScores(evaluateResultDTO.getTotalScores());
        evaluateResult.setContent(evaluateResultDTO.getContent());
        evaluateResult.setSuggest(evaluateResultDTO.getSuggest());
        evaluateResult.setCreateDatetime(new Date());
        evaluateResult.setEvaluateStatus(Constants.EVALUATE_RESULT_STATUS.EVALUATED);

        List<EvaluateResultDetail> evaluateResultDetails = evaluateResult.getEvaluateResultDetails();

        if (evaluateResultDTO.getCriteriaDetailDTOS() != null && !evaluateResultDTO.getCriteriaDetailDTOS().isEmpty()) {
            List<EvaluateResultDetail> tmp = evaluateResultDTO.getCriteriaDetailDTOS().stream().map(criteriaDetailDTO -> {
                EvaluateResultDetail evaluateResultDetail = new EvaluateResultDetail();
                evaluateResultDetail.setEvaluateResult(evaluateResult);
                evaluateResultDetail.setCreateDatetime(new Date());
                evaluateResultDetail.setCriteriaDetailId(criteriaDetailDTO.getId());
                evaluateResultDetail.setNote(criteriaDetailDTO.getNote());
                return evaluateResultDetail;
            }).collect(Collectors.toList());
            evaluateResultDetails.clear();
            evaluateResultDetails.addAll(tmp);
            evaluateResult.setEvaluateResultDetails(evaluateResultDetails);
        }
        evaluateResultRepository.save(evaluateResult);
        //Cap nhat lai trang thai cuoc goi
        InOutBoundService inOutBoundService = inOutBoundFactory.getInOutBoundService(evaluateResult.getChannelType());
        inOutBoundService.updateAfterEvaluate(evaluateResult.getObjectId(), evaluateResult.getEvaluateAssignmentDetailId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluateResultDTO> getTicketForEvaluate(Long id) {
        try {
            Long userId = userService.getUserIdLogin();
            EvaluateResult evaluateResult = evaluateResultRepository.findByIdAndEvaluaterIdAndEvaluateStatusAndStatus(id, userId,
                Constants.EVALUATE_RESULT_STATUS.YET_EVALUATE, Constants.STATUS_ACTIVE).orElseThrow(()
                -> new BusinessException("101", Translator.toLocale("evaluate.result.evaluated")));
            List<EvaluateResultDetailDTO> evaluateResultDetailDTOS = evaluateResult.getEvaluateResultDetails().stream().map(evaluateResultDetailMapper::toDto).collect(Collectors.toList());
            EvaluateResultDTO evaluateResultDTO = evaluateResultMapper.toDto(evaluateResult);
            evaluateResultDTO.setEvaluaterName(SecurityUtils.getCurrentUserLogin().get());
            Optional<User> optionalUser = userRepository.findById(evaluateResult.getEvaluatedId());

            evaluateResultDTO.setEvaluatedName(optionalUser.get().getFirstName());
            if ("1".equals(evaluateResultDTO.getChannelType())) {
                Ticket ticket = ticketRepository.getOne(evaluateResultDTO.getObjectId());
                evaluateResultDTO.setDurations(ticket.getDuration() == null ? null : Double.parseDouble(ticket.getDuration()));
                evaluateResultDTO.setStartCall(ticket.getStartCall());
                evaluateResultDTO.setRecordLink(ticket.getCallFile());
            } else {
                CampaignResourceDetail campaignResourceDetail = campaignResourceDetailRepository.getOne(evaluateResultDTO.getObjectId());
                evaluateResultDTO.setDurations(campaignResourceDetail.getDuration() == null ? null : Double.parseDouble(campaignResourceDetail.getDuration()));
                evaluateResultDTO.setStartCall(campaignResourceDetail.getStartCall());
                evaluateResultDTO.setRecordLink(campaignResourceDetail.getCallFile());
            }
//            evaluateResultDTO.setRecordLink("/record/mp3/test");
            evaluateResultDTO.setEvaluateResultDetailDTOS(evaluateResultDetailDTOS);
            return Optional.of(evaluateResultDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;


    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateResultDTO> getEvaluateResults(EvaluateResultDTO evaluateResultDTO, Pageable pageable) throws ParseException {
        Page<Object[]> page = evaluateResultRepository.getEvaluateResult(evaluateResultDTO.getChannelType() == null ? "-1" : evaluateResultDTO.getChannelType(),
            evaluateResultDTO.getEvaluaterId() == null ? -1 : evaluateResultDTO.getEvaluaterId(),
            evaluateResultDTO.getEvaluatedId() == null ? -1 : evaluateResultDTO.getEvaluatedId(),
            evaluateResultDTO.getCriteriaRatingId() == null ? -1 : evaluateResultDTO.getCriteriaRatingId(),
            DataUtil.makeLikeQuery(evaluateResultDTO.getPhoneNumber()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getEndDate()),
            pageable);
        List<Object[]> objectLst = page.getContent();
        List<EvaluateResultDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateResultDTO evaluateResultDataDTO = new EvaluateResultDTO();
            int index = -1;
            evaluateResultDataDTO.setId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setChannelType(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setPhoneNumber(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setEvaluaterId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setEvaluaterName(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            evaluateResultDataDTO.setCreateDatetimeName(DateUtil.dateToStringDateVN(evaluateResultDataDTO.getCreateDatetime()));
            evaluateResultDataDTO.setEvaluatedId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setEvaluatedName(DataUtil.safeToString(object[++index]));

            evaluateResultDataDTO.setRecordDate(evaluateResultDataDTO.getCreateDatetime());
            evaluateResultDataDTO.setRecordDateName(DateUtil.dateToStringDateVN(evaluateResultDataDTO.getRecordDate()));

            evaluateResultDataDTO.setContent(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setSuggest(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setTotalScores(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setTotalScoresNew(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setCriteriaRatingId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setCriteriaRatingName(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError1(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError1Name(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError2(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError2Name(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setCriteriaRatingNewId(DataUtil.safeToLong(object[++index]));
            if (Constants.CHANNEL_TYPE.IN.equals(evaluateResultDataDTO.getChannelType())) {
                evaluateResultDataDTO.setChannelTypeName(Translator.toLocale("callin.label"));
            } else {
                evaluateResultDataDTO.setChannelTypeName(Translator.toLocale("callout.label"));
            }
            lstResults.add(evaluateResultDataDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluateResultDTO> getAllEvaluateResults(EvaluateResultDTO evaluateResultDTO) throws ParseException {
        List<Object[]> objectLst = evaluateResultRepository.getAllEvaluateResult(evaluateResultDTO.getChannelType() == null ? "-1" : evaluateResultDTO.getChannelType(),
            evaluateResultDTO.getEvaluaterId() == null ? -1 : evaluateResultDTO.getEvaluaterId(),
            evaluateResultDTO.getEvaluatedId() == null ? -1 : evaluateResultDTO.getEvaluatedId(),
            evaluateResultDTO.getCriteriaRatingId() == null ? -1 : evaluateResultDTO.getCriteriaRatingId(),
            DataUtil.makeLikeQuery(evaluateResultDTO.getPhoneNumber()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getEndDate()));
        List<EvaluateResultDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateResultDTO evaluateResultDataDTO = new EvaluateResultDTO();
            int index = -1;
            evaluateResultDataDTO.setId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setChannelType(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setPhoneNumber(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setEvaluaterId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setEvaluaterName(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setCreateDatetime(DataUtil.safeToDate(object[++index]));
            evaluateResultDataDTO.setCreateDatetimeName(DateUtil.dateToStringDateVN(evaluateResultDataDTO.getCreateDatetime()));
            evaluateResultDataDTO.setEvaluatedId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setRecordDate(evaluateResultDataDTO.getCreateDatetime());
            evaluateResultDataDTO.setRecordDateName(DateUtil.dateToStringDateVN(evaluateResultDataDTO.getRecordDate()));
            evaluateResultDataDTO.setEvaluatedName(DataUtil.safeToString(object[++index]));

            evaluateResultDataDTO.setContent(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setSuggest(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setTotalScores(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setTotalScoresNew(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setCriteriaRatingId(DataUtil.safeToLong(object[++index]));
            evaluateResultDataDTO.setCriteriaRatingName(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError1(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError1Name(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError2(DataUtil.safeToString(object[++index]));
            evaluateResultDataDTO.setError2Name(DataUtil.safeToString(object[++index]));
            if (Constants.CHANNEL_TYPE.IN.equals(evaluateResultDataDTO.getChannelType())) {
                evaluateResultDataDTO.setChannelTypeName(Translator.toLocale("callin.label"));
            } else {
                evaluateResultDataDTO.setChannelTypeName(Translator.toLocale("callout.label"));
            }
            lstResults.add(evaluateResultDataDTO);
        }
        return lstResults;
    }

    @Override
    public Boolean reEvaluateResult(EvaluateResultDTO evaluateResultDTO) throws Exception {
        log.debug("Request to save reEvaluateResult : {}", evaluateResultDTO);
        Long userId = userService.getUserIdLogin();
        EvaluateResult evaluateResult = evaluateResultRepository.findByIdAndEvaluaterIdAndEvaluateStatusInAndStatus(evaluateResultDTO.getId(), userId,
            Arrays.asList(Constants.EVALUATE_RESULT_STATUS.EVALUATED, Constants.EVALUATE_RESULT_STATUS.RE_EVALUATE),
            Constants.STATUS_ACTIVE).orElseThrow(() -> new BusinessException("101", Translator.toLocale("evaluate.result.yet-evaluated")));
        List<EvaluateResultDetail> evaluateResultDetails = evaluateResult.getEvaluateResultDetails();
        //Backup data
        evaluateResultHistoryService.createEvaluateResultHistoryByEvaluate(evaluateResult, evaluateResultDetails);
        //Update db
        evaluateResult.setEvaluaterId(userId);

        evaluateResult.setError1(evaluateResultDTO.getError1());
        evaluateResult.setError2(evaluateResultDTO.getError2());
        evaluateResult.setCriteriaRatingNewId(evaluateResultDTO.getCriteriaRatingId());
        evaluateResult.setTotalScoresNew(evaluateResultDTO.getTotalScores());
        evaluateResult.setTotalScoresNew(evaluateResultDTO.getTotalScoresNew());
        evaluateResult.setContent(evaluateResultDTO.getContent());
        evaluateResult.setSuggest(evaluateResultDTO.getSuggest());
        evaluateResult.setCreateDatetimeNew(new Date());
        evaluateResult.setEvaluateStatus(Constants.EVALUATE_RESULT_STATUS.RE_EVALUATE);
        if (evaluateResultDTO.getCriteriaDetailDTOS() != null && !evaluateResultDTO.getCriteriaDetailDTOS().isEmpty()) {
            List<EvaluateResultDetail> tmp = evaluateResultDTO.getCriteriaDetailDTOS().stream().map(criteriaDetailDTO -> {
                EvaluateResultDetail evaluateResultDetail = new EvaluateResultDetail();
                evaluateResultDetail.setEvaluateResult(evaluateResult);
                evaluateResultDetail.setCreateDatetime(new Date());
                evaluateResultDetail.setCriteriaDetailId(criteriaDetailDTO.getId());
                evaluateResultDetail.setNote(criteriaDetailDTO.getNote());
                return evaluateResultDetail;
            }).collect(Collectors.toList());
            evaluateResultDetails.clear();
            evaluateResultDetails.addAll(tmp);
            evaluateResult.setEvaluateResultDetails(evaluateResultDetails);
        }
        evaluateResultRepository.save(evaluateResult);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
            null, String.format("Đánh giá lại: Danh sách cuộc gọi được giám sát"),
            new Date(), Constants.MENU_ID.QA, Constants.MENU_ITEM_ID.evaluate_list, "CONFIG_MENU_ITEM"));
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluateResultRatingDTO> getRatingCall(EvaluateResultDTO evaluateResultDTO, Pageable pageable) {
        Page<Object[]> page = evaluateResultRepository.getRatingCall(
            evaluateResultDTO.getCriteriaRatingId() == null ? -1 : evaluateResultDTO.getCriteriaRatingId(),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getEndDate()),
            pageable);
        List<Object[]> objectLst = page.getContent();
        List<EvaluateResultRatingDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateResultRatingDTO evaluateResultRatingDTO = new EvaluateResultRatingDTO();
            int index = -1;
            evaluateResultRatingDTO.setCriteriaRatingId(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setCriteriaRatingName(DataUtil.safeToString(object[++index]));
            evaluateResultRatingDTO.setTimes(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setTotalCall(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setRate(DataUtil.safeToString(object[++index]));
            lstResults.add(evaluateResultRatingDTO);
        }
        return new PageImpl<>(lstResults, pageable, page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluateResultRatingDTO> getAllRatingCall(EvaluateResultDTO evaluateResultDTO) throws ParseException {
        List<Object[]> objectLst = evaluateResultRepository.getAllRatingCall(
            evaluateResultDTO.getCriteriaRatingId() == null ? -1 : evaluateResultDTO.getCriteriaRatingId(),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getStartDate()),
            DateUtil.dateToStringDateVN(evaluateResultDTO.getEndDate()));
        List<EvaluateResultRatingDTO> lstResults = new ArrayList<>();
        for (Object[] object : objectLst) {
            EvaluateResultRatingDTO evaluateResultRatingDTO = new EvaluateResultRatingDTO();
            int index = -1;
            evaluateResultRatingDTO.setCriteriaRatingId(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setCriteriaRatingName(DataUtil.safeToString(object[++index]));
            evaluateResultRatingDTO.setTimes(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setTotalCall(DataUtil.safeToLong(object[++index]));
            evaluateResultRatingDTO.setRate(DataUtil.safeToString(object[++index]));
            lstResults.add(evaluateResultRatingDTO);
        }
        return lstResults;
    }
}
