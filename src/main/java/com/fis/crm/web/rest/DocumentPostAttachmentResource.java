package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.service.DocumentPostAttachmentService;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.DocumentPostAttachmentDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.DocumentPostAttachment}.
 */
@RestController
@RequestMapping("/api")
public class DocumentPostAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(DocumentPostAttachmentResource.class);

    private static final String ENTITY_NAME = "documentPostAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Value("${file-manager.attach-file}")
    private String attachPath;

    private final DocumentPostAttachmentService documentPostAttachmentService;

    public DocumentPostAttachmentResource(DocumentPostAttachmentService documentPostAttachmentService) {
        this.documentPostAttachmentService = documentPostAttachmentService;
    }

    /**
     * {@code POST  /document-post-attachments} : Create a new documentPostAttachment.
     *
     * @param documentPostAttachmentDTO the documentPostAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentPostAttachmentDTO, or with status {@code 400 (Bad Request)} if the documentPostAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-post-attachments")
    public ResponseEntity<DocumentPostAttachmentDTO> createDocumentPostAttachment(@RequestBody DocumentPostAttachmentDTO documentPostAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentPostAttachment : {}", documentPostAttachmentDTO);
        if (documentPostAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentPostAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentPostAttachmentDTO result = documentPostAttachmentService.save(documentPostAttachmentDTO);
        return ResponseEntity.created(new URI("/api/document-post-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-post-attachments} : Updates an existing documentPostAttachment.
     *
     * @param documentPostAttachmentDTO the documentPostAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentPostAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the documentPostAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentPostAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-post-attachments")
    public ResponseEntity<DocumentPostAttachmentDTO> updateDocumentPostAttachment(@RequestBody DocumentPostAttachmentDTO documentPostAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentPostAttachment : {}", documentPostAttachmentDTO);
        if (documentPostAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentPostAttachmentDTO result = documentPostAttachmentService.save(documentPostAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentPostAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-post-attachments} : get all the documentPostAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentPostAttachments in body.
     */
    @GetMapping("/document-post-attachments")
    public ResponseEntity<List<DocumentPostAttachmentDTO>> getAllDocumentPostAttachments(Pageable pageable) {
        log.debug("REST request to get a page of DocumentPostAttachments");
        Page<DocumentPostAttachmentDTO> page = documentPostAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-post-attachments/:id} : get the "id" documentPostAttachment.
     *
     * @param id the id of the documentPostAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentPostAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-post-attachments/{id}")
    public ResponseEntity<DocumentPostAttachmentDTO> getDocumentPostAttachment(@PathVariable Long id) {
        log.debug("REST request to get DocumentPostAttachment : {}", id);
        Optional<DocumentPostAttachmentDTO> documentPostAttachmentDTO = documentPostAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentPostAttachmentDTO);
    }

    @GetMapping("/document-post-attachments/find-by-post/{postId}")
    public ResponseEntity<List<DocumentPostAttachmentDTO>> getDocumentPostAttachmentByPost(@PathVariable Long postId) {
        log.debug("REST request to get DocumentPostAttachment : {}", postId);
        List<DocumentPostAttachmentDTO> lstDocAttach = documentPostAttachmentService.findAllByDocumentPostId(postId);
        return ResponseEntity.ok().body(lstDocAttach);
    }
    @GetMapping("/document-post-attachments/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception{
        Optional<DocumentPostAttachmentDTO> fileAttach = documentPostAttachmentService.findOne(id);
        if(!fileAttach.isPresent()) {
            throw new BadRequestAlertException(Translator.toLocale("documentPost.fileNotExisted"), "documentPost", "documentPost.fileNotExisted");
        }
        File fileOut = Paths.get(attachPath + File.separator + fileAttach.get().getFileNameEncrypt()).toAbsolutePath().toFile();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileOut));
        DocumentPostAttachmentDTO fileAttachDTO = fileAttach.get();
        fileAttachDTO.setDownloadTotal(fileAttachDTO.getDownloadTotal()+1);
        documentPostAttachmentService.save(fileAttachDTO);

        return ResponseEntity.ok()
            .contentLength(fileOut.length())
            .header("filename", fileOut.getName())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

    /**
     * {@code DELETE  /document-post-attachments/:id} : delete the "id" documentPostAttachment.
     *
     * @param id the id of the documentPostAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-post-attachments/{id}")
    public ResponseEntity<Void> deleteDocumentPostAttachment(@PathVariable Long id) {
        log.debug("REST request to delete DocumentPostAttachment : {}", id);
        Optional<DocumentPostAttachmentDTO> fileAttach = documentPostAttachmentService.findOne(id);
        if(!fileAttach.isPresent()) {
            throw new BadRequestAlertException(Translator.toLocale("documentPost.fileNotExisted"), "documentPost", "documentPost.fileNotExisted");
        }

        File fileOut = Paths.get(attachPath + File.separator + fileAttach.get().getFileNameEncrypt()).toFile();
        if(fileOut.exists()) {
            fileOut.delete();
        }
        documentPostAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
