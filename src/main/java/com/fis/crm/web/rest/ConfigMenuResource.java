package com.fis.crm.web.rest;

import com.fis.crm.repository.ConfigMenuRepository;
import com.fis.crm.service.ConfigMenuService;
import com.fis.crm.service.dto.ConfigMenuDTO;
import com.fis.crm.service.dto.FunctionItemDTO;
import com.fis.crm.service.dto.MenuItemDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.fis.crm.domain.ConfigMenu}.
 */
@RestController
@RequestMapping("/api")
public class ConfigMenuResource {

    private final Logger log = LoggerFactory.getLogger(ConfigMenuResource.class);

    private static final String ENTITY_NAME = "configMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigMenuService configMenuService;

    private final ConfigMenuRepository configMenuRepository;

    public ConfigMenuResource(ConfigMenuService configMenuService, ConfigMenuRepository configMenuRepository) {
        this.configMenuService = configMenuService;
        this.configMenuRepository = configMenuRepository;
    }

    /**
     * {@code POST  /config-menus} : Create a new configMenu.
     *
     * @param configMenuDTO the configMenuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configMenuDTO, or with status {@code 400 (Bad Request)} if the configMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-menus")
    public ResponseEntity<ConfigMenuDTO> createConfigMenu(@RequestBody ConfigMenuDTO configMenuDTO) throws URISyntaxException {
        log.debug("REST request to save ConfigMenu : {}", configMenuDTO);
        if (configMenuDTO.getId() != null) {
            throw new BadRequestAlertException("A new configMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigMenuDTO result = configMenuService.save(configMenuDTO);
        return ResponseEntity
            .created(new URI("/api/config-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-menus/:id} : Updates an existing configMenu.
     *
     * @param id            the id of the configMenuDTO to save.
     * @param configMenuDTO the configMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configMenuDTO,
     * or with status {@code 400 (Bad Request)} if the configMenuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-menus/{id}")
    public ResponseEntity<ConfigMenuDTO> updateConfigMenu(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigMenuDTO configMenuDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConfigMenu : {}, {}", id, configMenuDTO);
        if (configMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configMenuDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configMenuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfigMenuDTO result = configMenuService.save(configMenuDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * Danh sach phan he
     * @return
     */
    @GetMapping("/config-menus")
    public ResponseEntity<List<ConfigMenuDTO>> getAllConfigMenus() {
        log.debug("REST request to get a page of ConfigMenus");
        List<ConfigMenuDTO> configMenuDTOS = configMenuService.findAll();
        return ResponseEntity.ok().body(configMenuDTOS);
    }

    /**
     * {@code GET  /config-menus/:id} : get the "id" configMenu.
     *
     * @param id the id of the configMenuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configMenuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-menus/{id}")
    public ResponseEntity<ConfigMenuDTO> getConfigMenu(@PathVariable Long id) {
        log.debug("REST request to get ConfigMenu : {}", id);
        Optional<ConfigMenuDTO> configMenuDTO = configMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configMenuDTO);
    }

    /**
     * {@code DELETE  /config-menus/:id} : delete the "id" configMenu.
     *
     * @param id the id of the configMenuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-menus/{id}")
    public ResponseEntity<Void> deleteConfigMenu(@PathVariable Long id) {
        log.debug("REST request to delete ConfigMenu : {}", id);
        configMenuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Lay danh sach menu theo user login
     * @param login
     * @return
     */
    @GetMapping("/config-menus/get-tree-by-login")
    public ResponseEntity<List<MenuItemDTO>> getMenuTreeLogin(String login) {
        log.debug("REST request to get a tree of Menus");
        List<MenuItemDTO> rs = configMenuService.getMenuTreeLogin(login);
        return ResponseEntity.ok().body(rs);
    }

    /**
     * Lay danh sach chuc nang theo role va menu hien tai
     * Phan quyen chuc nang cho role
     * @param menuId
     * @return
     */
    @GetMapping("/config-menus/get-function-by-menu/{roleId}/{menuId}")
    public ResponseEntity<List<FunctionItemDTO>> getFunctionByMenuAndRole(@PathVariable("roleId") Long roleId, @PathVariable("menuId") Long menuId) {
        List<FunctionItemDTO> rs = configMenuService.getFunctionByMenuAndRole(roleId, menuId);
        return ResponseEntity.ok().body(rs);
    }

    /**
     * Danh sach chuc nang, tac dong hoat dong
     * 3.1.3.4.1.	MH08
     * @param id
     * @return
     */
    @GetMapping("/config-menus/menu/{id}")
    public ResponseEntity<List<FunctionItemDTO>> getConfigMenuItemByMenu(@PathVariable Long id) {
        List<FunctionItemDTO> functionItemDTOS = configMenuService.getConfigMenuItemByMenu(id);
        return ResponseEntity.ok().body(functionItemDTOS);
    }

    /**
     * Danh sach chuc nang, tac dong vo hieu hoa
     * 3.1.3.10.1.	MH13
     * @param id
     * @return
     */
    @GetMapping("/config-menus/getFunctionInActive/{id}")
    public ResponseEntity<List<FunctionItemDTO>> getFunctionInActiveByMenu(@PathVariable Long id) {
        List<FunctionItemDTO> functionItemDTOS = configMenuService.getConfigMenuItemInActiveByMenu(id);
        return ResponseEntity.ok().body(functionItemDTOS);
    }
}
