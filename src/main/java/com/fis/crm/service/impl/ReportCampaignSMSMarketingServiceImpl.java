package com.fis.crm.service.impl;

import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportCampaignSMSMarketingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.service.dto.ReportCampaignSMSMarketingDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.google.common.collect.Lists;
import oracle.jdbc.OracleTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportCampaignSMSMarketingServiceImpl implements ReportCampaignSMSMarketingService {

    private final ExportUtils exportUtils;
    private final DataSource dataSource;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportCampaignSMSMarketingServiceImpl(ExportUtils exportUtils, DataSource dataSource, UserService userService, ActionLogService actionLogService) {
        this.exportUtils = exportUtils;
        this.dataSource = dataSource;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ReportCampaignSMSMarketingDTO> search(String id, String fromDate, String toDate, Pageable pageable) {
        List<ReportCampaignSMSMarketingDTO> lst = getLst(id, fromDate, toDate);
        List<List<ReportCampaignSMSMarketingDTO>> subSets = Lists.partition(lst, pageable.getPageSize());
        return new PageImpl<>(subSets.get(pageable.getPageNumber()), pageable, lst.size());
    }

    @Override
    public ByteArrayInputStream export(String id, String fromDate, String toDate) {
        List<ReportCampaignSMSMarketingDTO> lst = getLst(id, fromDate, toDate);
        List<ExcelColumn> lstColumn = buildColumnExport();
        String title = "Báo cáo theo chiến dịch SMS marketing";
        ExcelTitle excelTitle = new ExcelTitle(title, fromDate, toDate);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = exportUtils.onExport(lstColumn, lst, 3, 0, excelTitle, true, "sheet1");
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Báo cáo theo chiến dịch SMS marketing",
                new Date(), Constants.MENU_ID.SMS_MARKETING, Constants.MENU_ITEM_ID.report_campaign_sms_marketing, "CONFIG_MENU_ITEM"));
            return byteArrayInputStream;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
        }
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("campaignName", "Tên chiến dịch", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("totalSms", "Tổng số SMS", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sent", "Đã gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("unsent", "Chưa gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentSuccess", "Gửi thành công", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentError", "Gửi lỗi", ExcelColumn.ALIGN_MENT.CENTER));
        return lstColumn;
    }

    private List<ReportCampaignSMSMarketingDTO> getLst(String id, String fromDate, String toDate) {
        List<ReportCampaignSMSMarketingDTO> lst = new ArrayList<>();
        Connection connection = null;
        String sql = "{? = call PCK_REPORT.cc3_chiendich_sms_marketing(?, ?, ?) }";
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setLong(2, Long.parseLong(id));
            statement.setString(3, fromDate);
            statement.setString(4, toDate);
            statement.executeQuery();
            resultSet = (ResultSet) statement.getObject(1);
            while (resultSet.next()) {
                ReportCampaignSMSMarketingDTO dto = new ReportCampaignSMSMarketingDTO();
                dto.setCampaignName(resultSet.getString("campaign_name"));
                dto.setTotalSms(resultSet.getString("total_sms"));
                dto.setSent(resultSet.getString("sent"));
                dto.setUnsent(resultSet.getString("unsent"));
                dto.setSentSuccess(resultSet.getString("sent_success"));
                dto.setSentError(resultSet.getString("sent_error"));
                lst.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
}
