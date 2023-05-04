package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignSmsBatchService;
import com.fis.crm.service.dto.CampaignSmsBatchDTO;
import com.fis.crm.service.dto.CampaignSmsBatchResDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.CampaignSmsBatch}.
 */
@RestController
@RequestMapping("/api")
public class CampaignSmsBatchResource {

    private final Logger log = LoggerFactory.getLogger(CampaignSmsBatchResource.class);

    private static final String ENTITY_NAME = "campaignSmsBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignSmsBatchService campaignSmsBatchService;

    public CampaignSmsBatchResource(CampaignSmsBatchService campaignSmsBatchService) {
        this.campaignSmsBatchService = campaignSmsBatchService;
    }

    /**
     * {@code POST  /campaign-sms-batches} : Create a new campaignSmsBatch.
     *
     * @param campaignSmsBatchDTO the campaignSmsBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignSmsBatchDTO, or with status {@code 400 (Bad Request)} if the campaignSmsBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-sms-batches")
    public ResponseEntity<CampaignSmsBatchDTO> createCampaignSmsBatch(@RequestBody CampaignSmsBatchDTO campaignSmsBatchDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignSmsBatch : {}", campaignSmsBatchDTO);
        if (campaignSmsBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignSmsBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignSmsBatchDTO result = campaignSmsBatchService.save(campaignSmsBatchDTO);
        return ResponseEntity.created(new URI("/api/campaign-sms-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-sms-batches} : Updates an existing campaignSmsBatch.
     *
     * @param campaignSmsBatchDTO the campaignSmsBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignSmsBatchDTO,
     * or with status {@code 400 (Bad Request)} if the campaignSmsBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignSmsBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-sms-batches")
    public ResponseEntity<CampaignSmsBatchDTO> updateCampaignSmsBatch(@RequestBody CampaignSmsBatchDTO campaignSmsBatchDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignSmsBatch : {}", campaignSmsBatchDTO);
        if (campaignSmsBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignSmsBatchDTO result = campaignSmsBatchService.save(campaignSmsBatchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignSmsBatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-sms-batches} : get all the campaignSmsBatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignSmsBatches in body.
     */
    @GetMapping("/campaign-sms-batches")
    public ResponseEntity<List<CampaignSmsBatchDTO>> getAllCampaignSmsBatches(Pageable pageable) {
        log.debug("REST request to get a page of CampaignSmsBatches");
        Page<CampaignSmsBatchDTO> page = campaignSmsBatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-sms-batches/:id} : get the "id" campaignSmsBatch.
     *
     * @param id the id of the campaignSmsBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignSmsBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-sms-batches/{id}")
    public ResponseEntity<CampaignSmsBatchDTO> getCampaignSmsBatch(@PathVariable Long id) {
        log.debug("REST request to get CampaignSmsBatch : {}", id);
        Optional<CampaignSmsBatchDTO> campaignSmsBatchDTO = campaignSmsBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignSmsBatchDTO);
    }

    /**
     * {@code DELETE  /campaign-sms-batches/:id} : delete the "id" campaignSmsBatch.
     *
     * @param id the id of the campaignSmsBatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-sms-batches/{id}")
    public ResponseEntity<Void> deleteCampaignSmsBatch(@PathVariable Long id) {
        log.debug("REST request to delete CampaignSmsBatch : {}", id);
        campaignSmsBatchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/campaign-sms-batches/create")
    public ResponseEntity<MessageResponse> createCampaignSmsBatch2(@RequestBody CampaignSmsBatchResDTO CampaignSmsBatchResDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignSmsBatch : {}", CampaignSmsBatchResDTO);
        boolean check = true;
        List<CampaignSmsBatchDTO> result = campaignSmsBatchService.save2(CampaignSmsBatchResDTO);
        if (result == null || result.isEmpty()){
            return new ResponseEntity<>(new MessageResponse<>("fail", result), HttpStatus.CREATED);
        }
        for (CampaignSmsBatchDTO campaignSmsBatchDTO: result){
            if (campaignSmsBatchDTO.getCheckList() == 2){
                check = false;
                return new ResponseEntity<>(new MessageResponse<>("fail", result), HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
    }

    @PostMapping("/campaign-sms-batch")
    public ResponseEntity<?> save(@RequestBody List<CampaignSmsBatchDTO> campaignSmsBatchDTOS) {
        log.info("Request to method save in CampaignBlacklist");
        campaignSmsBatchService.saveListSmsBatch(campaignSmsBatchDTOS);
        campaignSmsBatchService.update(campaignSmsBatchDTOS);
        return ResponseEntity.ok().body(null);
    }
}
