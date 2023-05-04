package com.fis.crm.web.rest;

import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignScriptService;
import com.fis.crm.service.CampaignService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CampaignDTO;
import com.fis.crm.service.dto.CampaignScriptDTO;
import com.fis.crm.service.response.ConfigScheduleError;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.Campaign}.
 */
@RestController
@RequestMapping("/api")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private static final String ENTITY_NAME = "campaign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignService campaignService;

    private final CampaignScriptService campaignScriptService;

    private final UserService userService;

    private final ActionLogService actionLogService;

    public CampaignResource(CampaignService campaignService, CampaignScriptService campaignScriptService, UserService userService, ActionLogService actionLogService) {
        this.campaignService = campaignService;
        this.campaignScriptService = campaignScriptService;
        this.userService = userService;
        this.actionLogService = actionLogService;
    }

    /**
     * {@code POST  /campaigns} : Create a new campaign.
     *
     * @param campaignDTO the campaignDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignDTO, or with status {@code 400 (Bad Request)} if the campaign has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaigns")
    public ResponseEntity<Object> createCampaign(@RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to save Campaign : {}", campaignDTO);
        try {
            if (campaignDTO.getId() != null) {
                throw new BadRequestAlertException("A new campaign cannot already have an ID", ENTITY_NAME, "idexists");
            }
            Optional<User> user = userService.getUserWithAuthorities();
            String group = "";
            if (campaignDTO.getLstGroupUser() != null && campaignDTO.getLstGroupUser().size() != 0){
                for (int i = 0; i < campaignDTO.getLstGroupUser().size(); i++){
                    if (i == campaignDTO.getLstGroupUser().size() - 1){
                        group = group + campaignDTO.getLstGroupUser().get(i);
                    } else{
                        group = group + campaignDTO.getLstGroupUser().get(i) + ",";
                    }
                }
            }
            Date date = new Date();
            campaignDTO.setCreateDatetime(date);
            campaignDTO.setUpdateDatetime(date);
            campaignDTO.setGroupUser(group);
            campaignDTO.setCreateUser(user.get().getId());
            CampaignDTO result = campaignService.save(campaignDTO);
            //Cap nhap kich ban hien dich sang trang thai da duoc su dung
            Optional<CampaignScriptDTO> campaignScript = campaignScriptService.findOne(campaignDTO.getCampaignScriptId());
            campaignScript.get().setIsUsed("1");
            campaignScriptService.save(campaignScript.get());
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/campaigns/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(success);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof BadRequestAlertException) {
                ConfigScheduleError error = new ConfigScheduleError("400", ((BadRequestAlertException) e).getTitle());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * {@code PUT  /campaigns} : Updates an existing campaign.
     *
     * @param campaignDTO the campaignDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignDTO,
     * or with status {@code 400 (Bad Request)} if the campaignDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaigns")
    public ResponseEntity<Object> updateCampaign(@RequestBody CampaignDTO campaignDTO) throws URISyntaxException {
        log.debug("REST request to update Campaign : {}", campaignDTO);
        try {
            if (campaignDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            Optional<User> user = userService.getUserWithAuthorities();
            String group = "";
            if (campaignDTO.getLstGroupUser() != null && campaignDTO.getLstGroupUser().size() != 0){
                for (int i = 0; i < campaignDTO.getLstGroupUser().size(); i++){
                    if (i == campaignDTO.getLstGroupUser().size() - 1){
                        group = group + campaignDTO.getLstGroupUser().get(i);
                    } else{
                        group = group + campaignDTO.getLstGroupUser().get(i) + ",";
                    }
                }
            }
            campaignDTO.setGroupUser(group);
            Date date = new Date();
            campaignDTO.setUpdateDatetime(date);
//            campaignDTO.setCreateDatetime(campaignDTO.getCreateDatetime());
            CampaignDTO result = campaignService.save(campaignDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/campaigns/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(success);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * {@code GET  /campaigns} : get all the campaigns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaigns in body.
     */
    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns(Pageable pageable) {
        log.debug("REST request to get a page of Campaigns");
        Page<CampaignDTO> page = campaignService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaigns/:id} : get the "id" campaign.
     *
     * @param id the id of the campaignDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaigns/{id}")
    public ResponseEntity<CampaignDTO> getCampaign(@PathVariable Long id) {
        log.debug("REST request to get Campaign : {}", id);
        Optional<CampaignDTO> campaignDTO = campaignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignDTO);
    }

    /**
     * {@code DELETE  /campaigns/:id} : delete the "id" campaign.
     *
     * @param id the id of the campaignDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaigns/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        log.debug("REST request to delete Campaign : {}", id);
        campaignService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/campaigns/query")
    public ResponseEntity<List<CampaignDTO>> doSearch(@RequestBody CampaignDTO campaignDTO, Pageable pageable) {
        Page<CampaignDTO> page = campaignService.searchCampaign(campaignDTO, pageable);
        List<CampaignDTO> list = page.getContent();
        for (CampaignDTO campaignDTO1: list){
            List<String> lstGroup = new ArrayList<>();
            String lstGroup2 [] = campaignDTO1.getGroupUser().split(",");
            for (String a: lstGroup2){
                lstGroup.add(a);
            }
            campaignDTO1.setLstGroupUser(lstGroup);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/campaigns/search")
    public ResponseEntity<List<CampaignDTO>> doSearchs(@RequestBody CampaignDTO campaignDTO, Pageable pageable) {
        Page<CampaignDTO> page = campaignService.findAllCampaigns(campaignDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/campaigns/findAll")
    public ResponseEntity<List<CampaignDTO>> findAll(@RequestBody CampaignDTO campaignDTO, Pageable pageable) {
        Page<CampaignDTO> page = campaignService.findAllCampaign(campaignDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
