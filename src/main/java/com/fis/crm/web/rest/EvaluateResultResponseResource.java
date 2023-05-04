package com.fis.crm.web.rest;

import com.fis.crm.service.EvaluateResultResponseService;
import com.fis.crm.service.dto.EvaluateResultResponseDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.fis.crm.domain.EvaluateResultResponse}.
 */
@RestController
@RequestMapping("/api")
public class EvaluateResultResponseResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultResponseResource.class);

    private static final String ENTITY_NAME = "evaluateResultResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluateResultResponseService evaluateResultResponseService;

    public EvaluateResultResponseResource(EvaluateResultResponseService evaluateResultResponseService) {
        this.evaluateResultResponseService = evaluateResultResponseService;
    }

    /**
     * Luu thong tin phan hoi
     * @param evaluateResultResponseDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/evaluate-result-responses")
    public ResponseEntity<EvaluateResultResponseDTO> createEvaluateResultResponse(@RequestBody EvaluateResultResponseDTO evaluateResultResponseDTO) throws URISyntaxException {
        log.debug("REST request to save EvaluateResultResponse : {}", evaluateResultResponseDTO);
        if (evaluateResultResponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluateResultResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluateResultResponseDTO result = evaluateResultResponseService.save(evaluateResultResponseDTO);
        return ResponseEntity.created(new URI("/api/evaluate-result-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluate-result-responses} : Updates an existing evaluateResultResponse.
     *
     * @param evaluateResultResponseDTO the evaluateResultResponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluateResultResponseDTO,
     * or with status {@code 400 (Bad Request)} if the evaluateResultResponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluateResultResponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PutMapping("/evaluate-result-responses")
//    public ResponseEntity<EvaluateResultResponseDTO> updateEvaluateResultResponse(@RequestBody EvaluateResultResponseDTO evaluateResultResponseDTO) throws URISyntaxException {
//        log.debug("REST request to update EvaluateResultResponse : {}", evaluateResultResponseDTO);
//        if (evaluateResultResponseDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        EvaluateResultResponseDTO result = evaluateResultResponseService.save(evaluateResultResponseDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluateResultResponseDTO.getId().toString()))
//            .body(result);
//    }

    /**
     * Tim kiem
     * @param pageable
     * @return
     */
//    @GetMapping("/evaluate-result-responses")
//    public ResponseEntity<List<EvaluateResultResponseDTO>> getAllEvaluateResultResponses(Pageable pageable) {
//        log.debug("REST request to get a page of EvaluateResultResponses");
//        Page<EvaluateResultResponseDTO> page = evaluateResultResponseService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

    /**
     * {@code GET  /evaluate-result-responses/:id} : get the "id" evaluateResultResponse.
     *
     * @param id the id of the evaluateResultResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluateResultResponseDTO, or with status {@code 404 (Not Found)}.
     */
//    @GetMapping("/evaluate-result-responses/{id}")
//    public ResponseEntity<EvaluateResultResponseDTO> getEvaluateResultResponse(@PathVariable Long id) {
//        log.debug("REST request to get EvaluateResultResponse : {}", id);
//        Optional<EvaluateResultResponseDTO> evaluateResultResponseDTO = evaluateResultResponseService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(evaluateResultResponseDTO);
//    }

    /**
     * {@code DELETE  /evaluate-result-responses/:id} : delete the "id" evaluateResultResponse.
     *
     * @param id the id of the evaluateResultResponseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
//    @DeleteMapping("/evaluate-result-responses/{id}")
//    public ResponseEntity<Void> deleteEvaluateResultResponse(@PathVariable Long id) {
//        log.debug("REST request to delete EvaluateResultResponse : {}", id);
//        evaluateResultResponseService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//    }


    @PostMapping("/evaluate-result-responses/{evaluateResultId}")
    public ResponseEntity<List<EvaluateResultResponseDTO>> getEvaluateResultResponseByEvaluateResult(@PathVariable Long evaluateResultId, Pageable pageable) {
        Page<EvaluateResultResponseDTO> page = evaluateResultResponseService.getEvaluateResultResponseByEvaluateResult(evaluateResultId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
