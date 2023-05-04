package com.fis.crm.service.impl;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.Ticket;
import com.fis.crm.repository.DashboardRepository;
import com.fis.crm.service.DashboardService;
import com.fis.crm.service.dto.CricleObj;
import com.fis.crm.service.dto.LineBarObj;
import com.fis.crm.service.dto.SummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Ticket}.
 */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final DashboardRepository dashboardRepository;


    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public List<SummaryDTO> getSummary(String type, String date) {
        return dashboardRepository.getSummary(type, date)
            .stream()
            .map(SummaryDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<LineBarObj> getSuVuHeThong(String date) {
        return dashboardRepository.getSuVuHeThong(date)
            .stream()
            .map(LineBarObj::getSuVuHeThong)
            .collect(Collectors.toList());
    }

    @Override
    public List<LineBarObj> getReceivedAssignedDept(String date) {
        return dashboardRepository.getReceivedAssignedDept(date)
            .stream()
            .map(LineBarObj::getReceivedAssignedDept)
            .collect(Collectors.toList());
    }

    @Override
    public List<CricleObj> getDeptPercent(String date) {
        return dashboardRepository.getDeptPercent(date)
            .stream()
            .map(CricleObj::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<CricleObj> getBusinessTypePercent(String date) {
        return dashboardRepository.getBusinessTypePercent(date)
            .stream()
            .map(CricleObj::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<CricleObj> getCallOutGroup(String date) {
        return dashboardRepository.getCallOutGroup(date)
            .stream()
            .map(CricleObj::new)
            .collect(Collectors.toList());
    }


    @Override
    public List<LineBarObj> callOutEvaluateSummary(String date) {
        return dashboardRepository.callOutInEvaluateSummary(Constants.CALL.OUT, date)
            .stream()
            .map(LineBarObj::callOutInEvaluateSummary)
            .collect(Collectors.toList());
    }

    @Override
    public List<LineBarObj> callInEvaluateSummary(String date) {
        return dashboardRepository.callOutInEvaluateSummary(Constants.CALL.INT, date)
            .stream()
            .map(LineBarObj::callOutInEvaluateSummary)
            .collect(Collectors.toList());
    }

    @Override
    public List<LineBarObj> emailMarketingSummary(String date) {
        return dashboardRepository.emailSmsMarketingSummary(Constants.CHANNEL.EMAIL, date)
            .stream()
            .map(LineBarObj::emailSmsMarketingSummary)
            .collect(Collectors.toList());
    }

    @Override
    public List<LineBarObj> smsMarketingSummary(String date) {
        return dashboardRepository.emailSmsMarketingSummary(Constants.CHANNEL.SMS, date)
            .stream()
            .map(LineBarObj::emailSmsMarketingSummary)
            .collect(Collectors.toList());
    }
}
