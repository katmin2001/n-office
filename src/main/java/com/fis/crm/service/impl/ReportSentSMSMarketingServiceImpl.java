package com.fis.crm.service.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportSentSMSMarketingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.service.dto.ReportSentSMSMarketingDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.google.common.collect.Lists;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
public class ReportSentSMSMarketingServiceImpl implements ReportSentSMSMarketingService {

    private final EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private final ExportUtils exportUtils;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportSentSMSMarketingServiceImpl(EntityManager entityManager, ExportUtils exportUtils, UserService userService, ActionLogService actionLogService) {
        this.entityManager = entityManager;
        this.exportUtils = exportUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ReportSentSMSMarketingDTO> search(String id, String fromDate, String toDate, String phoneNumber, String status, Pageable pageable) {
        List<ReportSentSMSMarketingDTO> lst = getLst(id, fromDate, toDate, phoneNumber, status);
        List<List<ReportSentSMSMarketingDTO>> subSets = Lists.partition(lst, pageable.getPageSize());
        if (lst.size() != 0){
            return new PageImpl<>(subSets.get(pageable.getPageNumber()), pageable, lst.size());
        }
        List<ReportSentSMSMarketingDTO> strings = new ArrayList<>();
        return new PageImpl<>(strings, pageable, lst.size());

    }

    @Override
    public ByteArrayInputStream export(String id, String fromDate, String toDate, String phoneNumber, String status) {
        List<ReportSentSMSMarketingDTO> lst = getLst(id, fromDate, toDate, phoneNumber, status);
        for (ReportSentSMSMarketingDTO dto : lst) {
            if (dto.getStatus().equals("2")) {
                dto.setStatusView(Translator.toLocale("report-productivity-callout-campaign.success"));
            } else {
                dto.setStatusView(Translator.toLocale("report-productivity-callout-campaign.error"));
            }
        }
        List<ExcelColumn> lstColumn = buildColumnExport();
        String title = "Báo cáo chi tiết gửi SMS marketing";
        ExcelTitle excelTitle = new ExcelTitle(title, fromDate, toDate);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = exportUtils.onExport(lstColumn, lst, 3, 0, excelTitle, true, "sheet1");
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Báo cáo chi tiết gửi SMS marketing",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.report_sent_sms_marketing, "CONFIG_MENU_ITEM"));
            return byteArrayInputStream;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
        }
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("campaignName", "Tên chiến dịch", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("title", "Tiêu đề", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", "Số điện thoại", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentUser", "Người gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentDate", "Ngày gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("statusView", "Trạng thái", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("content", "Nội dung", ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    private List<ReportSentSMSMarketingDTO> getLst(String id, String fromDate, String toDate, String phoneNumber, String status) {
        List<ReportSentSMSMarketingDTO> lst = new ArrayList<>();
        Connection connection = null;
        String sql = "{? = call PCK_REPORT.cc3_chiendich_sms_detail(?, ?, ?,?,?) }";
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setLong(2, Long.parseLong(id));
            statement.setString(3, fromDate);
            statement.setString(4, toDate);
            statement.setString(5, phoneNumber);
            statement.setString(6, status);
            statement.executeQuery();
            resultSet = (ResultSet) statement.getObject(1);
            while (resultSet.next()) {
                ReportSentSMSMarketingDTO dto = new ReportSentSMSMarketingDTO();
                dto.setCampaignName(resultSet.getString("campaign_name"));
                dto.setPhoneNumber(resultSet.getString("phoneNumber"));
                dto.setContent(resultSet.getString("content"));
                dto.setSentDate(resultSet.getString("send_date"));
                dto.setSentUser(resultSet.getString("user_sent"));
                dto.setStatus(resultSet.getString("status"));
                dto.setTitle(resultSet.getString("title"));
                lst.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.close(resultSet);
            DbUtils.close(statement);
            DbUtils.close(connection);
        }
        return lst;

    }
}
