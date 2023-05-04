package com.fis.crm.service.impl;

import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ReportRenderDetailOutputCallRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportRenderDetailOutputCallService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
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
public class ReportRenderDetailOutputCallServiceImpl implements ReportRenderDetailOutputCallService {

    private final ReportRenderDetailOutputCallRepository reportRenderDetailOutputCallRepository;
    private final ExcelUtils excelUtils;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportRenderDetailOutputCallServiceImpl(ReportRenderDetailOutputCallRepository reportRenderDetailOutputCallRepository,
                                                   ExcelUtils excelUtils, UserService userService, ActionLogService actionLogService) {
        this.reportRenderDetailOutputCallRepository = reportRenderDetailOutputCallRepository;
        this.excelUtils = excelUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom, String callTimeTo, Long campaignId, String statusCall, String assignUserId) {
        return reportRenderDetailOutputCallRepository.getCallData(callTimeFrom, callTimeTo, campaignId, statusCall, assignUserId);
    }

    @Override
    public ByteArrayInputStream exportExcel(ReportDTO dto) {
        try {
            ListExcelDynamicGroupDTO listExcelDynamicGroupDTO = reportRenderDetailOutputCallRepository.exportExcel(
                dto.getCallTimeFrom(),
                dto.getCallTimeTo(),
                dto.getCampaignId(),
                dto.getStatusCall(),
                dto.getAssignUserId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Kết xuất báo cáo chi tiết kết quả gọi ra",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.report_render_detail_output_call, "CONFIG_MENU_ITEM"));
            return excelUtils.buildExcelDynamic(listExcelDynamicGroupDTO, "hihi");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
