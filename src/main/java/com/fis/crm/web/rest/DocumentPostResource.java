package com.fis.crm.web.rest;

import com.fis.crm.domain.DocumentPostNotApprove;
import com.fis.crm.service.DocumentPostService;
import com.fis.crm.service.dto.DocumentPostApprove;
import com.fis.crm.service.dto.DocumentPostDetail;
import com.fis.crm.service.dto.DocumentPostForm;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.DocumentPostDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.DocumentPost}.
 */
@RestController
@RequestMapping("/api")
public class DocumentPostResource {

    private final Logger log = LoggerFactory.getLogger(DocumentPostResource.class);

    private static final String ENTITY_NAME = "documentPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentPostService documentPostService;

    public DocumentPostResource(DocumentPostService documentPostService) {
        this.documentPostService = documentPostService;
    }

    /**
     * {@code POST  /document-posts} : Create a new documentPost.
     *
     * @param documentPostDTO the documentPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentPostDTO, or with status {@code 400 (Bad Request)} if the documentPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(value = "/document-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentPostDTO> createDocumentPost(@Valid @ModelAttribute DocumentPostDTO documentPostDTO,
                                                              @ModelAttribute List<MultipartFile> documentAttachs) throws Exception {
        log.debug("REST request to save DocumentPost : {}", documentPostDTO);
        if (documentPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentPostDTO result = documentPostService.save(documentPostDTO, documentAttachs);
        return ResponseEntity.created(new URI("/api/document-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-posts} : Updates an existing documentPost.
     *
     * @param documentPostDTO the documentPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentPostDTO,
     * or with status {@code 400 (Bad Request)} if the documentPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/document-posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentPostDTO> updateDocumentPost(@Valid @ModelAttribute DocumentPostDTO documentPostDTO,
                                                              @ModelAttribute List<MultipartFile> documentAttachs) throws Exception {
        log.debug("REST request to update DocumentPost : {}", documentPostDTO);
        if (documentPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentPostDTO result = documentPostService.save(documentPostDTO, documentAttachs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-posts} : get all the documentPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentPosts in body.
     */
    @GetMapping("/document-posts")
    public ResponseEntity<List<DocumentPostDTO>> getAllDocumentPosts(String title, Pageable pageable) {
        log.debug("REST request to get a page of DocumentPosts");
        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDatetime").descending());
        }
        Page<DocumentPostDTO> page = documentPostService.findAll(title, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/document-posts/top-view")
    public ResponseEntity<List<DocumentPostDTO>> getTopView(Pageable pageable) {
        log.debug("REST request to get a page of DocumentPosts");
        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("viewTotal")));
        }
        Page<DocumentPostDTO> page = documentPostService.getTopView(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/document-posts/get-bookmarks")
    public ResponseEntity<List<DocumentPostDTO>> getBookmark(Pageable pageable) {
        log.debug("REST request to get-bookmarks a page of DocumentPosts");
        Page<DocumentPostDTO> page = documentPostService.getBookmark(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/document-posts/get-new-post")
    public ResponseEntity<List<DocumentPostDTO>> getNewPost(Pageable pageable) {
        log.debug("REST request to get-bookmarks a page of DocumentPosts");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createDatetime")));

        Page<DocumentPostDTO> page = documentPostService.getNewPost(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-posts/:id} : get the "id" documentPost.
     *
     * @param id the id of the documentPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-posts/{id}")
    public ResponseEntity<DocumentPostDetail> getDocumentPost(@PathVariable Long id) {
        log.debug("REST request to get DocumentPost : {}", id);
        Optional<DocumentPostDetail> documentPostDTO = documentPostService.getDocumentPostById(id, false);
        return ResponseUtil.wrapOrNotFound(documentPostDTO);
    }
    @GetMapping("/document-posts/view-document/{id}")
    public ResponseEntity<DocumentPostDetail> viewDocumentPost(@PathVariable Long id) {
        log.debug("REST request to get DocumentPost : {}", id);
        Optional<DocumentPostDetail> documentPostDTO = documentPostService.getDocumentPostById(id, true);
        return ResponseUtil.wrapOrNotFound(documentPostDTO);
    }

    /**
     * {@code DELETE  /document-posts/:id} : delete the "id" documentPost.
     *
     * @param id the id of the documentPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-posts/{id}")
    public ResponseEntity<Void> deleteDocumentPost(@PathVariable Long id) {
        log.debug("REST request to delete DocumentPost : {}", id);
        documentPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/document-posts/move-document")
    public ResponseEntity<Void> moveDocument(Long srcDocId, Long desDocId) {
        log.debug("REST request to get a page of Documents");
        documentPostService.moveDocument(srcDocId, desDocId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, srcDocId.toString())).build();
    }

    @GetMapping("/document-posts/bookmark-document/{id}")
    public ResponseEntity<Void> bookmarkDocument(@PathVariable Long id) {
        log.debug("REST request to get a page of Documents");
        documentPostService.bookmarkDocument(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/document-posts/remove-bookmark/{id}")
    public ResponseEntity<Void> removeBookmark(@PathVariable Long id) {
        log.debug("REST request to get a page of Documents");
        documentPostService.removeBookmark(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/document-posts/approve")
    public ResponseEntity<Void> approveDocument(@RequestBody DocumentPostApprove documentPostApprove) {
        log.debug("REST request to get a page of Documents");
        documentPostService.approveDocument(documentPostApprove);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, "")).build();
    }

    @GetMapping("/document-posts-not-approve")
    public ResponseEntity<List<DocumentPostNotApprove>> getAllDocumentPostsNotApprove(Pageable pageable) {
        log.debug("REST request to get a page of DocumentPosts");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createDatetime")));
        Page<DocumentPostNotApprove> page = documentPostService.findAllNotApprove(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/document-posts/get-file/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(documentPostService.getFile(id));
    }

}
