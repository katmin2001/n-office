package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.DbUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.repository.ReportBaseRepository;
import com.fis.crm.service.dto.*;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReportBaseRepositoryImpl implements ReportBaseRepository {
    private final Logger log = LoggerFactory.getLogger(ReportBaseRepositoryImpl.class);
    private static final String query_detail_productivity = " select t.ticket_code,\n" +
        "       tr.ticket_request_code,\n" +
        "       (select name from ap_domain ap where ap.type = 'CHANEL_TYPE' and t.CHANEL_TYPE = ap.code)       kenh_tiep_nhan,\n" +
        "       (select name from ap_domain ap where ap.type = 'TICKET_REQUEST_STATUS' and ap.code = tr.status) trang_thai_yc,\n" +
        "       to_char(t.create_datetime, 'dd/mm/yyyy')                                                                      ngay_tiep_nhan\n" +
        "from ticket t,\n" +
        "     ticket_request tr,\n" +
        "     JHI_USER u\n" +
        "where t.ticket_id = tr.ticket_id\n" +
        "  and u.id=tr.create_user\n" +
        "  and lower (t.create_user) = :staffCode\n" +
        "  and t.STATUS = :status\n" +
        "  and tr.request_type = nvl(:requestType,tr.request_type)\n" +
        "  and tr.bussiness_type = nvl(:busType,tr.bussiness_type)\n" +
        "  and (:fromDate is null or t.create_datetime>=to_date(:fromDate, 'dd/MM/yyyy'))\n" +
        "  and (:toDate is null or t.create_datetime<to_date(:toDate, 'dd/MM/yyyy')+1 )\n" +
        "  and t.chanel_type=nvl(:channelType,t.chanel_type)\n" +
        "  and upper(u.first_name) like '%'||upper(nvl(:receiverUser,u.first_name))||'%' order by t.ticket_code";

    private static final String query_detail_productivity_count = " select count(1)  " +
        "from ticket t,\n" +
        "     ticket_request tr,\n" +
        "     JHI_USER u\n" +
        "where t.ticket_id = tr.ticket_id\n" +
        "  and u.id=tr.create_user\n" +
        "  and lower (t.create_user) = :staffCode\n" +
        "  and t.STATUS = :status\n" +
        "  and tr.request_type = nvl(:requestType,tr.request_type)\n" +
        "  and tr.bussiness_type = nvl(:busType,tr.bussiness_type)\n" +
        "  and (:fromDate is null or t.create_datetime>=to_date(:fromDate, 'dd/MM/yyyy'))\n" +
        "  and (:toDate is null or t.create_datetime<to_date(:toDate, 'dd/MM/yyyy')+1)\n" +
        "  and t.chanel_type=nvl(:channelType,t.chanel_type)\n" +
        "  and upper(u.first_name) like '%'||upper(nvl(:receiverUser,u.first_name))||'%' order by t.ticket_code";

    @Autowired
    DataSource dataSource;
    private final EntityManager entityManager;

    public ReportBaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public WSResReportComplaintDTO getReportComplaintDetail(RequestReportTicketDTO requestReportTicketDTO) {
        String vstrSQL = "{?=call pck_report.cc1_mh09(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
        CallableStatement callableStatement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        List<ReportComplaintDetailDTO> results = new ArrayList<>();
        WSResReportComplaintDTO wsResReportComplaintDTO = new WSResReportComplaintDTO();
        try {
            conn = dataSource.getConnection();
            callableStatement = conn.prepareCall(vstrSQL);
            setParramQueryReport(requestReportTicketDTO, callableStatement);
            callableStatement.executeQuery();
            resultSet = (ResultSet) callableStatement.getObject(1);
            Long totalRow = DataUtil.safeToLong(callableStatement.getObject(14));
            while (resultSet.next()) {
                ReportComplaintDetailDTO reportComplaintDetailDTO = new ReportComplaintDetailDTO(resultSet);
                results.add(reportComplaintDetailDTO);
            }
            wsResReportComplaintDTO.setResults(results);
            wsResReportComplaintDTO.setTotalRow(totalRow);
            return wsResReportComplaintDTO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            DbUtils.closeAll(conn, resultSet, callableStatement);
        }
        return null;

    }

    private void setParramQueryReport(RequestReportTicketDTO requestReportTicketDTO, CallableStatement callableStatement) throws SQLException {
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.setString(2, DataUtil.isNullOrEmpty(requestReportTicketDTO.getChannelType()) ? "" : requestReportTicketDTO.getChannelType());
        callableStatement.setString(3, DataUtil.isNullOrEmpty(requestReportTicketDTO.getPriority()) ? "" : requestReportTicketDTO.getPriority());
        callableStatement.setString(4, DataUtil.isNullOrEmpty(requestReportTicketDTO.getTicketStatus()) ? "" : requestReportTicketDTO.getTicketStatus());
        callableStatement.setString(5, DataUtil.isNullOrEmpty(requestReportTicketDTO.getRequestStatus()) ? "" : requestReportTicketDTO.getRequestStatus());
        callableStatement.setString(6, DataUtil.isNullOrEmpty(requestReportTicketDTO.getDepartmentId()) ? "" : requestReportTicketDTO.getDepartmentId().toString());
        callableStatement.setString(7, DataUtil.isNullOrEmpty(requestReportTicketDTO.getRequestType()) ? null : requestReportTicketDTO.getRequestType());
        callableStatement.setString(8, DataUtil.isNullOrEmpty(requestReportTicketDTO.getBussinessType()) ? null : requestReportTicketDTO.getBussinessType());
        callableStatement.setString(9, DataUtil.isNullOrEmpty(requestReportTicketDTO.getFromDate()) ? null : DateUtil.dateToString(requestReportTicketDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setString(10, DataUtil.isNullOrEmpty(requestReportTicketDTO.getToDate()) ? null : DateUtil.dateToString(requestReportTicketDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setLong(11, requestReportTicketDTO.getOffSet() != null ? requestReportTicketDTO.getOffSet() : 0);
        callableStatement.setInt(12, requestReportTicketDTO.getPageSize());
        callableStatement.setString(13, requestReportTicketDTO.getQueryType());
        callableStatement.registerOutParameter(14, Types.DECIMAL);
        callableStatement.registerOutParameter(15, Types.VARCHAR);
    }

    @Override
    public WSResReportProcessTimeDTO getReportProcessTime(RequestReportTimeProcessDTO requestReportTimeProcessDTO) {
        String vstrSQL = "{? = call pck_report.cc1_mh10(?,?,?,?,?,?,?,?,?,?,?,?,?) }";
        CallableStatement callableStatement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        List<ReportProcessTimeDTO> results = new ArrayList<>();
        WSResReportProcessTimeDTO wsResReportProcessTimeDTO = new WSResReportProcessTimeDTO();
        try {
            conn = dataSource.getConnection();
            callableStatement = conn.prepareCall(vstrSQL);
            setParramQueryReport(requestReportTimeProcessDTO, callableStatement);
            callableStatement.executeQuery();
            resultSet = (ResultSet) callableStatement.getObject(1);
            Long totalRow = DataUtil.safeToLong(callableStatement.getObject(13));
            while (resultSet.next()) {
                ReportProcessTimeDTO reportProcessTimeDTO = new ReportProcessTimeDTO(resultSet);
                results.add(reportProcessTimeDTO);
            }
            wsResReportProcessTimeDTO.setResults(results);
            wsResReportProcessTimeDTO.setTotalRow(totalRow);
            return wsResReportProcessTimeDTO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            DbUtils.closeAll(conn, resultSet, callableStatement);
        }
        return null;

    }

    private void setParramQueryReport(RequestReportTimeProcessDTO reportTimeProcessDTO, CallableStatement callableStatement) throws SQLException {
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.setString(2, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getIdCode()) ? "" : reportTimeProcessDTO.getIdCode());
        callableStatement.setString(3, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getProcessDeadline()) ? "" : reportTimeProcessDTO.getProcessDeadline());
        callableStatement.setString(4, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getConfirmDeadline()) ? "" : reportTimeProcessDTO.getConfirmDeadline());
        callableStatement.setString(5, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getPhoneNumber()) ? "" : reportTimeProcessDTO.getPhoneNumber());
        callableStatement.setString(6, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getTicketStatus()) ? "" : reportTimeProcessDTO.getTicketStatus());
        callableStatement.setString(7, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getRequestStatus()) ? "" : reportTimeProcessDTO.getRequestStatus());
        callableStatement.setString(8, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getFromDate()) ? "" : DateUtil.dateToString(reportTimeProcessDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setString(9, DataUtil.isNullOrEmpty(reportTimeProcessDTO.getToDate()) ? "" : DataUtil.dateToString(reportTimeProcessDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setLong(10, reportTimeProcessDTO.getOffSet() != null ? reportTimeProcessDTO.getOffSet() : 0);
        callableStatement.setInt(11, reportTimeProcessDTO.getPageSize());
        callableStatement.setString(12, reportTimeProcessDTO.getQueryType());
        callableStatement.registerOutParameter(13, Types.DECIMAL);
        callableStatement.registerOutParameter(14, Types.VARCHAR);
    }


    @Override
    public WSReportComplaintContactCenterDTO getReportComplaintContactCenter(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO) {
        String vstrSQL = "{? = call pck_report.cc1_mh12(?,?,?,?,?,?,?,?,?) }";
        CallableStatement callableStatement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        List<ReportComplaintContactCenterDTO> results = new ArrayList<>();
        WSReportComplaintContactCenterDTO wsReportComplaintContactCenterDTO = new WSReportComplaintContactCenterDTO();
        try {
            conn = dataSource.getConnection();
            callableStatement = conn.prepareCall(vstrSQL);
            setParramQueryReport(requestReportComplaintContactCenterDTO, callableStatement);
            callableStatement.executeQuery();
            resultSet = (ResultSet) callableStatement.getObject(1);
            Long totalRow = DataUtil.safeToLong(callableStatement.getObject(9));
            while (resultSet.next()) {
                ReportComplaintContactCenterDTO reportComplaintContactCenterDTO = new ReportComplaintContactCenterDTO(resultSet);
                results.add(reportComplaintContactCenterDTO);
            }
            wsReportComplaintContactCenterDTO.setResults(results);
            wsReportComplaintContactCenterDTO.setTotalRow(totalRow);
            return wsReportComplaintContactCenterDTO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            DbUtils.closeAll(conn, resultSet, callableStatement);
        }
        return null;

    }

    private void setParramQueryReport(RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO, CallableStatement callableStatement) throws SQLException {
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.setString(2, DataUtil.isNullOrEmpty(requestReportComplaintContactCenterDTO.getChannelType()) ? "" : requestReportComplaintContactCenterDTO.getChannelType());
        callableStatement.setString(3, DataUtil.isNullOrEmpty(requestReportComplaintContactCenterDTO.getReceiveUser()) ? "" : requestReportComplaintContactCenterDTO.getReceiveUser());
        callableStatement.setString(4, DataUtil.isNullOrEmpty(requestReportComplaintContactCenterDTO.getFromDate()) ? "" : DateUtil.dateToString(requestReportComplaintContactCenterDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setString(5, DataUtil.isNullOrEmpty(requestReportComplaintContactCenterDTO.getToDate()) ? "" : DateUtil.dateToString(requestReportComplaintContactCenterDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setLong(6, requestReportComplaintContactCenterDTO.getOffSet() != null ? requestReportComplaintContactCenterDTO.getOffSet() : 0);
        callableStatement.setInt(7, requestReportComplaintContactCenterDTO.getPageSize());
        callableStatement.setString(8, requestReportComplaintContactCenterDTO.getQueryType());
        callableStatement.registerOutParameter(9, Types.DECIMAL);
        callableStatement.registerOutParameter(10, Types.VARCHAR);
    }

    public WSResProductivityDTO getProductivity(RequestProductivityDTO requestProductivityDTO) {
        String vstrSQL = "{? = call pck_report.cc1_mh11_total(?,?,?,?,?,?,?,?,?,?,?) }";
        CallableStatement callableStatement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        List<ProductivityReportDTO> results = new ArrayList<>();
        WSResProductivityDTO wsResProductivityDTO = new WSResProductivityDTO();
        try {
            conn = dataSource.getConnection();
            callableStatement = conn.prepareCall(vstrSQL);
            setParramQueryReport(requestProductivityDTO, callableStatement);
            callableStatement.executeQuery();
            resultSet = (ResultSet) callableStatement.getObject(1);
            Long totalRow = DataUtil.safeToLong(callableStatement.getObject(11));
            while (resultSet.next()) {
                ProductivityReportDTO productivityReportDTO = new ProductivityReportDTO(resultSet);
                results.add(productivityReportDTO);
            }
            wsResProductivityDTO.setResults(results);
            wsResProductivityDTO.setTotalRow(totalRow);
            return wsResProductivityDTO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            DbUtils.closeAll(conn, resultSet, callableStatement);
        }
        return null;
    }

    private void setParramQueryReport(RequestProductivityDTO requestProductivityDTO, CallableStatement callableStatement) throws SQLException {
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.setString(2, DataUtil.isNullOrEmpty(requestProductivityDTO.getChannelType()) ? "" : requestProductivityDTO.getChannelType());
        callableStatement.setString(3, DataUtil.isNullOrEmpty(requestProductivityDTO.getReceiveUser()) ? "" : requestProductivityDTO.getReceiveUser());
        callableStatement.setString(4, DataUtil.isNullOrEmpty(requestProductivityDTO.getRequestType()) ? "" : requestProductivityDTO.getRequestType());
        callableStatement.setString(5, DataUtil.isNullOrEmpty(requestProductivityDTO.getBussinessType()) ? "" : requestProductivityDTO.getBussinessType());
        callableStatement.setString(6, DataUtil.isNullOrEmpty(requestProductivityDTO.getFromDate()) ? "" : DateUtil.dateToString(requestProductivityDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setString(7, DataUtil.isNullOrEmpty(requestProductivityDTO.getToDate()) ? "" : DateUtil.dateToString(requestProductivityDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        callableStatement.setLong(8, requestProductivityDTO.getOffSet() != null ? requestProductivityDTO.getOffSet() : 0);
        callableStatement.setInt(9, requestProductivityDTO.getPageSize());
        callableStatement.setString(10, requestProductivityDTO.getQueryType());
        callableStatement.registerOutParameter(11, Types.DECIMAL);
        callableStatement.registerOutParameter(12, Types.VARCHAR);
    }

    public List<ProductivityReportDetailDTO> getLstProductivityDetail(RequestProductivityDTO requestProductivityDTO) {
        Query query = entityManager.createNativeQuery(query_detail_productivity);
        String fromDate = null;
        String toDate = null;
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getFromDate())) {
            fromDate = DateUtil.dateToString(requestProductivityDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY);
        }
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getToDate())) {
            toDate = DateUtil.dateToString(requestProductivityDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY);
        }
        if (DataUtil.isNullOrEmpty(requestProductivityDTO.getReceiveUser())) {
            requestProductivityDTO.setReceiveUser("");
        }
        String chanelType = "";
        if (requestProductivityDTO.getChannelType() != null && !requestProductivityDTO.getChannelType().equals("null")) {
            chanelType = requestProductivityDTO.getChannelType();
        }
        String requestType = "";
        if (requestProductivityDTO.getRequestType() != null && !requestProductivityDTO.getRequestType().equals("null")) {
            requestType = requestProductivityDTO.getRequestType();
        }
        String busType = "";
        if (requestProductivityDTO.getBussinessType() != null && !requestProductivityDTO.getBussinessType().equals("null")) {
            busType = requestProductivityDTO.getBussinessType();
        }
        query.setParameter("staffCode", requestProductivityDTO.getStaffId());
        query.setParameter("status", requestProductivityDTO.getStatus());
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("requestType", requestType);
        query.setParameter("busType", busType);
        query.setParameter("channelType", chanelType);
        query.setParameter("receiverUser", requestProductivityDTO.getReceiveUser());
        List<Object[]> rsQuery = query.getResultList();
        if (!DataUtil.isNullOrEmpty(rsQuery)) {
            return rsQuery.stream().map(ProductivityReportDetailDTO::new).collect(Collectors.toList());
        }
        return null;
    }

    public Page<ProductivityReportDetailDTO> getLstProductivityDetailPage(RequestProductivityDTO requestProductivityDTO, Pageable pageable) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fromDate = null;
        String toDate = null;
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getFromDate())) {
            fromDate = format.format(requestProductivityDTO.getFromDate());
        }
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getToDate())) {
            toDate = format.format(requestProductivityDTO.getToDate());
        }
        if (DataUtil.isNullOrEmpty(requestProductivityDTO.getReceiveUser())) {
            requestProductivityDTO.setReceiveUser("");
        }
        String chanelType = "";
        if (requestProductivityDTO.getChannelType() != null && !requestProductivityDTO.getChannelType().equals("null")) {
            chanelType = requestProductivityDTO.getChannelType();
        }
        String requestType = "";
        if (requestProductivityDTO.getRequestType() != null && !requestProductivityDTO.getRequestType().equals("null")) {
            requestType = requestProductivityDTO.getRequestType();
        }
        String busType = "";
        if (requestProductivityDTO.getBussinessType() != null && !requestProductivityDTO.getBussinessType().equals("null")) {
            busType = requestProductivityDTO.getBussinessType();
        }
        String strSQL=" select t.ticket_code,\n" +
            "       tr.ticket_request_code,\n" +
            "       (select name from ap_domain ap where ap.type = 'CHANEL_TYPE' and t.CHANEL_TYPE = ap.code)       kenh_tiep_nhan,\n" +
            "       (select name from ap_domain ap where ap.type = 'TICKET_REQUEST_STATUS' and ap.code = tr.status) trang_thai_yc,\n" +
            "       to_char(t.create_datetime, 'dd/mm/yyyy')                                                                      ngay_tiep_nhan\n" +
            "from ticket t,\n" +
            "     ticket_request tr,\n" +
            "     JHI_USER u\n" +
            "where t.ticket_id = tr.ticket_id\n" +
            "  and u.id=tr.create_user\n" +
            "  and lower (t.create_user) = :staffCode\n" +
            "  and t.STATUS = :status\n" +
            "  and (:fromDate is null or t.create_datetime>=to_date(:fromDate, 'dd/MM/yyyy'))\n" +
            "  and (:toDate is null or t.create_datetime<to_date(:toDate, 'dd/MM/yyyy')+1 ) ";
        if (!requestType.equals(""))
        {
            strSQL+="and tr.request_type = :requestType ";
        }
        if (!busType.equals(""))
        {
            strSQL+="and tr.bussiness_type=:busType ";
        }
        if (!chanelType.equals(""))
        {
            strSQL+="and t.chanel_type=:channelType ";
        }
        if (!requestProductivityDTO.getReceiveUser().equals(""))
        {
            strSQL+="and upper(u.first_name) like '%'||upper(nvl(:receiverUser,u.first_name))||'%'";
        }
        strSQL+=" order by t.ticket_code ";
        Query query = entityManager.createNativeQuery(strSQL);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        query.setParameter("staffCode", requestProductivityDTO.getStaffId());
        query.setParameter("status", requestProductivityDTO.getStatus());
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        if (!requestType.equals(""))
        {
            query.setParameter("requestType", requestType);
        }
        System.out.println("OK");
        if (!busType.equals(""))
        {
            query.setParameter("busType", busType);
        }
        if (!chanelType.equals(""))
        {
            query.setParameter("channelType", chanelType);
        }
        if (!requestProductivityDTO.getReceiveUser().equals(""))
        {
            query.setParameter("receiverUser", requestProductivityDTO.getReceiveUser());
        }

        List<Object[]> rsQuery = query.getResultList();
        List<ProductivityReportDetailDTO> results = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(rsQuery)) {
            results = rsQuery.stream().map(ProductivityReportDetailDTO::new).collect(Collectors.toList());
        }
        Integer countResult = countResult(requestProductivityDTO);
        return new PageImpl<>(results, pageable, countResult);
    }

    private Integer countResult(RequestProductivityDTO requestProductivityDTO) {
        Query query = entityManager.createNativeQuery(query_detail_productivity_count);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fromDate = null;
        String toDate = null;
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getFromDate())) {
            fromDate = format.format(requestProductivityDTO.getFromDate());
        }
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getToDate())) {
            toDate = format.format(requestProductivityDTO.getToDate());
        }
        if (DataUtil.isNullOrEmpty(requestProductivityDTO.getReceiveUser())) {
            requestProductivityDTO.setReceiveUser("");
        }
        String chanelType = "";
        if (requestProductivityDTO.getChannelType() != null && !requestProductivityDTO.getChannelType().equals("null")) {
            chanelType = requestProductivityDTO.getChannelType();
        }
        String requestType = "";
        if (requestProductivityDTO.getRequestType() != null && !requestProductivityDTO.getRequestType().equals("null")) {
            requestType = requestProductivityDTO.getRequestType();
        }
        String busType = "";
        if (requestProductivityDTO.getBussinessType() != null && !requestProductivityDTO.getBussinessType().equals("null")) {
            busType = requestProductivityDTO.getBussinessType();
        }

        query.setParameter("staffCode", requestProductivityDTO.getStaffId());
        query.setParameter("status", requestProductivityDTO.getStatus());
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("requestType", requestType);
        query.setParameter("busType", busType);
        query.setParameter("channelType", chanelType);
        query.setParameter("receiverUser", requestProductivityDTO.getReceiveUser());
        System.out.println("a");
        return DataUtil.safeToInt(query.getSingleResult());
    }
}
