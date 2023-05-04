package com.fis.crm.web.rest;

import com.fis.crm.domain.EvaluateAssignmentDetail;
import com.fis.crm.service.EvaluateAssignmentDetailService;
import com.fis.crm.service.dto.EvaluateAssignmentDetailDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link EvaluateAssignmentDetail}.
 */
@RestController
@RequestMapping("/api")
public class EvaluateAssignmentDetailResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateAssignmentDetailResource.class);

    private static final String ENTITY_NAME = "evaluateAssignmentD";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluateAssignmentDetailService evaluateAssignmentDetailService;

    public EvaluateAssignmentDetailResource(EvaluateAssignmentDetailService evaluateAssignmentDetailService) {
        this.evaluateAssignmentDetailService = evaluateAssignmentDetailService;
    }

    /**
     * {@code POST  /evaluate-assignment-ds} : Create a new evaluateAssignmentD.
     *
     * @param evaluateAssignmentDetailDTO the evaluateAssignmentDDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluateAssignmentDDTO, or with status {@code 400 (Bad Request)} if the evaluateAssignmentD has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluate-assignment-ds")
    public ResponseEntity<EvaluateAssignmentDetailDTO> createEvaluateAssignmentD(@RequestBody EvaluateAssignmentDetailDTO evaluateAssignmentDetailDTO) throws URISyntaxException {
        log.debug("REST request to save EvaluateAssignmentD : {}", evaluateAssignmentDetailDTO);
        if (evaluateAssignmentDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluateAssignmentD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluateAssignmentDetailDTO result = evaluateAssignmentDetailService.save(evaluateAssignmentDetailDTO);
        return ResponseEntity.created(new URI("/api/evaluate-assignment-ds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluate-assignment-ds} : Updates an existing evaluateAssignmentD.
     *
     * @param evaluateAssignmentDetailDTO the evaluateAssignmentDDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluateAssignmentDDTO,
     * or with status {@code 400 (Bad Request)} if the evaluateAssignmentDDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluateAssignmentDDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluate-assignment-ds")
    public ResponseEntity<EvaluateAssignmentDetailDTO> updateEvaluateAssignmentD(@RequestBody EvaluateAssignmentDetailDTO evaluateAssignmentDetailDTO) throws URISyntaxException {
        log.debug("REST request to update EvaluateAssignmentD : {}", evaluateAssignmentDetailDTO);
        if (evaluateAssignmentDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluateAssignmentDetailDTO result = evaluateAssignmentDetailService.save(evaluateAssignmentDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluateAssignmentDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evaluate-assignment-ds} : get all the evaluateAssignmentDS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluateAssignmentDS in body.
     */
    @GetMapping("/evaluate-assignment-ds")
    public ResponseEntity<List<EvaluateAssignmentDetailDTO>> getAllEvaluateAssignmentDS(Pageable pageable) {
        log.debug("REST request to get a page of EvaluateAssignmentDS");
        Page<EvaluateAssignmentDetailDTO> page = evaluateAssignmentDetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluate-assignment-ds/:id} : get the "id" evaluateAssignmentD.
     *
     * @param id the id of the evaluateAssignmentDDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluateAssignmentDDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluate-assignment-ds/{id}")
    public ResponseEntity<EvaluateAssignmentDetailDTO> getEvaluateAssignmentD(@PathVariable Long id) {
        log.debug("REST request to get EvaluateAssignmentD : {}", id);
        Optional<EvaluateAssignmentDetailDTO> evaluateAssignmentDDTO = evaluateAssignmentDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluateAssignmentDDTO);
    }

    /**
     * {@code DELETE  /evaluate-assignment-ds/:id} : delete the "id" evaluateAssignmentD.
     *
     * @param id the id of the evaluateAssignmentDDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluate-assignment-ds/{id}")
    public ResponseEntity<Void> deleteEvaluateAssignmentD(@PathVariable Long id) {
        log.debug("REST request to delete EvaluateAssignmentD : {}", id);
        evaluateAssignmentDetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
