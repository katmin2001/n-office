package com.fis.crm.web.rest;

import com.fis.crm.service.OptionSetService;
import com.fis.crm.service.dto.OptionSetDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.service.dto.RequestSearchTicketDTO;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.service.response.ConfigScheduleError;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.OptionSet}.
 */
@RestController
@RequestMapping("/api")
public class OptionSetResource {

    private final Logger log = LoggerFactory.getLogger(OptionSetResource.class);

    private static final String ENTITY_NAME = "optionSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionSetService optionSetService;

    public OptionSetResource(OptionSetService optionSetService) {
        this.optionSetService = optionSetService;
    }

    /**
     * {@code POST  /config-schedules} : Create a new configSchedule.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configScheduleDTO, or with status {@code 400 (Bad Request)} if the configSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option_set")
    public ResponseEntity<Object> createOptionSet(@RequestBody OptionSetDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigSchedule : {}", optionSetDTO);
        try {
            if (optionSetDTO.getOptionSetId() != null) {
                throw new BadRequestAlertException("A new OptionSet cannot already have an ID", ENTITY_NAME, "idexists");
            }

            OptionSetDTO result = optionSetService.save(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set/" + result.getOptionSetId()))
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
    @PutMapping("/option_set")
    public ResponseEntity<Object> updateOptionSetDTO(@RequestBody OptionSetDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to update optionSet : {}", optionSetDTO);
        try {
            if (optionSetDTO.getOptionSetId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }

            OptionSetDTO result = optionSetService.save(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set/" + result.getOptionSetId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOptionSetId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configSchedules in body.
     */
    @GetMapping("/option_set")
    public ResponseEntity<List<OptionSetDTO>> getAllOptionSetDTO(Pageable pageable) {
        log.debug("REST request to get a page of ConfigSchedules");
        Page<OptionSetDTO> page = optionSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option_set/{id}")
    public ResponseEntity<OptionSetDTO> getOptionSetDTO(@PathVariable Long id) {
        Optional<OptionSetDTO> optionSetDTO = optionSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionSetDTO);
    }

    /**
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option_set/{id}")
    public ResponseEntity<Object> deleteOptionSetDTO(@PathVariable Long id) {
        log.debug("REST request to delete OptionSet : {}", id);
        OptionSetDTO result = optionSetService.delete(id);
        if (StringUtils.isNotEmpty(result.getErrorCodeConfig())) {
            String message = result.getErrorCodeConfig();
            ConfigScheduleError error = new ConfigScheduleError("400", message);
            return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/option_set/search")
    public ResponseEntity<List<OptionSetDTO>> searchOptionSet(@RequestBody OptionSetDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to searchConfigSchedule : {}", optionSetDTO);
        List<OptionSetDTO> page = optionSetService.find(optionSetDTO);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PutMapping("/option_set/change_status")
    public ResponseEntity<Object> changeStatus(@RequestBody OptionSetDTO optionSetDTO) throws URISyntaxException {
        log.debug("REST request to changeStatus : {}", optionSetDTO);
        try {
            if (optionSetDTO.getOptionSetId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }

            OptionSetDTO result = optionSetService.changeStatus(optionSetDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/option_set/" + result.getOptionSetId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getOptionSetId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/option_set/searchOptSetValue")
    public ResponseEntity<List<OptionSetValueDTO>> searchOptSetValue(@RequestBody OptionSetDTO optionSetDTO) throws URISyntaxException {
        List<OptionSetValueDTO> page = optionSetService.findOptSetValueByOptionSetId(optionSetDTO);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping("/option_set/upload_file")
    public ResponseEntity<Object> getSampleFile(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to down file sample ConfigSchedule : {}");
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            //String now = new Date().getTime()+"";
            File myFile = new File("Report_Error_OptionSet.xlsx");
            FileOutputStream out = new FileOutputStream(myFile);
            OptionSetValueDTO result = optionSetService.importList(file);
            if (!StringUtils.isEmpty(result.getErrorCodeConfig())) {
                    Workbook workbook = result.getXssfWorkbook();
                    if (null != workbook) {
                        workbook.write(out);
                        out.close();
                        FileInputStream fis = new FileInputStream(new File("Report_Error_OptionSet.xlsx"));
                        InputStreamResource inputStreamResource = new InputStreamResource(fis);
                        responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                        return ResponseEntity.ok()
                            .contentLength(myFile.length())
                            .header("filename", myFile.getName())
                            .contentType(MediaType.parseMediaType("application/octet-stream"))
                            .body(inputStreamResource);
                    }


            }
//            ConfigScheduleError success = new ConfigScheduleError("200", "Import file cập nhật cấu hình thành công");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/option_set/query")
    public ResponseEntity<List<OptionSetDTO>> doSearch(@RequestBody OptionSetDTO optionSetDTO, Pageable pageable) {
        Page<OptionSetDTO> page = optionSetService.searchOptionSet(optionSetDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
