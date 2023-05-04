package com.fis.crm.web.rest;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.repository.CampaignTemplateRepository;
import com.fis.crm.service.CampaignTemplateService;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import com.fis.crm.service.dto.CampaignTemplateListDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.CampaignTemplate}.
 */
@RestController
@RequestMapping("/api")
public class CampaignTemplateResource {

    private final Logger log = LoggerFactory.getLogger(CampaignTemplateResource.class);

    private static final String ENTITY_NAME = "campaignTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignTemplateService campaignTemplateService;

    private final CampaignTemplateRepository campaignTemplateRepository;

    public CampaignTemplateResource(CampaignTemplateService campaignTemplateService, CampaignTemplateRepository campaignTemplateRepository) {
        this.campaignTemplateService = campaignTemplateService;
        this.campaignTemplateRepository = campaignTemplateRepository;
    }

    /**
     * {@code POST  /campaign-templates} : Create a new campaignTemplate.
     *
     * @param campaignTemplateDTO the campaignTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignTemplateDTO, or with status {@code 400 (Bad Request)} if the campaignTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-templates")
    public ResponseEntity<CampaignTemplateDTO> createCampaignTemplate(@RequestBody CampaignTemplateDTO campaignTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save CampaignTemplate : {}", campaignTemplateDTO);
        if (campaignTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignTemplateDTO result = campaignTemplateService.save(campaignTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/campaign-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-templates/:id} : Updates an existing campaignTemplate.
     *
     * @param id                  the id of the campaignTemplateDTO to save.
     * @param campaignTemplateDTO the campaignTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the campaignTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignTemplateDTO couldn't be updated.
     */
    @PutMapping("/campaign-templates/{id}")
    public ResponseEntity<CampaignTemplateDTO> updateCampaignTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CampaignTemplateDTO campaignTemplateDTO
    ) {
        log.debug("REST request to update CampaignTemplate : {}, {}", id, campaignTemplateDTO);
        if (campaignTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, campaignTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!campaignTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (!DataUtil.isNullOrEmpty(campaignTemplateService.checkExistedTemplate(campaignTemplateDTO.getCampaignName(), campaignTemplateDTO.getId()))) {
            throw new BadRequestAlertException(null, ENTITY_NAME, "existed");
        }
        CampaignTemplateDTO result = campaignTemplateService.update(campaignTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-templates} : get all the campaignTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignTemplates in body.
     */
    @GetMapping("/campaign-templates")
    public ResponseEntity<List<CampaignTemplateDTO>> getAllCampaignTemplates() {
        log.debug("REST request to get a page of CampaignTemplates");
        List<CampaignTemplateDTO> page = campaignTemplateService.findAll();
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /campaign-templates/:id} : get the "id" campaignTemplate.
     *
     * @param id the id of the campaignTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-templates/{id}")
    public ResponseEntity<CampaignTemplateDTO> getCampaignTemplate(@PathVariable Long id) {
        log.debug("REST request to get CampaignTemplate : {}", id);
        Optional<CampaignTemplateDTO> campaignTemplateDTO = campaignTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignTemplateDTO);
    }

    /**
     * {@code DELETE  /campaign-templates/:id} : delete the "id" campaignTemplate.
     *
     * @param id the id of the campaignTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PutMapping("/campaign-templates/delete/{id}")
    public ResponseEntity<Void> deleteCampaignTemplate(@PathVariable Long id) {
        log.debug("REST request to delete CampaignTemplate : {}", id);
        campaignTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/campaign-templates/get-info-code")
    public ResponseEntity<List<OptionSetValueDTO>> getAllInfoCode() {
        return ResponseEntity.ok(campaignTemplateService.getAllInfoCode());
    }

    @PostMapping("/campaign-templates/copy")
    public ResponseEntity<CampaignTemplateDTO> copyTemplate(@RequestBody CampaignTemplateDTO campaignTemplateDTO)
        throws URISyntaxException {
        if (!DataUtil.isNullOrEmpty(campaignTemplateService.checkExistedTemplate(campaignTemplateDTO.getCampaignName(), null))) {
            throw new BadRequestAlertException(null, ENTITY_NAME, "existed");
        }
        CampaignTemplateDTO result = campaignTemplateService.copy(campaignTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/campaign-templates/copy" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/campaign-templates/getListCampaignTemplateForCombobox")
    public ResponseEntity<List<CampaignTemplateListDTO>> getListDepartmentsForCombobox() {
        log.debug("REST request to get list of CampaignTemplate for combobox");
        List<CampaignTemplateListDTO> page = campaignTemplateService.getListCampaignTemplateForCombobox();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
}
