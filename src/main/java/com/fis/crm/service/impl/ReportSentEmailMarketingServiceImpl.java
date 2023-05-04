package com.fis.crm.service.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportSentEmailMarketingService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.service.dto.ReportSentEmailMarketingDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.google.common.collect.Lists;
import oracle.jdbc.OracleTypes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReportSentEmailMarketingServiceImpl implements ReportSentEmailMarketingService {

    @Autowired
    private  DataSource dataSource;

    @Autowired
    private ExportUtils exportUtils;

    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportSentEmailMarketingServiceImpl(UserService userService, ActionLogService actionLogService) {
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ReportSentEmailMarketingDTO> search(String id, String fromDate, String toDate, String email, String status, Pageable pageable) {
        List<ReportSentEmailMarketingDTO> lst = getLst(id, fromDate, toDate, email, status);
        List<List<ReportSentEmailMarketingDTO>> subSets = Lists.partition(lst, pageable.getPageSize());
        return new PageImpl<>(subSets.get(pageable.getPageNumber()), pageable, lst.size());
    }

    @Override
    public ByteArrayInputStream export(String id, String fromDate, String toDate, String email, String status) {
        List<ReportSentEmailMarketingDTO> lst = getLst(id, fromDate, toDate, email, status);

        if (lst.size() > 0){
            for (int i =0; i < lst.size(); i++){
                String html = lst.get(i).getContent();
                Document doc = Jsoup.parse(html);
                String body = doc.text();
                lst.get(i).setContent(body);
            }
        }
        List<ExcelColumn> lstColumn = buildColumnExport();
        String title = "Báo cáo chi tiết gửi Email marketing";
        ExcelTitle excelTitle = new ExcelTitle(title, fromDate, toDate);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = exportUtils.onExport(lstColumn, lst, 3, 0, excelTitle, true, "sheet1");
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, String.format("Xuất excel: Báo cáo chi tiết gửi email marketing"),
                new Date(), Constants.MENU_ID.EMAIL_MARKETING, Constants.MENU_ITEM_ID.report_sent_email_marketing, "CONFIG_MENU_ITEM"));
            return byteArrayInputStream;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
        }
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("campaignName", "Tên chiến dịch", ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("title", "Tiêu đề", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("email", "Email", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentUser", "Người gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("sentDate", "Ngày gửi", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("status", "Trạng thái", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("content", "Nội dung", ExcelColumn.ALIGN_MENT.CENTER));
        return lstColumn;
    }

    private List<ReportSentEmailMarketingDTO> getLst(String id, String fromDate, String toDate, String email, String status) {
        List<ReportSentEmailMarketingDTO> lst = new ArrayList<>();
        Connection connection = null;
            String sql = "{? = call PCK_REPORT.cc3_chiendich_email_detail(?, ?, ?,?,?) }";
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setLong(2, Long.parseLong(id));
            statement.setString(3, fromDate);
            statement.setString(4, toDate);
            statement.setString(5, email);
            statement.setString(6, status);
            statement.executeQuery();
            resultSet = (ResultSet) statement.getObject(1);
            while (resultSet.next()) {
                ReportSentEmailMarketingDTO dto = new ReportSentEmailMarketingDTO();
                dto.setCampaignName(resultSet.getString("campaign_name"));
                dto.setEmail(resultSet.getString("email"));
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
            DbUtils.close(statement);
            DbUtils.close(connection);
        }
        return lst;

    }



}
