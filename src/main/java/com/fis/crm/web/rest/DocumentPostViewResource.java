package com.fis.crm.web.rest;

import com.fis.crm.service.DocumentPostViewService;
import com.fis.crm.service.dto.DocumentPostViewDetail;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.DocumentPostViewDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * REST controller for managing {@link com.fis.crm.domain.DocumentPostView}.
 */
@RestController
@RequestMapping("/api")
public class DocumentPostViewResource {

    private final Logger log = LoggerFactory.getLogger(DocumentPostViewResource.class);

    private static final String ENTITY_NAME = "documentPostView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentPostViewService documentPostViewService;

    public DocumentPostViewResource(DocumentPostViewService documentPostViewService) {
        this.documentPostViewService = documentPostViewService;
    }

    /**
     * {@code POST  /document-post-views} : Create a new documentPostView.
     *
     * @param documentPostViewDTO the documentPostViewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentPostViewDTO, or with status {@code 400 (Bad Request)} if the documentPostView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-post-views")
    public ResponseEntity<DocumentPostViewDTO> createDocumentPostView(@RequestBody DocumentPostViewDTO documentPostViewDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentPostView : {}", documentPostViewDTO);
        if (documentPostViewDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentPostView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentPostViewDTO result = documentPostViewService.save(documentPostViewDTO);
        return ResponseEntity.created(new URI("/api/document-post-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-post-views} : Updates an existing documentPostView.
     *
     * @param documentPostViewDTO the documentPostViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentPostViewDTO,
     * or with status {@code 400 (Bad Request)} if the documentPostViewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentPostViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-post-views")
    public ResponseEntity<DocumentPostViewDTO> updateDocumentPostView(@RequestBody DocumentPostViewDTO documentPostViewDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentPostView : {}", documentPostViewDTO);
        if (documentPostViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentPostViewDTO result = documentPostViewService.save(documentPostViewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentPostViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-post-views} : get all the documentPostViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentPostViews in body.
     */
    @GetMapping("/document-post-views/search/{postId}")
    public ResponseEntity<List<DocumentPostViewDetail>> getAllDocumentPostViews(@PathVariable Long postId, Pageable pageable) {
        log.debug("REST request to get a page of DocumentPostViews");
        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDatetime").descending());
        }
        Page<DocumentPostViewDetail> page = documentPostViewService.findAllByPostId(postId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-post-views/:id} : get the "id" documentPostView.
     *
     * @param id the id of the documentPostViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentPostViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-post-views/{id}")
    public ResponseEntity<DocumentPostViewDTO> getDocumentPostView(@PathVariable Long id) {
        log.debug("REST request to get DocumentPostView : {}", id);
        Optional<DocumentPostViewDTO> documentPostViewDTO = documentPostViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentPostViewDTO);
    }

    /**
     * {@code DELETE  /document-post-views/:id} : delete the "id" documentPostView.
     *
     * @param id the id of the documentPostViewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-post-views/{id}")
    public ResponseEntity<Void> deleteDocumentPostView(@PathVariable Long id) {
        log.debug("REST request to delete DocumentPostView : {}", id);
        documentPostViewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
