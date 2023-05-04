package com.fis.crm.web.rest;

import com.fis.crm.domain.CampaignTemplateDetail;
import com.fis.crm.repository.CampaignTemplateDetailRepository;
import com.fis.crm.service.CampaignTemplateDetailService;
import com.fis.crm.service.dto.CampaignTemplateDetailDTO;
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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link CampaignTemplateDetail}.
 */
@RestController
@RequestMapping("/api")
public class CampaignTemplateDetailResource {

    private final Logger log = LoggerFactory.getLogger(CampaignTemplateDetailResource.class);

    private static final String ENTITY_NAME = "campaignTemplateDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignTemplateDetailService campaignTemplateDetailService;

    private final CampaignTemplateDetailRepository campaignTemplateDetailRepository;

    public CampaignTemplateDetailResource(
        CampaignTemplateDetailService campaignTemplateDetailService,
        CampaignTemplateDetailRepository campaignTemplateDetailRepository
    ) {
        this.campaignTemplateDetailService = campaignTemplateDetailService;
        this.campaignTemplateDetailRepository = campaignTemplateDetailRepository;
    }

    /**
     * {@code POST  /campaign-template-detail} : Create a new campaignTemplateDetail.
     *
     * @param campaignTemplateDetailDTO the campaignTemplateDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignTemplateDetailDTO, or with status {@code 400 (Bad Request)} if the campaignTemplateDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-template-detail")
    public ResponseEntity<CampaignTemplateDetailDTO> createCampaignTemplateDetail(
        @RequestBody CampaignTemplateDetailDTO campaignTemplateDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CampaignTemplateDetail : {}", campaignTemplateDetailDTO);
        if (campaignTemplateDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignTemplateDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignTemplateDetailDTO result = campaignTemplateDetailService.save(campaignTemplateDetailDTO);
        return ResponseEntity
            .created(new URI("/api/campaign-template-detail/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-template-detail/:id} : Updates an existing campaignTemplateDetail.
     *
     * @param id                        the id of the campaignTemplateDetailDTO to save.
     * @param campaignTemplateDetailDTO the campaignTemplateDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignTemplateDetailDTO,
     * or with status {@code 400 (Bad Request)} if the campaignTemplateDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignTemplateDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-template-detail/{id}")
    public ResponseEntity<CampaignTemplateDetailDTO> updateCampaignTemplateDetail(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CampaignTemplateDetailDTO campaignTemplateDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CampaignTemplateDetail : {}, {}", id, campaignTemplateDetailDTO);
        if (campaignTemplateDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campaignTemplateDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campaignTemplateDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CampaignTemplateDetailDTO result = campaignTemplateDetailService.save(campaignTemplateDetailDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignTemplateDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-template-detail} : get all the campaignTemplateDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignTemplateDetails in body.
     */
    @GetMapping("/campaign-template-detail")
    public ResponseEntity<List<CampaignTemplateDetailDTO>> getAllCampaignTemplateDetails(Pageable pageable) {
        log.debug("REST request to get a page of CampaignTemplateDetails");
        Page<CampaignTemplateDetailDTO> page = campaignTemplateDetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-template-detail/:id} : get the "id" campaignTemplateDetail.
     *
     * @param id the id of the campaignTemplateDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignTemplateDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-template-detail/{id}")
    public ResponseEntity<CampaignTemplateDetailDTO> getCampaignTemplateDetail(@PathVariable Long id) {
        log.debug("REST request to get CampaignTemplateDetail : {}", id);
        Optional<CampaignTemplateDetailDTO> campaignTemplateDetailDTO = campaignTemplateDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignTemplateDetailDTO);
    }

    /**
     * {@code DELETE  /campaign-template-detail/:id} : delete the "id" campaignTemplateDetail.
     *
     * @param id the id of the campaignTemplateDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-template-detail/{id}")
    public ResponseEntity<Void> deleteCampaignTemplateDetail(@PathVariable Long id) {
        log.debug("REST request to delete CampaignTemplateDetail : {}", id);
        campaignTemplateDetailService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/campaign-template-detail/find-by-campaign-id/{id}")
    public ResponseEntity<?> findByCampaignId(@PathVariable  Long id) {
        return ResponseEntity.ok(campaignTemplateDetailService.findAllByCampaignId(id));
    }
}
