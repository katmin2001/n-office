package com.fis.crm.web.rest;

import com.fis.crm.service.ApDomainService;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.ApDomainDTO;

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
 * REST controller for managing {@link com.fis.crm.domain.ApDomain}.
 */
@RestController
@RequestMapping("/api")
public class ApDomainResource {

    private final Logger log = LoggerFactory.getLogger(ApDomainResource.class);

    private static final String ENTITY_NAME = "apDomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApDomainService apDomainService;

    public ApDomainResource(ApDomainService apDomainService) {
        this.apDomainService = apDomainService;
    }

    /**
     * {@code POST  /ap-domains} : Create a new apDomain.
     *
     * @param apDomainDTO the apDomainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apDomainDTO, or with status {@code 400 (Bad Request)} if the apDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ap-domains")
    public ResponseEntity<ApDomainDTO> createApDomain(@RequestBody ApDomainDTO apDomainDTO) throws URISyntaxException {
        log.debug("REST request to save ApDomain : {}", apDomainDTO);
        if (apDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new apDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApDomainDTO result = apDomainService.save(apDomainDTO);
        return ResponseEntity.created(new URI("/api/ap-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ap-domains} : Updates an existing apDomain.
     *
     * @param apDomainDTO the apDomainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apDomainDTO,
     * or with status {@code 400 (Bad Request)} if the apDomainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apDomainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ap-domains")
    public ResponseEntity<ApDomainDTO> updateApDomain(@RequestBody ApDomainDTO apDomainDTO) throws URISyntaxException {
        log.debug("REST request to update ApDomain : {}", apDomainDTO);
        if (apDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApDomainDTO result = apDomainService.save(apDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ap-domains} : get all the apDomains.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apDomains in body.
     */
    @GetMapping("/ap-domains")
    public ResponseEntity<List<ApDomainDTO>> getAllApDomains(Pageable pageable) {
        log.debug("REST request to get a page of ApDomains");
        Page<ApDomainDTO> page = apDomainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ap-domains/:id} : get the "id" apDomain.
     *
     * @param id the id of the apDomainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apDomainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ap-domains/{id}")
    public ResponseEntity<ApDomainDTO> getApDomain(@PathVariable Long id) {
        log.debug("REST request to get ApDomain : {}", id);
        Optional<ApDomainDTO> apDomainDTO = apDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apDomainDTO);
    }

    /**
     * {@code DELETE  /ap-domains/:id} : delete the "id" apDomain.
     *
     * @param id the id of the apDomainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ap-domains/{id}")
    public ResponseEntity<Void> deleteApDomain(@PathVariable Long id) {
        log.debug("REST request to delete ApDomain : {}", id);
        apDomainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/ap-domains/findDomainByType")
    public ResponseEntity<List<ApDomainDTO>> findAllByType(String type) {
        List<ApDomainDTO> results = apDomainService.findAllByType(type);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(type));
        return ResponseEntity.ok().headers(headers).body(results);
    }

}
