package com.fis.crm.service;

import com.fis.crm.domain.EvaluateResult;
import com.fis.crm.domain.EvaluateResultDetail;

import java.util.List;
import java.util.Map;

public interface EvaluateResultHistoryService {

    boolean createEvaluateResultHistoryByEvaluate(EvaluateResult evaluateResult, List<EvaluateResultDetail> evaluateResultDetails);

    Map<String, List<String>> getEvaluateResultHistoryByEvaluate(Long evaluateId);
}
