package com.fis.crm.web.rest;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ApDomainService;
import com.fis.crm.service.ExportReportService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.ApDomain}.
 */
@RestController
@RequestMapping("/api")
public class ExportReportResource {

    private final Logger log = LoggerFactory.getLogger(ExportReportResource.class);

    private static final String ENTITY_NAME = "apDomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApDomainService apDomainService;

    private final ExportReportService exportReportService;

    private final ExportUtils exportUtils;

    private final UserService userService;

    private final ActionLogService actionLogService;

    public ExportReportResource(ApDomainService apDomainService, ExportReportService exportReportService, ExportUtils exportUtils, UserService userService, ActionLogService actionLogService) {
        this.apDomainService = apDomainService;
        this.exportReportService = exportReportService;
        this.exportUtils = exportUtils;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    /**
     * {@code POST  /export-report} : Create a new apDomain.
     *
     * @param apDomainDTO the apDomainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apDomainDTO, or with status {@code 400 (Bad Request)} if the apDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/export-report")
    public ResponseEntity<ApDomainDTO> createApDomain(@RequestBody ApDomainDTO apDomainDTO) throws URISyntaxException {
        log.debug("REST request to save ApDomain : {}", apDomainDTO);
        if (apDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new apDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApDomainDTO result = apDomainService.save(apDomainDTO);
        return ResponseEntity.created(new URI("/api/export-report/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /export-report} : Updates an existing apDomain.
     *
     * @param apDomainDTO the apDomainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apDomainDTO,
     * or with status {@code 400 (Bad Request)} if the apDomainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apDomainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/export-report")
    public ResponseEntity<ApDomainDTO> updateApDomain(@RequestBody ApDomainDTO apDomainDTO) throws URISyntaxException {
        log.debug("REST request to update ApDomain : {}", apDomainDTO);
        if (apDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApDomainDTO result = apDomainService.save(apDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /export-report} : get all the apDomains.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apDomains in body.
     */
    @GetMapping("/export-report")
    public ResponseEntity<List<ApDomainDTO>> getAllApDomains(Pageable pageable) {
        log.debug("REST request to get a page of ApDomains");
        Page<ApDomainDTO> page = apDomainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /export-report/:id} : get the "id" apDomain.
     *
     * @param id the id of the apDomainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apDomainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/export-report/{id}")
    public ResponseEntity<ApDomainDTO> getApDomain(@PathVariable Long id) {
        log.debug("REST request to get ApDomain : {}", id);
        Optional<ApDomainDTO> apDomainDTO = apDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apDomainDTO);
    }

