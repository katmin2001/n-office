package com.fis.crm.service.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ReportProductivityCalloutCampaignService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.service.dto.ReportProductivityCalloutCampaignDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.google.common.collect.Lists;
import oracle.jdbc.OracleTypes;
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
public class ReportProductivityCalloutCampaignServiceImpl implements ReportProductivityCalloutCampaignService {

    private final ExportUtils exportUtils;

    private final EntityManager entityManager;

    private final DataSource dataSource;
    private final UserService userService;
    private final ActionLogService actionLogService;

    public ReportProductivityCalloutCampaignServiceImpl(ExportUtils exportUtils, EntityManager entityManager, DataSource dataSource, UserService userService, ActionLogService actionLogService) {
        this.exportUtils = exportUtils;
        this.entityManager = entityManager;
        this.dataSource = dataSource;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    @Override
    public Page<ReportProductivityCalloutCampaignDTO> search(String id, String operator, String fromDate, String toDate, Pageable pageable) {
        List<ReportProductivityCalloutCampaignDTO> lst = getLst(id, operator, fromDate, toDate);
        if (!lst.isEmpty()) {
            List<List<ReportProductivityCalloutCampaignDTO>> subSets = Lists.partition(lst, pageable.getPageSize());
            return new PageImpl<>(subSets.get(pageable.getPageNumber()), pageable, lst.size());
        } else {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }

    @Override
    public ByteArrayInputStream export(String id, String operator, String fromDate, String toDate) {
        List<ReportProductivityCalloutCampaignDTO> lst = getLst(id, operator, fromDate, toDate);
        List<ExcelColumn> lstColumn = buildColumnExport();
        String title = Translator.toLocale("report-productivity-callout-campaign.title-excel");
        ExcelTitle excelTitle = new ExcelTitle(title, fromDate, toDate);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = exportUtils.onExportReportProductivityCalloutCampaign(lstColumn, lst, 3, 0, excelTitle, true, "sheet1");
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
                null, "Xuất excel: Báo cáo năng xuất chiến dịch gọi ra",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.report_productivity_callout_campaign, "CONFIG_MENU_ITEM"));
            return byteArrayInputStream;
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.export.error"), "", "");
        }
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("operator", Translator.toLocale("report-productivity-callout-campaign.operator"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("totalCall", Translator.toLocale("report-productivity-callout-campaign.total-call"), ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("ketNoiThanhCong", "Kết nối thành công", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("voidEmail", "Voice email", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("khKhongNhacMay", "Không nhấc máy", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("saiSo", "Sai số", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("khongCoTinHieu", "Không có tín hiệu", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("mayBan", "Máy bận", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("khongLLDuoc", "Không liên lạc được", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("henGoiLai", "Hẹn gọi lại", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("ksThanhCong", "Khảo sát thành công", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("khThieuTT", "Khách hàng thiếu tương tác", ExcelColumn.ALIGN_MENT.CENTER));
        lstColumn.add(new ExcelColumn("khac", "Khác", ExcelColumn.ALIGN_MENT.CENTER));

        return lstColumn;
    }

    private List<ReportProductivityCalloutCampaignDTO> getLst(String id, String operator, String fromDate, String toDate) {
        List<ReportProductivityCalloutCampaignDTO> lst = new ArrayList<>();
        Connection connection = null;
        String sql = "{? = call PCK_REPORT.CC3_NANGXUAT_CHIENDICH_GOIRA(?, ?, ?, ?) }";
        CallableStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setLong(2, Long.parseLong(id));
            statement.setString(3, operator);
            statement.setString(4, fromDate);
            statement.setString(5, toDate);
            statement.executeQuery();
            resultSet = (ResultSet) statement.getObject(1);
            while (resultSet.next()) {
                ReportProductivityCalloutCampaignDTO dto = new ReportProductivityCalloutCampaignDTO();
                dto.setOperator(resultSet.getString("tdv"));
                dto.setTotalCall(resultSet.getString("total"));
                dto.setKetNoiThanhCong(resultSet.getString("ketnoi_thanhcong"));
                dto.setVoidEmail(resultSet.getString("void_email"));
                dto.setKhKhongNhacMay(resultSet.getString("kh_khong_nhacmay"));
                dto.setSaiSo(resultSet.getString("sai_so"));
                dto.setKhongCoTinHieu(resultSet.getString("khong_co_tinhieu"));
                dto.setMayBan(resultSet.getString("may_ban"));
                dto.setKhongLLDuoc(resultSet.getString("khong_ll_duoc"));
                dto.setHenGoiLai(resultSet.getString("hen_goi_lai"));
                dto.setKsThanhCong(resultSet.getString("ks_thanhcong"));
                dto.setKhThieuTT(resultSet.getString("kh_thieu_tt"));
                dto.setKhac(resultSet.getString("khac"));
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
