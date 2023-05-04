package com.fis.crm.service.impl;

import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ReportRenderStatisticQuestionRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportRenderStatisticQuestionService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;
import com.fis.crm.service.dto.ReportDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportRenderStatisticQuestionServiceImpl implements ReportRenderStatisticQuestionService {

    private final ReportRenderStatisticQuestionRepository reportRenderStatisticQuestionRepository;
    private final ExcelUtils excelUtils;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportRenderStatisticQuestionServiceImpl(ReportRenderStatisticQuestionRepository reportRenderStatisticQuestionRepository, ExcelUtils excelUtils, UserService userService, ActionLogService actionLogService) {
        this.reportRenderStatisticQuestionRepository = reportRenderStatisticQuestionRepository;
        this.excelUtils = excelUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<ReportDTO> getData(String callTimeFrom, String callTimeTo, Long campaignId, Long questionId) {
        return reportRenderStatisticQuestionRepository.getData(callTimeFrom, callTimeTo, campaignId, questionId);
    }

    @Override
    public ByteArrayInputStream exportExcel(ReportDTO dto) {
        try {
            ListExcelDynamicGroupDTO listExcelDynamicGroupDTO = reportRenderStatisticQuestionRepository.exportExcel(
                dto.getCallTimeFrom(),
                dto.getCallTimeTo(),
                dto.getCampaignId(),
                dto.getQuestionId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Kết xuất báo cáo thống kê câu hỏi",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.report_render_statistics_question, "CONFIG_MENU_ITEM"));
            return excelUtils.buildExcelDynamic(listExcelDynamicGroupDTO, "hihi");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
