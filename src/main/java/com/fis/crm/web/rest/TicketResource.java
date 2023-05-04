package com.fis.crm.web.rest;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.TicketRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.TicketService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.TicketMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.Ticket}.
 */
@RestController
@RequestMapping("/api")
public class TicketResource implements ServletContextAware {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    private ServletContext servletContext;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketService ticketService;

    private final TicketRepository ticketRepository;

    private final ExportUtils exportUtils;

    private final UserService userService;

    private final ActionLogService actionLogService;

    private final Environment env;

    private final TicketMapper ticketMapper;

    public TicketResource(TicketService ticketService, TicketRepository ticketRepository, ExportUtils exportUtils, UserService userService, Environment env,
                          ActionLogService actionLogService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.exportUtils = exportUtils;
        this.userService = userService;
        this.env = env;
        this.actionLogService=actionLogService;
        this.ticketMapper = ticketMapper;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tickets/create")
    public ResponseEntity<ServiceResult> createTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        //HieuNT :  Khi tao xong su vu, van duoc them YC
//        if (ticketDTO.getTicketId() != null) {
//            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        // sử lý date truyền vào (-7h)
        List<TicketRequestDTO> ticketRequestDTOS = ticketDTO.getListTicketRequestDTOS();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        for (TicketRequestDTO d : ticketRequestDTOS){
            if (d.getConfirmDate() != null){
                c2.setTime(d.getConfirmDate());
                c2.add(Calendar.MONTH, 0);
                c2.set(Calendar.DAY_OF_MONTH, 1);
                c2.add(Calendar.DATE, -1);
                Date lastDayOfMonth = c2.getTime();
                int lastDateMonth = lastDayOfMonth.getDate();

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                int hour = d.getConfirmDate().getHours();
                int day = d.getConfirmDate().getDate();
                int month = d.getConfirmDate().getMonth();
                int year = d.getConfirmDate().getYear();

                if (hour >= 0 && hour <= 7 && day == 1 && month == 0){
                    d.getConfirmDate().setYear(year-1);
                    d.getConfirmDate().setMonth(11);
                    d.getConfirmDate().setDate(lastDateMonth);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }
                if (hour >= 0 && hour <= 7 && day == 1){
                    d.getConfirmDate().setMonth(month-1);
                    d.getConfirmDate().setDate(lastDateMonth);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }
                if (hour >= 0 && hour <= 7){
                    d.getConfirmDate().setDate(day-1);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                } else {
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }

            }
        }
        ServiceResult result = ticketService.save(ticketDTO);
        if (result == null) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.create.ticket"), "ticketDTO", "user.has.no.right.to.create.ticket");
        }
        return ResponseEntity.created(new URI("/api/tickets/" + result.getTicketId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTicketId().toString()))
            .body(result);
    }

    @PostMapping("/tickets/create-inactive")
    public ResponseEntity<ServiceResult> createTicketInactive(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        //HieuNT :  Khi tao xong su vu, van duoc them YC
//        if (ticketDTO.getTicketId() != null) {
//            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        // sử lý date truyền vào (-7h)
        List<TicketRequestDTO> ticketRequestDTOS = ticketDTO.getListTicketRequestDTOS();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        for (TicketRequestDTO d : ticketRequestDTOS){
            if (d.getConfirmDate() != null){
                c2.setTime(d.getConfirmDate());
                c2.add(Calendar.MONTH, 0);
                c2.set(Calendar.DAY_OF_MONTH, 1);
                c2.add(Calendar.DATE, -1);
                Date lastDayOfMonth = c2.getTime();
                int lastDateMonth = lastDayOfMonth.getDate();

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                int hour = d.getConfirmDate().getHours();
                int day = d.getConfirmDate().getDate();
                int month = d.getConfirmDate().getMonth();
                int year = d.getConfirmDate().getYear();

                if (hour >= 0 && hour <= 7 && day == 1 && month == 0){
                    d.getConfirmDate().setYear(year-1);
                    d.getConfirmDate().setMonth(11);
                    d.getConfirmDate().setDate(lastDateMonth);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }
                if (hour >= 0 && hour <= 7 && day == 1){
                    d.getConfirmDate().setMonth(month-1);
                    d.getConfirmDate().setDate(lastDateMonth);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }
                if (hour >= 0 && hour <= 7){
                    d.getConfirmDate().setDate(day-1);
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                } else {
                    c1.setTime(d.getConfirmDate());
                    c1.roll(Calendar.HOUR_OF_DAY, -7);
                    d.setConfirmDate(c1.getTime());
                }

            }
        }
        ServiceResult result = ticketService.saveInactive(ticketDTO);
        if (result == null) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.create.ticket"), "ticketDTO", "user.has.no.right.to.create.ticket");
        }
        return ResponseEntity.created(new URI("/api/tickets/" + result.getTicketId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTicketId().toString()))
            .body(result);
    }

    @PostMapping("/tickets/update")
    public ResponseEntity<ServiceResult> updateTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getTicketId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (!user.getCreateTicket()) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
        }
        ServiceResult result = ticketService.save(ticketDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getTicketId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTicketId().toString()))
            .body(result);
    }

    @PostMapping(value = "/tickets/upload-file"
//        , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<MessageResponse<ServiceResult>> uploadFile(@RequestParam("files") MultipartFile[] files) {
        ServiceResult serviceResult = new ServiceResult();
        List<TicketRequestAttachmentDTO> listTicketRequestAttachmentDTOS = new ArrayList<>();
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (!user.getConfirmTicket() || null == user.getConfirmTicket()) {
//            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
            return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("user.has.no.right.to.process.ticket"), null), HttpStatus.BAD_REQUEST);
        }
        Double maxFileSize = DataUtil.safeToDouble(env.getProperty("file-manager.max-size"));
        String fileExt = env.getProperty("file-manager.file-ext");
        for (MultipartFile file : files) {
            Long validate = FileUtils.validateAttachFile(file, fileExt, maxFileSize);
            if (!validate.equals(0L)) {
                if (validate.equals(24L)) {
                    serviceResult.setStatus(ServiceResult.Status.FAILED);
//                    throw new BadRequestAlertException(Translator.toLocale("error.format.file"), "ticketDTO", "error.format.file");
                    return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("error.format.file"), null), HttpStatus.BAD_REQUEST);
                }
                if (validate.equals(25L)) {
//                    throw new BadRequestAlertException(Translator.toLocale("error.file.size"), "ticketDTO", "error.file.size");
                    return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("error.file.size"), null), HttpStatus.BAD_REQUEST);
                }
            }
            try {
                String encryptFileName = saveFile(file);
                TicketRequestAttachmentDTO ticketRequestAttachmentDTO = new TicketRequestAttachmentDTO();
                ticketRequestAttachmentDTO.setFileName(file.getOriginalFilename());
                ticketRequestAttachmentDTO.setFillNameEncrypt(encryptFileName);
                ticketRequestAttachmentDTO.setCreateDatetime(Instant.now());
                ticketRequestAttachmentDTO.setTypeFile(file.getContentType());
                listTicketRequestAttachmentDTOS.add(ticketRequestAttachmentDTO);

            } catch (Exception e) {
                serviceResult.setStatus(ServiceResult.Status.FAILED);
                serviceResult.setMessage(Translator.toLocale("error.upload.file"));
                return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("error.upload.file"),serviceResult), HttpStatus.BAD_REQUEST);
            }
        }
        serviceResult.setStatus(ServiceResult.Status.SUCCESS);
        serviceResult.setMessage(Translator.toLocale("upload.file.success"));
        serviceResult.setListRequestAttachmentDTOList(listTicketRequestAttachmentDTOS);

        return new ResponseEntity<>(new MessageResponse<>(null, serviceResult), HttpStatus.OK);
    }

    private String saveFile(MultipartFile file) {
        try {
            String folder = env.getProperty("file-manager.request-file");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String fileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename().replace(" ", "_").replaceAll("[\\\\/:*?\"<>|]", "_");
            String filePath = folder + fileName;
            FileUtils.saveFile(filePath, file);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@code GET  /tickets} : get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets in body.
     */
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets(Pageable pageable) {
        log.debug("REST request to get a page of Tickets");
        Page<TicketDTO> page = ticketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tickets/:id} : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        Optional<TicketDTO> ticketDTO = ticketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketDTO);
    }

    @GetMapping("/tickets/get-for-process/{id}")
    public ResponseEntity<TicketDTO> getTicketForProcess(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        Optional<TicketDTO> ticketDTO = ticketService.findOne(id);
        ticketService.updateForProcess(ticketDTO.get());
        return ResponseUtil.wrapOrNotFound(ticketDTO);
    }

    /**
     * {@code DELETE  /tickets/:id} : delete the "id" ticket.
     *
     * @param id the id of the ticketDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/tickets/getListHistorySupports")
    public ResponseEntity<List<TicketDTO>> getListHistorySupports(@RequestBody TicketDTO ticketDTO, Pageable pageable) {
        log.debug("REST request to get list of history support");
        Page<TicketDTO> page = ticketService.getListHistorySupports(ticketDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostMapping("/querySearch")
    public ResponseEntity<List<TicketDTO>> searchTicket(@RequestBody RequestSearchTicketDTO requestSearchTicketDTO, Pageable pageable) {
        log.debug("REST request to get a page of CatGroupCharts");
        Page<TicketDTO> page = ticketService.searchTicket(requestSearchTicketDTO, pageable);
        if (page != null) {
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else return new ResponseEntity <>(null, HttpStatus.OK);
    }

    private List<ExcelColumn> buildColumnExportProcessTime() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("ticketCode", Translator.toLocale("exportFileExcel.column.ticketCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("channelName", Translator.toLocale("exportFileExcel.column.channelName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("customerName", Translator.toLocale("exportFileExcel.column.customerName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("cid", Translator.toLocale("exportFileExcel.column.idCode"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("companyName", Translator.toLocale("exportFileExcel.column.companyName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("phoneNumber", Translator.toLocale("exportFileExcel.column.phoneNumber1"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("email", Translator.toLocale("exportFileExcel.column.email"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processStatus", Translator.toLocale("exportFileExcel.column.processStatus"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processUser", Translator.toLocale("exportFileExcel.column.processUser1"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("receiveDateTime", Translator.toLocale("exportFileExcel.column.receiveDateTime"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    @PostMapping("/export-ticket-not-process")
    public ResponseEntity<Resource> exportTicketNotProcess(@RequestBody RequestSearchTicketDTO requestSearchTicketDTO) throws Exception {
        List<TicketDTO> lstDatas = ticketService.exportExcelTicket(requestSearchTicketDTO);
        List<ExcelColumn> lstColumn = buildColumnExportProcessTime();
        List<TicketDTO> lstData1 = new ArrayList<TicketDTO>();
        List<TicketDTO> lstData2 = new ArrayList<TicketDTO>();
        //Neu la du lieu chua xua ly va dang xu ly
        if (requestSearchTicketDTO.getTabType().equals(1L)) {

            for (TicketDTO ls : lstDatas) {

                if(ls.getStatus().equals("1"))
                {
                    ls.setProcessStatus("");
                }
                else
                {
                    if(ls.getTicketStatus().equals("2"))
                    {
                        ls.setProcessStatus("Quá hạn");
                    }
                    else {
                        ls.setProcessStatus("Trong hạn");
                    }
                }

                if ("1".equals(ls.getStatus())) {
                    lstData1.add(ls);
                } else {
                    lstData2.add(ls);
                }
            }
        }
        //Neu la du lieu da xu ly va dang xac nhan
        if (requestSearchTicketDTO.getTabType().equals(2L)) {
            for (TicketDTO ls : lstDatas) {
                if(ls.getTicketStatus().equals("2"))
                {
                    ls.setProcessStatus("Quá hạn");
                }
                else
                {
                    ls.setProcessStatus("Trong hạn");
                }
                if ("3".equals(ls.getStatus())) {
                    lstData1.add(ls);
                } else {
                    lstData2.add(ls);
                }
            }
        }
        //Neu la du lieu da xu ly va dang xac nhan
        if (requestSearchTicketDTO.getTabType().equals(3L)) {
            for (TicketDTO ls : lstDatas) {
                if(ls.getCompareDateConfirm()>0)
                {
                    ls.setProcessStatus("Quá hạn");
                }
                else
                {
                    ls.setProcessStatus("Đúng hạn");
                }
            }
        }

        String title = Translator.toLocale("search.ticket." + requestSearchTicketDTO.getTabType());
        String fileName = Translator.toLocale("report.ticket");

        ExcelTitle excelTitle = new ExcelTitle(title, DateUtil.dateToString(requestSearchTicketDTO.getFromDate(), Constants.DATE_FORMAT_DDMMYYY), DateUtil.dateToString(requestSearchTicketDTO.getToDate(), Constants.DATE_FORMAT_DDMMYYY));
        ByteArrayInputStream byteArrayInputStream = null;
        if (requestSearchTicketDTO.getTabType().equals(1L)) {
            lstColumn.get(7).setTitle("Trạng thái hạn xử lý");
            byteArrayInputStream = exportUtils.onExport2Sheet(lstColumn, lstData1, lstData2,
                3, 0, excelTitle, true, Translator.toLocale("exportFileExcel.column.sheet.1"), Translator.toLocale("exportFileExcel.column.sheet.2")); //Lưu ý chỗ này cần gọi từ từ điển ra
        }
        if (requestSearchTicketDTO.getTabType().equals(2L)) {
            lstColumn.get(7).setTitle("Trạng thái hạn xác nhận");
            byteArrayInputStream = exportUtils.onExport2Sheet(lstColumn, lstData1, lstData2,
                3, 0, excelTitle, true, Translator.toLocale("exportFileExcel.column.sheet.4"), Translator.toLocale("exportFileExcel.column.sheet.5")); //Lưu ý chỗ này cần gọi từ từ điển ra
        }
        if (requestSearchTicketDTO.getTabType().equals(3L)) {
            lstColumn.get(7).setTitle("Trạng thái hạn xác nhận");
            byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true, Translator.toLocale("exportFileExcel.column.sheet.3")); //Lưu ý chỗ này cần gọi từ từ điển ra

        }
        if (byteArrayInputStream != null) {
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
            return ResponseEntity.ok()
                .contentLength(byteArrayInputStream.available())
                .header("filename", fileName)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
        }
        return null;
    }

    @GetMapping("/tickets/download-file-attachment/{nameFile}")
    public Object downloadFileAttachment(@PathVariable(name = "nameFile") String nameFile) {
        String path = env.getProperty("file-manager.request-file");
        return FileUtils.downloadFile(path, nameFile);
    }

    @GetMapping("/tickets/get-file")
    public ResponseEntity<Resource> getFile (@RequestParam(value = "file-name") String fileName) {
        try {
            InputStreamResource resource = null;
            String path = env.getProperty("file-manager.request-file");
            path = path + fileName;
            File file = new File(path);
            if (!file.exists()) {
                throw new BadRequestAlertException("Tải file thất bại", ENTITY_NAME, "");
            }

            resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (FileNotFoundException e) {
            throw new BadRequestAlertException("Tải file thất bại", ENTITY_NAME, "");
        }
    }

    @GetMapping("/tickets/update-confirm-deadline/{id}")
    public ResponseEntity<TicketDTO> updateConfirmDeadLine(@PathVariable("id") Long id) {
        Optional<TicketDTO> ticketDTO = ticketService.findOne(id);
        ticketService.updateTicketConfirmDate(ticketMapper.toEntity(ticketDTO.get()));
        return ResponseEntity.ok().body(ticketDTO.get());
    }

}

