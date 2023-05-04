package com.fis.crm.web.rest;

import com.fis.crm.service.BussinessTypeService;
import com.fis.crm.service.OptionSetValueService;
import com.fis.crm.service.dto.ApDomainDTO;
import com.fis.crm.service.dto.RequestTypeDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.service.dto.BussinessTypeDTO;

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
 * REST controller for managing {@link com.fis.crm.domain.BussinessType}.
 */
@RestController
@RequestMapping("/api")
public class BussinessTypeResource {

    private final Logger log = LoggerFactory.getLogger(BussinessTypeResource.class);

    private static final String ENTITY_NAME = "bussinessType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BussinessTypeService bussinessTypeService;

    private final OptionSetValueService optionSetValueService;

    public BussinessTypeResource(BussinessTypeService bussinessTypeService,
                                 OptionSetValueService optionSetValueService) {
        this.bussinessTypeService = bussinessTypeService;
        this.optionSetValueService = optionSetValueService;
    }

    /**
     * {@code POST  /bussiness-types} : Create a new bussinessType.
     *
     * @param bussinessTypeDTO the bussinessTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bussinessTypeDTO, or with status {@code 400 (Bad Request)} if the bussinessType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bussiness-types")
    public ResponseEntity<BussinessTypeDTO> createBussinessType(@RequestBody BussinessTypeDTO bussinessTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BussinessType : {}", bussinessTypeDTO);
        if (bussinessTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bussinessType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BussinessTypeDTO result = bussinessTypeService.save(bussinessTypeDTO);
        return ResponseEntity.created(new URI("/api/bussiness-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bussiness-types} : Updates an existing bussinessType.
     *
     * @param bussinessTypeDTO the bussinessTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bussinessTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bussinessTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bussinessTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bussiness-types")
    public ResponseEntity<BussinessTypeDTO> updateBussinessType(@RequestBody BussinessTypeDTO bussinessTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BussinessType : {}", bussinessTypeDTO);
        if (bussinessTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BussinessTypeDTO result = bussinessTypeService.save(bussinessTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bussinessTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bussiness-types} : get all the bussinessTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bussinessTypes in body.
     */
    @GetMapping("/bussiness-types")
    public ResponseEntity<List<BussinessTypeDTO>> getAllBussinessTypes(Pageable pageable) {
        log.debug("REST request to get a page of BussinessTypes");
        Page<BussinessTypeDTO> page = bussinessTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/bussiness-types/combobox")
    public ResponseEntity<List<BussinessTypeDTO>> getBussinessTypeForCombobox(){
        log.debug("REST request to get a list of BussinessTypes for combobox");
        return new ResponseEntity<>(optionSetValueService.getBusinessTypeForCombobox(), HttpStatus.OK);
    }

    @GetMapping("/request-types/combobox")
    public ResponseEntity<List<RequestTypeDTO>> getRequestTypeForCombobox(){
        log.debug("REST request to get a list of RequestTypes for combobox");
        return new ResponseEntity<>(optionSetValueService.getRequestTypeForCombobox(), HttpStatus.OK);
    }

    /**
     * {@code GET  /bussiness-types/:id} : get the "id" bussinessType.
     *
     * @param id the id of the bussinessTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bussinessTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bussiness-types/{id}")
    public ResponseEntity<BussinessTypeDTO> getBussinessType(@PathVariable Long id) {
        log.debug("REST request to get BussinessType : {}", id);
        Optional<BussinessTypeDTO> bussinessTypeDTO = bussinessTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bussinessTypeDTO);
    }

    /**
     * {@code DELETE  /bussiness-types/:id} : delete the "id" bussinessType.
     *
     * @param id the id of the bussinessTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bussiness-types/{id}")
    public ResponseEntity<Void> deleteBussinessType(@PathVariable Long id) {
        log.debug("REST request to delete BussinessType : {}", id);
        bussinessTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
