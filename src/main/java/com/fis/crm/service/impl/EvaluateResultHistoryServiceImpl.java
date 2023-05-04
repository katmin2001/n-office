package com.fis.crm.service.impl;

import com.fis.crm.domain.*;
import com.fis.crm.repository.EvaluateResultHistoryRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.EvaluateResultHistoryService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.mapper.EvaluateResultDetailHistoryMapper;
import com.fis.crm.service.mapper.EvaluateResultHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EvaluateResultHistoryServiceImpl implements EvaluateResultHistoryService {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultHistoryServiceImpl.class);

    private final EvaluateResultHistoryRepository evaluateResultHistoryRepository;

    private final EvaluateResultHistoryMapper evaluateResultHistoryMapper;
    private final EvaluateResultDetailHistoryMapper evaluateResultDetailHistoryMapper;

    private final UserService userService;

    public EvaluateResultHistoryServiceImpl(EvaluateResultHistoryRepository evaluateResultHistoryRepository, EvaluateResultHistoryMapper evaluateResultHistoryMapper, EvaluateResultDetailHistoryMapper evaluateResultDetailHistoryMapper, UserService userService) {
        this.evaluateResultHistoryRepository = evaluateResultHistoryRepository;
        this.evaluateResultHistoryMapper = evaluateResultHistoryMapper;
        this.evaluateResultDetailHistoryMapper = evaluateResultDetailHistoryMapper;
        this.userService = userService;
    }

    @Override
    public boolean createEvaluateResultHistoryByEvaluate(EvaluateResult evaluateResult, List<EvaluateResultDetail> evaluateResultDetails) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hh:mm");
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        EvaluateResultHistory evaluateResultHistory = evaluateResultHistoryMapper.toEntity(evaluateResult);
        evaluateResultHistory.setId(null);
        evaluateResultHistory.setEvaluateResultId(evaluateResult.getId());
        List<EvaluateResultDetailHistory> evaluateResultDetailHistories = new ArrayList<>();
        if(evaluateResultDetails!=null&&!evaluateResultDetails.isEmpty()) {
            evaluateResultDetailHistories = evaluateResultDetails.stream().map(evaluateResultDetail -> {
                EvaluateResultDetailHistory evaluateResultDetailHistory = evaluateResultDetailHistoryMapper.toEntity(evaluateResultDetail);
                evaluateResultDetailHistory.setId(null);
                evaluateResultDetailHistory.setEvaluateResultDetailId(evaluateResultDetail.getId());
                evaluateResultDetailHistory.setEvaluateResultHistory(evaluateResultHistory);
                return evaluateResultDetailHistory;
            }).collect(Collectors.toList());
        }
        evaluateResultHistory.setEvaluateResultDetailHistories(evaluateResultDetailHistories);
        evaluateResultHistory.setContent(user.getLogin() + " - " + sdf.format(new Date()) + " - " + evaluateResultHistory.getContent());
        evaluateResultHistory.setSuggest(user.getLogin() + " - " + sdf.format(new Date()) + " - " + evaluateResultHistory.getSuggest());
        evaluateResultHistoryRepository.save(evaluateResultHistory);
        return true;
    }

    @Override
    public Map<String, List<String>> getEvaluateResultHistoryByEvaluate(Long evaluateId) {
        List<EvaluateResultHistory> evaluateResultHistoryList = evaluateResultHistoryRepository.findByEvaluateResultId(evaluateId);
        List<String> listContent = new ArrayList<>();
        List<String> listSuggest = new ArrayList<>();
        for (EvaluateResultHistory evaluateResultHistory : evaluateResultHistoryList) {
            listContent.add(evaluateResultHistory.getContent());
            listSuggest.add(evaluateResultHistory.getSuggest());
        }

        Map<String, List<String>> map = new HashMap<>();

        map.put("content", listContent);
        map.put("suggest", listSuggest);

        return map;
    }
}
