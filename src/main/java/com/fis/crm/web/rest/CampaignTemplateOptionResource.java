package com.fis.crm.web.rest;

import com.fis.crm.repository.CampaignTemplateOptionRepository;
import com.fis.crm.service.CampaignTemplateOptionService;
import com.fis.crm.service.dto.CampaignTemplateOptionDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fis.crm.domain.CampaignTemplateOption}.
 */
@RestController
@RequestMapping("/api")
public class CampaignTemplateOptionResource {

  private final Logger log = LoggerFactory.getLogger(CampaignTemplateOptionResource.class);

  private static final String ENTITY_NAME = "campaignTemplateOption";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CampaignTemplateOptionService campaignTemplateOptionService;

  private final CampaignTemplateOptionRepository campaignTemplateOptionRepository;

  public CampaignTemplateOptionResource(
    CampaignTemplateOptionService campaignTemplateOptionService,
    CampaignTemplateOptionRepository campaignTemplateOptionRepository
  ) {
    this.campaignTemplateOptionService = campaignTemplateOptionService;
    this.campaignTemplateOptionRepository = campaignTemplateOptionRepository;
  }

  /**
   * {@code POST  /campaign-template-options} : Create a new campaignTemplateOption.
   *
   * @param campaignTemplateOptionDTO the campaignTemplateOptionDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignTemplateOptionDTO, or with status {@code 400 (Bad Request)} if the campaignTemplateOption has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/campaign-template-options")
  public ResponseEntity<CampaignTemplateOptionDTO> createCampaignTemplateOption(
    @RequestBody CampaignTemplateOptionDTO campaignTemplateOptionDTO
  ) throws URISyntaxException {
    log.debug("REST request to save CampaignTemplateOption : {}", campaignTemplateOptionDTO);
    if (campaignTemplateOptionDTO.getId() != null) {
      throw new BadRequestAlertException("A new campaignTemplateOption cannot already have an ID", ENTITY_NAME, "idexists");
    }
    CampaignTemplateOptionDTO result = campaignTemplateOptionService.save(campaignTemplateOptionDTO);
    return ResponseEntity
      .created(new URI("/api/campaign-template-options/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /campaign-template-options/:id} : Updates an existing campaignTemplateOption.
   *
   * @param id the id of the campaignTemplateOptionDTO to save.
   * @param campaignTemplateOptionDTO the campaignTemplateOptionDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignTemplateOptionDTO,
   * or with status {@code 400 (Bad Request)} if the campaignTemplateOptionDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the campaignTemplateOptionDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/campaign-template-options/{id}")
  public ResponseEntity<CampaignTemplateOptionDTO> updateCampaignTemplateOption(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody CampaignTemplateOptionDTO campaignTemplateOptionDTO
  ) throws URISyntaxException {
    log.debug("REST request to update CampaignTemplateOption : {}, {}", id, campaignTemplateOptionDTO);
    if (campaignTemplateOptionDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, campaignTemplateOptionDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!campaignTemplateOptionRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    CampaignTemplateOptionDTO result = campaignTemplateOptionService.save(campaignTemplateOptionDTO);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignTemplateOptionDTO.getId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /campaign-template-options} : get all the campaignTemplateOptions.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignTemplateOptions in body.
   */
  @GetMapping("/campaign-template-options")
  public ResponseEntity<List<CampaignTemplateOptionDTO>> getAllCampaignTemplateOptions(Pageable pageable) {
    log.debug("REST request to get a page of CampaignTemplateOptions");
    Page<CampaignTemplateOptionDTO> page = campaignTemplateOptionService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /campaign-template-options/:id} : get the "id" campaignTemplateOption.
   *
   * @param id the id of the campaignTemplateOptionDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignTemplateOptionDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/campaign-template-options/{id}")
  public ResponseEntity<CampaignTemplateOptionDTO> getCampaignTemplateOption(@PathVariable Long id) {
    log.debug("REST request to get CampaignTemplateOption : {}", id);
    Optional<CampaignTemplateOptionDTO> campaignTemplateOptionDTO = campaignTemplateOptionService.findOne(id);
    return ResponseUtil.wrapOrNotFound(campaignTemplateOptionDTO);
  }

  /**
   * {@code DELETE  /campaign-template-options/:id} : delete the "id" campaignTemplateOption.
   *
   * @param id the id of the campaignTemplateOptionDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/campaign-template-options/{id}")
  public ResponseEntity<Void> deleteCampaignTemplateOption(@PathVariable Long id) {
    log.debug("REST request to delete CampaignTemplateOption : {}", id);
    campaignTemplateOptionService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
      .build();
  }
}