    /**
     * {@code DELETE  /export-report/:id} : delete the "id" apDomain.
     *
     * @param id the id of the apDomainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/export-report/{id}")
    public ResponseEntity<Void> deleteApDomain(@PathVariable Long id) {
        log.debug("REST request to delete ApDomain : {}", id);
        apDomainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/export-report/findDomainByType")
    public ResponseEntity<List<ApDomainDTO>> findAllByType(String type) {
        List<ApDomainDTO> results = apDomainService.findAllByType(type);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(type));
        return ResponseEntity.ok().headers(headers).body(results);
    }

    @PostMapping("/export-report/query-complaint-detail")
    public ResponseEntity<List<ReportComplaintDetailDTO>> exportReportComplaintDetail(@RequestBody RequestReportTicketDTO requestReportTicketDTO, Pageable pageable) {
        requestReportTicketDTO.setOffSet(DataUtil.safeToLong(pageable.getPageNumber()));
        requestReportTicketDTO.setPageSize(pageable.getPageSize());
        Page<ReportComplaintDetailDTO> page = exportReportService.getReportComplaintWeb(requestReportTicketDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/export-report/export-complaint-detail")
    public ResponseEntity<Resource> getAllexportFileExcelsNoPage(@RequestBody RequestReportTicketDTO requestReportTicketDTO) throws Exception {
        List<ReportComplaintDetailDTO> lstDatas = exportReportService.getReportComplaintDetail(requestReportTicketDTO);
        List<ExcelColumn> lstColumn = buildColumnExportComplaintDetail();
        String title = Translator.toLocale("exportFileExcel.export.title");
        String fileName = Translator.toLocale("exportFileExcel.export.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestReportTicketDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestReportTicketDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, "Xuất excel: Báo cáo chi tiết sự vụ phản ánh",
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.report_complaint_detail, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    private List<ExcelColumn> buildColumnExportComplaintDetail() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("ticketCode", Translator.toLocale("exportFileExcel.column.ticketCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketRequestCode", Translator.toLocale("exportFileExcel.column.ticketRequestCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("chanelType", Translator.toLocale("exportFileExcel.column.chanelType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestType", Translator.toLocale("exportFileExcel.column.requestType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("bussinessType", Translator.toLocale("exportFileExcel.column.bussinessType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", Translator.toLocale("exportFileExcel.column.phoneNumber"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("departmentName", Translator.toLocale("exportFileExcel.column.departmentName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("priority", Translator.toLocale("exportFileExcel.column.priority"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketStatus", Translator.toLocale("exportFileExcel.column.ticketStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestStatus", Translator.toLocale("exportFileExcel.column.requestStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDatetime", Translator.toLocale("exportFileExcel.column.createDatetime"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("userName", Translator.toLocale("exportFileExcel.column.userNamer"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }


    @PostMapping("/export-report/query-process-time")
    public ResponseEntity<List<ReportProcessTimeDTO>> exportReportComplaintDetail(@RequestBody RequestReportTimeProcessDTO requestReportTimeProcessDTO, Pageable pageable) {
        requestReportTimeProcessDTO.setOffSet(DataUtil.safeToLong(pageable.getPageNumber()));
        requestReportTimeProcessDTO.setPageSize(pageable.getPageSize());
        Page<ReportProcessTimeDTO> page = exportReportService.getReportProcessTimeWeb(requestReportTimeProcessDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/export-report/export-process-time")
    public ResponseEntity<Resource> getAllexportFileExcelsNoPage(@RequestBody RequestReportTimeProcessDTO requestReportTimeProcessDTO) throws Exception {
        List<ReportProcessTimeDTO> lstDatas = exportReportService.getReportProcessTime(requestReportTimeProcessDTO);
        List<ExcelColumn> lstColumn = buildColumnExportProcessTime();
        String title = Translator.toLocale("exportFileExcel.export.titleProcessTime");
        String fileName = Translator.toLocale("exportFileExcel.export.fileNameProcessTime");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestReportTimeProcessDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestReportTimeProcessDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, "Xuất excel: Báo cáo thời gian sử lý",
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.report_process_time, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    private List<ExcelColumn> buildColumnExportProcessTime() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("ticketCode", Translator.toLocale("exportFileExcel.column.ticketCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketRequestCode", Translator.toLocale("exportFileExcel.column.ticketRequestCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("idCode", Translator.toLocale("exportFileExcel.column.idCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("departmentName", Translator.toLocale("exportFileExcel.column.departmentName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("priority", Translator.toLocale("exportFileExcel.column.priority"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketStatus", Translator.toLocale("exportFileExcel.column.ticketStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestStatus", Translator.toLocale("exportFileExcel.column.requestStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processDeadline", Translator.toLocale("exportFileExcel.column.processDeadline"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("confirmDeadline", Translator.toLocale("exportFileExcel.column.confirmDeadline"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("receiveDate", Translator.toLocale("exportFileExcel.column.receiveDate"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("receiveUser", Translator.toLocale("exportFileExcel.column.receiveUser"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processDate", Translator.toLocale("exportFileExcel.column.processDate"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processUser", Translator.toLocale("exportFileExcel.column.processUser"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("confirmDate", Translator.toLocale("exportFileExcel.column.confirmDate"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("confirmUser", Translator.toLocale("exportFileExcel.column.confirmUser"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    @PostMapping("/export-report/query-complaint-contact-center")
    public ResponseEntity<List<ReportComplaintContactCenterDTO>> queryComplaintContactCenter(@RequestBody RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO, Pageable pageable) {
        requestReportComplaintContactCenterDTO.setOffSet(DataUtil.safeToLong(pageable.getPageNumber()));
        requestReportComplaintContactCenterDTO.setPageSize(pageable.getPageSize());
        Page<ReportComplaintContactCenterDTO> page = exportReportService.getReportComplaintContactCenterWeb(requestReportComplaintContactCenterDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/export-report/export-complaint-contact-center")
    public ResponseEntity<Resource> exportComplaintContactCenter(@RequestBody RequestReportComplaintContactCenterDTO requestReportComplaintContactCenterDTO) throws Exception {
        List<ReportComplaintContactCenterDTO> lstDatas = exportReportService.getReportComplaintContactCenter(requestReportComplaintContactCenterDTO);
        List<ExcelColumn> lstColumn = buildColumnComplaintContactCenter();
        String title = Translator.toLocale("exportFileExcel.export.titleComplaintContactCenter");
        String fileName = Translator.toLocale("exportFileExcel.export.fileNameComplaintContactCenter");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestReportComplaintContactCenterDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestReportComplaintContactCenterDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, "Xuất excel: Báo cáo thắc mắc khiếu nại tổng đài CSKH",
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.report_contact_center, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }
//    @PostMapping("/export-report/export-campaign-email-black-list")
//    public ResponseEntity<Resource> exportCampaignEmailBlackList(@RequestBody ReportCampaignEmailBlackListDTO reportCampaignEmailBlackListDTO) throws Exception {
//        List<ReportCampaignEmailBlackListDTO> lstDatas = exportReportService.getReportCampaignEmailBlackList(reportCampaignEmailBlackListDTO);
//
//        List<ExcelColumn> lstColumn = buildColumnComplaintContactCenter();
//        String title = Translator.toLocale("exportFileExcel.export.titleComplaintContactCenter");
//        String fileName = Translator.toLocale("exportFileExcel.export.fileNameComplaintContactCenter");
//        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestReportComplaintContactCenterDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestReportComplaintContactCenterDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
//        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
//        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
//        return ResponseEntity.ok()
//            .contentLength(byteArrayInputStream.available())
//            .header("filename", fileName)
//            .contentType(MediaType.parseMediaType("application/octet-stream"))
//            .body(resource);
//    }

    private List<ExcelColumn> buildColumnComplaintContactCenter() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("ticketCode", Translator.toLocale("exportFileExcel.column.ticketCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketRequestCode", Translator.toLocale("exportFileExcel.column.ticketRequestCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("chanelType", Translator.toLocale("exportFileExcel.column.chanelType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("bussinessType", Translator.toLocale("exportFileExcel.column.bussinessType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestContent", Translator.toLocale("exportFileExcel.column.requestContent"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestProcess", Translator.toLocale("exportFileExcel.column.requestProcess"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestStatus", Translator.toLocale("exportFileExcel.column.requestStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("satisfactionLevel", Translator.toLocale("exportFileExcel.column.satisfactionLevel"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("receiveDate", Translator.toLocale("exportFileExcel.column.receiveDate"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("receiveUser", Translator.toLocale("exportFileExcel.column.receiveUser"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }


    @PostMapping("/export-report/query-productivity")
    public ResponseEntity<List<ProductivityReportDTO>> queryComplaintContactCenter(@RequestBody RequestProductivityDTO requestProductivityDTO, Pageable pageable) {
        requestProductivityDTO.setOffSet(DataUtil.safeToLong(pageable.getPageNumber()));
        requestProductivityDTO.setPageSize(pageable.getPageSize());
        Page<ProductivityReportDTO> page = exportReportService.getProductivityWeb(requestProductivityDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private String getTitleNameByStatus(Long status) {
        return Constants.getMapTitleSatus().get(status);
    }

    @PostMapping("/export-report/export-productivity")
    public ResponseEntity<Resource> exportComplaintContactCenter(@RequestBody RequestProductivityDTO requestProductivityDTO) throws Exception {
        List<ProductivityReportDTO> lstDatas = exportReportService.getProductivity(requestProductivityDTO);
        List<ProductivityReportDetailDTO> lstDatasDetails = new ArrayList<>();
        List<ExcelColumn> lstColumnDetails = new ArrayList<>();
        String titleChild2 = "";
        if (!DataUtil.isNullOrEmpty(requestProductivityDTO.getStaffId())) {
            lstDatasDetails = exportReportService.getLstProductivityDetail(requestProductivityDTO);
            Optional<UserDTO> user = userService.findFirstById(DataUtil.safeToLong(requestProductivityDTO.getStaffId()));
            lstColumnDetails = buildColumnProductivityDetails();
            String titleStatus = Translator.toLocale(getTitleNameByStatus(requestProductivityDTO.getStatus()));
            titleChild2 = Translator.toLocale("exportFileExcel.export.titleProductivityChild", new Object[]{user.get().getLogin(), titleStatus});

        }
        List<ExcelColumn> lstColumn = buildColumnProductivity();
        String title = Translator.toLocale("exportFileExcel.export.titleProductivity");
        String titleChild1 = Translator.toLocale("exportFileExcel.export.titleProductivity");

        String sheetName1 = Translator.toLocale("exportFileExcel.export.sheetNameProductivity");
        String fileName = Translator.toLocale("exportFileExcel.export.fileNameProductivity");
        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestProductivityDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestProductivityDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport2Table(lstColumn, lstColumnDetails, lstDatas, lstDatasDetails, 3, 0, excelTitle, true, titleChild1, titleChild2, sheetName1);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        Optional<User> userLog = userService.getUserWithAuthorities();
        actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.EXPORT + "",
            null, "Xuất excel: Báo cáo năng xuất tổng đài viên",
            new Date(), Constants.MENU_ID.VOC, Constants.MENU_ITEM_ID.report_complaint, "CONFIG_MENU_ITEM"));
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    private List<ExcelColumn> buildColumnProductivity() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("staffName", Translator.toLocale("exportFileExcel.column.staffName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("notProcess", Translator.toLocale("exportFileExcel.column.notProcess"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processing", Translator.toLocale("exportFileExcel.column.processing"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processed", Translator.toLocale("exportFileExcel.column.processed"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("confirming", Translator.toLocale("exportFileExcel.column.confirming"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("complete", Translator.toLocale("exportFileExcel.column.complete"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    private List<ExcelColumn> buildColumnProductivityDetails() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("ticketCode", Translator.toLocale("exportFileExcel.column.ticketCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ticketRequestCode", Translator.toLocale("exportFileExcel.column.ticketRequestCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("channelReceive", Translator.toLocale("exportFileExcel.column.chanelType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("requestStatus", Translator.toLocale("exportFileExcel.column.requestStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDateTime", Translator.toLocale("exportFileExcel.column.createDatetime"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    /**
     * 1: chua xu ly; 2: dang xu ly; 3: da xu ly; 4: Dang xac nhan; 5: hoan thanh
     *
     * @param requestProductivityDTO
     * @param pageable
     * @return
     */
    @PostMapping("/export-report/query-productivity-detail")
    public ResponseEntity<List<ProductivityReportDetailDTO>> getLstProductivityDetailPage(@RequestBody RequestProductivityDTO requestProductivityDTO, Pageable pageable) {
        requestProductivityDTO.setOffSet(DataUtil.safeToLong(pageable.getPageNumber()));
        requestProductivityDTO.setPageSize(pageable.getPageSize());
        Page<ProductivityReportDetailDTO> page = exportReportService.getLstProductivityDetailPage(requestProductivityDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
