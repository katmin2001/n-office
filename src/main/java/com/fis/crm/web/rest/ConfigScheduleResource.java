package com.fis.crm.web.rest;

import com.fis.crm.commons.DateUtil;
import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.service.ConfigScheduleService;
import com.fis.crm.service.OptionSetValueService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.response.ConfigScheduleError;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.ConfigSchedule}.
 */
@RestController
@RequestMapping("/api")
public class ConfigScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ConfigScheduleResource.class);

    private static final String ENTITY_NAME = "configSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigScheduleService configScheduleService;

    private final ExportUtils exportUtils;

    private final OptionSetValueService optionSetValueService;

    public ConfigScheduleResource(ConfigScheduleService configScheduleService, ExportUtils exportUtils,OptionSetValueService optionSetValueService ) {
        this.configScheduleService = configScheduleService;
        this.exportUtils = exportUtils;
        this.optionSetValueService=optionSetValueService;
    }

    /**
     * {@code POST  /config-schedules} : Create a new configSchedule.
     *
     * @param configScheduleDTO the configScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configScheduleDTO, or with status {@code 400 (Bad Request)} if the configSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-schedules")
    public ResponseEntity<Object> createConfigSchedule(@RequestBody ConfigScheduleDTO configScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigSchedule : {}", configScheduleDTO);

        if (configScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new configSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigScheduleDTO result = configScheduleService.save(configScheduleDTO);
        return ResponseEntity.created(new URI("/api/config-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    @PostMapping("/config-schedules/search")
    public ResponseEntity<List<ConfigScheduleDTO>> searchConfigSchedule(@RequestBody ConfigScheduleDTO configScheduleDTO, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to searchConfigSchedule : {}", configScheduleDTO);
        Page<ConfigScheduleDTO> pages = configScheduleService.onSearchConfigSchedule(configScheduleDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pages);
        return ResponseEntity.ok().headers(headers).body(pages.getContent());
    }

    /**
     * {@code PUT  /config-schedules} : Updates an existing configSchedule.
     *
     * @param configScheduleDTO the configScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the configScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-schedules")
    public ResponseEntity<Object> updateConfigSchedule(@RequestBody ConfigScheduleDTO configScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update ConfigSchedule : {}", configScheduleDTO);

        if (configScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigScheduleDTO result = configScheduleService.save(configScheduleDTO);

        return ResponseEntity.created(new URI("/api/config-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /config-schedules} : get all the configSchedules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configSchedules in body.
     */
    @GetMapping("/config-schedules")
    public ResponseEntity<List<ConfigScheduleDTO>> getAllConfigSchedules(Pageable pageable) {
        log.debug("REST request to get a page of ConfigSchedules");
        Page<ConfigScheduleDTO> page = configScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /config-schedules/:id} : get the "id" configSchedule.
     *
     * @param id the id of the configScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-schedules/{id}")
    public ResponseEntity<ConfigScheduleDTO> getConfigSchedule(@PathVariable Long id) {
        log.debug("REST request to get ConfigSchedule : {}", id);
        Optional<ConfigScheduleDTO> configScheduleDTO = configScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configScheduleDTO);
    }

    /**
     * {@code DELETE  /config-schedules/:id} : delete the "id" configSchedule.
     *
     * @param id the id of the configScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-schedules/{id}")
    public ResponseEntity<Void> deleteConfigSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ConfigSchedule : {}", id);
        configScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @Autowired
    ResourceLoader resourceLoader;

    @PostMapping("/config-schedules/getSampleFile")
    public ResponseEntity<?>  getSampleFile() {
        try {
            List<BussinessTypeDTO>  lsBussiness=optionSetValueService.getBusinessTypeForCombobox();

            List<RequestTypeDTO>  lsRequest=optionSetValueService.getRequestTypeForCombobox();

            ByteArrayInputStream byteArrayInputStream = ExcelUtils.buildTemplateSchedule(lsBussiness,lsRequest);
            Resource resource = new InputStreamResource(byteArrayInputStream);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String fileName = "Filemau" + format.format(new Date());
            return ResponseEntity.ok().header("filename", fileName).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/config-schedules/uploadFile")
    public ResponseEntity<Object> getSampleFile(@RequestParam("file") MultipartFile file) throws Exception {
        log.debug("REST request to import file ConfigSchedule : {}");
        HttpHeaders responseHeader = new HttpHeaders();
        File myFile = new File("Report_Error.xlsx");
        FileOutputStream out = new FileOutputStream(myFile);
        ConfigScheduleDTO result = configScheduleService.importList(file);
        Workbook workbook = result.getXssfWorkbook();
        workbook.write(out);
        out.close();
        FileInputStream fis = new FileInputStream(new File("Report_Error.xlsx"));
        InputStreamResource inputStreamResource = new InputStreamResource(fis);
        responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpStatus responseStatus = HttpStatus.OK;
        if("formatError".equals(result.getErrorCodeConfig()) ){
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(responseStatus)
            .contentLength(myFile.length())
            .header("filename", myFile.getName())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(inputStreamResource);
    }


    @PostMapping("/config-schedules/getProcessTime")
    public ResponseEntity<ServiceResult> getProcessTime(@RequestBody ConfigScheduleDTO configScheduleDTO) {
        return new ResponseEntity<>(configScheduleService.getProcessTime(configScheduleDTO), HttpStatus.OK);
    }

    @PostMapping("/config-schedules/export")
    public ResponseEntity<Resource> getAllexportFileExcelsNoPage(@RequestBody ConfigScheduleDTO configScheduleDTO) throws Exception {
        List<ConfigScheduleDTO> lstDatas = configScheduleService.export(configScheduleDTO);
        List<ExcelColumn> lstColumn = buildColumnExport();
        String title = Translator.toLocale("exportFileExcel.export.configuring.processing.term");
        String fileName = Translator.toLocale("exportFileExcel.export.configuring.processing.term.fileName");
        ExcelTitle excelTitle = new ExcelTitle(title, null, null);
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    private List<ExcelColumn> buildColumnExport() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("requestTypeStr", Translator.toLocale("exportFileExcel.column.requestType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("bussinessTypeStr", Translator.toLocale("exportFileExcel.column.bussinessType"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("processTimeStr", Translator.toLocale("exportFileExcel.column.timeProcess"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("confirmTimeStr", Translator.toLocale("exportFileExcel.column.timeConfirm"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }
}
