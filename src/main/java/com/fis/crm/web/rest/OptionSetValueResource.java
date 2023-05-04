package com.fis.crm.web.rest;

import com.fis.crm.commons.ExportUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.service.OptionSetValueService;
import com.fis.crm.service.dto.ExcelColumn;
import com.fis.crm.service.dto.ExcelTitle;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.service.dto.QuerySearchOptionSetDTO;
import com.fis.crm.service.response.ConfigScheduleError;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.OptionSetValue}.
 */
@RestController
@RequestMapping("/api")
public class OptionSetValueResource {

    private final Logger log = LoggerFactory.getLogger(OptionSetValueResource.class);

    private static final String ENTITY_NAME = "optionSetValueValue";

    private final ExportUtils exportUtils;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionSetValueService optionSetValueService;

    public OptionSetValueResource(ExportUtils exportUtils, OptionSetValueService optionSetValueService) {
        this.exportUtils = exportUtils;
        this.optionSetValueService = optionSetValueService;
    }

    /**
     * @param optionSetDTO the configScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configScheduleDTO, or with status {@code 400 (Bad Request)} if the configSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option_set_value")
    public ResponseEntity<Object> createOptionSetValue(@RequestBody OptionSetValueDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to save OptionSetValue : {}", optionSetDTO);
        try {
            if (optionSetDTO.getId() != null) {
                throw new BadRequestAlertException("A new optionSetValue cannot already have an ID", ENTITY_NAME, "idexists");
            }

            OptionSetValueDTO result = optionSetValueService.save(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set_value/" + result.getOptionSetId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOptionSetId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * @param optionSetDTO the configScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the configScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option_set_value")
    public ResponseEntity<Object> updateOptionSetValueDTO(@RequestBody OptionSetValueDTO optionSetDTO) throws URISyntaxException {

        log.debug("REST request to save OptionSetValue : {}", optionSetDTO);
        try {
            if (optionSetDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            OptionSetValueDTO result = optionSetValueService.save(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set_value/" + result.getOptionSetId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOptionSetId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configSchedules in body.
     */
    @GetMapping("/option_set_value")
    public ResponseEntity<List<OptionSetValueDTO>> getAllOptionSetValueDTO(Pageable pageable) {
        Page<OptionSetValueDTO> page = optionSetValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/option_set_value/export_option_set_value")
    public ResponseEntity<Resource> exportComplaintContactCenter(@RequestBody QuerySearchOptionSetDTO querySearchOptionSetDTO) throws Exception {
        List<OptionSetValueDTO> lstDatas = optionSetValueService.getListDataOptionSetValue(querySearchOptionSetDTO);
        List<ExcelColumn> lstColumn = buildColum();
        String title = Translator.toLocale("exportFileExcel.export.optionSetValue");
        String fileName = Translator.toLocale("exportFileExcel.export.optionSetValueName");
        ExcelTitle excelTitle = new ExcelTitle(title, null, null);
        ByteArrayInputStream byteArrayInputStream = exportUtils.onExport(lstColumn, lstDatas, 3, 0, excelTitle, true);
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
            .contentLength(byteArrayInputStream.available())
            .header("filename", fileName)
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    private List<ExcelColumn> buildColum() {
        List<ExcelColumn> lstColumn = new ArrayList<>();
        lstColumn.add(new ExcelColumn("code", Translator.toLocale("exportFileExcel.column.code"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("name", Translator.toLocale("exportFileExcel.column.name"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("ord", Translator.toLocale("exportFileExcel.column.ord"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("groupName", Translator.toLocale("exportFileExcel.column.groupName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("fromDateView", Translator.toLocale("exportFileExcel.column.fromDateView"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("endDateView", Translator.toLocale("exportFileExcel.column.endDateView"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createUserName", Translator.toLocale("exportFileExcel.column.createUserName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("createDateView", Translator.toLocale("exportFileExcel.column.createDateView"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("updateUserName", Translator.toLocale("exportFileExcel.column.updateUserName"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("updateDateView", Translator.toLocale("exportFileExcel.column.updateDateView"), ExcelColumn.ALIGN_MENT.LEFT));
        lstColumn.add(new ExcelColumn("status", Translator.toLocale("exportFileExcel.column.status"), ExcelColumn.ALIGN_MENT.LEFT));
        return lstColumn;
    }

    /**
     * {@code GET  /config-schedules/:id} : get the "id" configSchedule.
     *
     * @param id the id of the configScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option_set_value/{id}")
    public ResponseEntity<OptionSetValueDTO> getOptionSetValueDTO(@PathVariable Long id) {
        Optional<OptionSetValueDTO> optionSetDTO = optionSetValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionSetDTO);
    }

    /**
     * {@code DELETE  /config-schedules/:id} : delete the "id" configSchedule.
     *
     * @param id the id of the configScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option_set_value/{id}")
    public ResponseEntity<Void> deleteOptionSetValueDTO(@PathVariable Long id) {
        log.debug("REST request to delete OptionSetValue : {}", id);
        optionSetValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/option_set_value/search")
    public ResponseEntity<List<OptionSetValueDTO>> searchOptionSet(@RequestBody OptionSetValueDTO obj) throws URISyntaxException {
        log.debug("REST request to search ConfigScheduleValue : {}", obj);
        List<OptionSetValueDTO> page = optionSetValueService.find(obj);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PutMapping("/option_set_value/change_status")
    public ResponseEntity<Object> changeStatus(@RequestBody OptionSetValueDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to changeStatus : {}", optionSetDTO);
        try {
            if (optionSetDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }

            OptionSetValueDTO result = optionSetValueService.changeStatus(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set_value/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/option_set_value/query")
    public ResponseEntity<List<OptionSetValueDTO>> query(@RequestBody QuerySearchOptionSetDTO querySearchOptionSetDTO, Pageable pageable) {
        log.debug("REST request to get a page of CatItems");
        Page<OptionSetValueDTO> page = optionSetValueService.getListDataOptionSetValue(querySearchOptionSetDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/option_set_value/optionSetCode")
    public ResponseEntity<List<OptionSetValueDTO>> findOptSetValueByOptionSetCode(@RequestParam String code) {
        Optional<List<OptionSetValueDTO>> optionSetValueDTOS = optionSetValueService.findOptSetValueByOptionSetCode(code);
        return ResponseUtil.wrapOrNotFound(optionSetValueDTOS);
    }
}
