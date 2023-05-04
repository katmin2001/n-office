package com.fis.crm.service;

import com.fis.crm.service.dto.CricleObj;
import com.fis.crm.service.dto.LineBarObj;
import com.fis.crm.service.dto.SummaryDTO;

import java.util.List;

public interface DashboardService {

    List<SummaryDTO> getSummary(String type, String date);

    List<LineBarObj> getSuVuHeThong(String date);

    List<LineBarObj> getReceivedAssignedDept(String date);

    List<CricleObj> getDeptPercent(String date);

    List<CricleObj> getBusinessTypePercent(String date);

    List<CricleObj> getCallOutGroup(String date);

    List<LineBarObj> callOutEvaluateSummary(String date);

    List<LineBarObj> callInEvaluateSummary(String date);

    List<LineBarObj> emailMarketingSummary(String date);

    List<LineBarObj> smsMarketingSummary(String date);

}
