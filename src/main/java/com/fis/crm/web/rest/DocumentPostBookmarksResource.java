package com.fis.crm.web.rest;

import com.fis.crm.service.DocumentPostBookmarksService;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.DocumentPostBookmarksDTO;

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
 * REST controller for managing {@link com.fis.crm.domain.DocumentPostBookmarks}.
 */
@RestController
@RequestMapping("/api")
public class DocumentPostBookmarksResource {

    private final Logger log = LoggerFactory.getLogger(DocumentPostBookmarksResource.class);

    private static final String ENTITY_NAME = "documentPostBookmarks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentPostBookmarksService documentPostBookmarksService;

    public DocumentPostBookmarksResource(DocumentPostBookmarksService documentPostBookmarksService) {
        this.documentPostBookmarksService = documentPostBookmarksService;
    }

    /**
     * {@code POST  /document-post-bookmarks} : Create a new documentPostBookmarks.
     *
     * @param documentPostBookmarksDTO the documentPostBookmarksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentPostBookmarksDTO, or with status {@code 400 (Bad Request)} if the documentPostBookmarks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-post-bookmarks")
    public ResponseEntity<DocumentPostBookmarksDTO> createDocumentPostBookmarks(@RequestBody DocumentPostBookmarksDTO documentPostBookmarksDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentPostBookmarks : {}", documentPostBookmarksDTO);
        if (documentPostBookmarksDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentPostBookmarks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentPostBookmarksDTO result = documentPostBookmarksService.save(documentPostBookmarksDTO);
        return ResponseEntity.created(new URI("/api/document-post-bookmarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-post-bookmarks} : Updates an existing documentPostBookmarks.
     *
     * @param documentPostBookmarksDTO the documentPostBookmarksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentPostBookmarksDTO,
     * or with status {@code 400 (Bad Request)} if the documentPostBookmarksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentPostBookmarksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-post-bookmarks")
    public ResponseEntity<DocumentPostBookmarksDTO> updateDocumentPostBookmarks(@RequestBody DocumentPostBookmarksDTO documentPostBookmarksDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentPostBookmarks : {}", documentPostBookmarksDTO);
        if (documentPostBookmarksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentPostBookmarksDTO result = documentPostBookmarksService.save(documentPostBookmarksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentPostBookmarksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-post-bookmarks} : get all the documentPostBookmarks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentPostBookmarks in body.
     */
    @GetMapping("/document-post-bookmarks")
    public ResponseEntity<List<DocumentPostBookmarksDTO>> getAllDocumentPostBookmarks(Pageable pageable) {
        log.debug("REST request to get a page of DocumentPostBookmarks");
        Page<DocumentPostBookmarksDTO> page = documentPostBookmarksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-post-bookmarks/:id} : get the "id" documentPostBookmarks.
     *
     * @param id the id of the documentPostBookmarksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentPostBookmarksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-post-bookmarks/{id}")
    public ResponseEntity<DocumentPostBookmarksDTO> getDocumentPostBookmarks(@PathVariable Long id) {
        log.debug("REST request to get DocumentPostBookmarks : {}", id);
        Optional<DocumentPostBookmarksDTO> documentPostBookmarksDTO = documentPostBookmarksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentPostBookmarksDTO);
    }

    /**
     * {@code DELETE  /document-post-bookmarks/:id} : delete the "id" documentPostBookmarks.
     *
     * @param id the id of the documentPostBookmarksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-post-bookmarks/{id}")
    public ResponseEntity<Void> deleteDocumentPostBookmarks(@PathVariable Long id) {
        log.debug("REST request to delete DocumentPostBookmarks : {}", id);
        documentPostBookmarksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
