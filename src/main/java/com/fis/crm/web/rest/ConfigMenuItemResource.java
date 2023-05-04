package com.fis.crm.web.rest;

import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.ConfigMenuItemService;
import com.fis.crm.service.dto.ConfigMenuItemAddDTO;
import com.fis.crm.service.dto.ConfigMenuItemDTO;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.ConfigMenuItem}.
 */
@RestController
@RequestMapping("/api")
public class ConfigMenuItemResource {

    private final Logger log = LoggerFactory.getLogger(ConfigMenuItemResource.class);

    private static final String ENTITY_NAME = "configMenuItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigMenuItemService configMenuItemService;
    private final ActionLogService actionLogService;

    public ConfigMenuItemResource(ConfigMenuItemService configMenuItemService, ActionLogService actionLogService) {
        this.configMenuItemService = configMenuItemService;
        this.actionLogService = actionLogService;
    }

    /**
     * Tao moi tac dong
     * 3.1.3.4.3.	MH09
     * @param configMenuItemDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/config-menu-items")
    public ResponseEntity<Boolean> createConfigMenuItem(@RequestBody ConfigMenuItemDTO configMenuItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConfigMenuItem : {}", configMenuItemDTO);
        Boolean result = configMenuItemService.save(configMenuItemDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     * Cap nhat tac dong
     * 3.1.3.4.5.	MH10
     * @param configMenuItemDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/config-menu-items")
    public ResponseEntity<Boolean> updateConfigMenuItem(@RequestBody ConfigMenuItemDTO configMenuItemDTO
    ) throws URISyntaxException {
        Boolean result = configMenuItemService.update(configMenuItemDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping("/config-menu-items")
    public ResponseEntity<List<ConfigMenuItemDTO>> getAllConfigMenuItems(Pageable pageable) {
        log.debug("REST request to get a page of ConfigMenuItems");
        Page<ConfigMenuItemDTO> page = configMenuItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/config-menu-items/{id}")
    public ResponseEntity<ConfigMenuItemDTO> getConfigMenuItem(@PathVariable Long id) {
        log.debug("REST request to get ConfigMenuItem : {}", id);
        Optional<ConfigMenuItemDTO> configMenuItemDTO = configMenuItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configMenuItemDTO);
    }

    /**
     * Vo hieu hoa tac dong
     *
     * @param id
     * @return
     */
    @DeleteMapping("/config-menu-items/{id}")
    public ResponseEntity<Void> deleteConfigMenuItem(@PathVariable Long id) {
        log.debug("REST request to delete ConfigMenuItem : {}", id);
        configMenuItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Gan chuc nang, tac dong cho quyen
     * 3.1.3.9.1.	MH12
     * @param configMenuItemAddDTO
     * @return
     */
    @PostMapping("/config-menu-items/addFunction")
    public ResponseEntity<Boolean> addFunctionMenu(@RequestBody ConfigMenuItemAddDTO configMenuItemAddDTO) {
        Boolean result = configMenuItemService.addFunctionMenu(configMenuItemAddDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }

    /**
     * active/inactive tac dong
     * 3.1.3.4.5.	MH10
     * @param configMenuItemDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/config-menu-items/action")
    public ResponseEntity<Boolean> actionConfigMenuItem(@RequestBody ConfigMenuItemDTO configMenuItemDTO
    ) throws URISyntaxException {
        Boolean result = configMenuItemService.action(configMenuItemDTO, actionLogService.getConsumerWriteLog());
        return ResponseEntity.ok().body(result);
    }
}
