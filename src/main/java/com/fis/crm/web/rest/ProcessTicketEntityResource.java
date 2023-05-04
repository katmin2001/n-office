package com.fis.crm.web.rest;

import com.fis.crm.service.ProcessTicketEntityService;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.ProcessTicketEntityDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.ProcessTicketEntity}.
 */
@RestController
@RequestMapping("/api")
public class ProcessTicketEntityResource {

    private final Logger log = LoggerFactory.getLogger(ProcessTicketEntityResource.class);

    private static final String ENTITY_NAME = "processTicketEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessTicketEntityService processTicketEntityService;

    public ProcessTicketEntityResource(ProcessTicketEntityService processTicketEntityService) {
        this.processTicketEntityService = processTicketEntityService;
    }

    /**
     * {@code POST  /process-ticket-entities} : Create a new processTicketEntity.
     *
     * @param processTicketEntityDTO the processTicketEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processTicketEntityDTO, or with status {@code 400 (Bad Request)} if the processTicketEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-ticket-entities")
    public ResponseEntity<ProcessTicketEntityDTO> createProcessTicketEntity(@RequestBody ProcessTicketEntityDTO processTicketEntityDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessTicketEntity : {}", processTicketEntityDTO);
        if (processTicketEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new processTicketEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessTicketEntityDTO result = processTicketEntityService.save(processTicketEntityDTO);
        return ResponseEntity.created(new URI("/api/process-ticket-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-ticket-entities} : Updates an existing processTicketEntity.
     *
     * @param processTicketEntityDTO the processTicketEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processTicketEntityDTO,
     * or with status {@code 400 (Bad Request)} if the processTicketEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processTicketEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-ticket-entities")
    public ResponseEntity<ProcessTicketEntityDTO> updateProcessTicketEntity(@RequestBody ProcessTicketEntityDTO processTicketEntityDTO) throws URISyntaxException {
        log.debug("REST request to update ProcessTicketEntity : {}", processTicketEntityDTO);
        if (processTicketEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessTicketEntityDTO result = processTicketEntityService.save(processTicketEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processTicketEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /process-ticket-entities} : get all the processTicketEntities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processTicketEntities in body.
     */
    @GetMapping("/process-ticket-entities")
    public ResponseEntity<List<ProcessTicketEntityDTO>> getAllProcessTicketEntities(Pageable pageable) {
        log.debug("REST request to get a page of ProcessTicketEntities");
        Page<ProcessTicketEntityDTO> page = processTicketEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /process-ticket-entities/:id} : get the "id" processTicketEntity.
     *
     * @param id the id of the processTicketEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processTicketEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-ticket-entities/{id}")
    public ResponseEntity<ProcessTicketEntityDTO> getProcessTicketEntity(@PathVariable Long id) {
        log.debug("REST request to get ProcessTicketEntity : {}", id);
        Optional<ProcessTicketEntityDTO> processTicketEntityDTO = processTicketEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processTicketEntityDTO);
    }

    /**
     * {@code DELETE  /process-ticket-entities/:id} : delete the "id" processTicketEntity.
     *
     * @param id the id of the processTicketEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-ticket-entities/{id}")
    public ResponseEntity<Void> deleteProcessTicketEntity(@PathVariable Long id) {
        log.debug("REST request to delete ProcessTicketEntity : {}", id);
        processTicketEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
