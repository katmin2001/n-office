package com.fis.crm.web.rest;

import com.fis.crm.repository.TicketRepository;
import com.fis.crm.service.TicketService;
import com.fis.crm.service.dto.TicketDTO;

import com.fis.crm.repository.DepartmentRepository;
import com.fis.crm.service.DepartmentService;
import com.fis.crm.service.dto.DepartmentDTO;

import com.fis.crm.web.rest.errors.BadRequestAlertException;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.Ticket}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

  private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

  private static final String ENTITY_NAME = "ticket";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final DepartmentService departmentService;

  private final DepartmentRepository departmentRepository;

  public DepartmentResource(DepartmentService departmentService, DepartmentRepository departmentRepository) {
    this.departmentService = departmentService;
    this.departmentRepository = departmentRepository;
  }

        //get list history support
    @GetMapping("/departments/getListDepartmentsForCombobox")
    public ResponseEntity<List<DepartmentDTO>> getListDepartmentsForCombobox() {
        log.debug("REST request to get list of departments for combobox");
        List<DepartmentDTO> page = departmentService.getListDepartmentsForCombobox();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

  /**
   * {@code POST  /tickets} : Create a new ticket.
   *
   * @param ticketDTO the ticketDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
//  @PostMapping("/tickets/create")
//  public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
//    log.debug("REST request to save Ticket : {}", ticketDTO);
//    if (ticketDTO.getTicketId() != null) {
//      throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
//    }
//    String result = ticketService.save(ticketDTO);
//    return ResponseEntity
//        .ok()
//      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result))
//      .body(result);
//  }

  /**
   * {@code PUT  /tickets/:id} : Updates an existing ticket.
   *
   * @param ticketId the id of the ticketDTO to save.
   * @param ticketDTO the ticketDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketDTO,
   * or with status {@code 400 (Bad Request)} if the ticketDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the ticketDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
//  @PutMapping("/tickets/{ticketId}")
//  public ResponseEntity<?> updateTicket(
//    @PathVariable(value = "ticketId", required = false) final Long ticketId,
//    @RequestBody TicketDTO ticketDTO
//  ) throws URISyntaxException {
//    log.debug("REST request to update Ticket : {}, {}", ticketId, ticketDTO);
//    if (ticketDTO.getTicketId() == null) {
//      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//    }
//    if (!Objects.equals(ticketId, ticketDTO.getTicketId())) {
//      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//    }
//
//    if (!ticketRepository.existsById(ticketId)) {
//      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//    }
//
//    String result = ticketService.save(ticketDTO);
//    return ResponseEntity
//      .ok()
//      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result))
//      .body(result);
//  }

  /**
   * {@code PATCH  /tickets/:id} : Partial updates given fields of an existing ticket, field will ignore if it is null
   *
   * @param id the id of the ticketDTO to save.
   * @param ticketDTO the ticketDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketDTO,
   * or with status {@code 400 (Bad Request)} if the ticketDTO is not valid,
   * or with status {@code 404 (Not Found)} if the ticketDTO is not found,
   * or with status {@code 500 (Internal Server Error)} if the ticketDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
//  @PatchMapping(value = "/tickets/{id}", consumes = "application/merge-patch+json")
//  public ResponseEntity<TicketDTO> partialUpdateTicket(
//    @PathVariable(value = "id", required = false) final Long id,
//    @RequestBody TicketDTO ticketDTO
//  ) throws URISyntaxException {
//    log.debug("REST request to partial update Ticket partially : {}, {}", id, ticketDTO);
//    if (ticketDTO.getTicketId() == null) {
//      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//    }
//    if (!Objects.equals(id, ticketDTO.getTicketId())) {
//      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//    }
//
//    if (!ticketRepository.existsById(id)) {
//      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//    }
//
//    Optional<TicketDTO> result = ticketService.partialUpdate(ticketDTO);
//
//    return ResponseUtil.wrapOrNotFound(
//      result,
//      HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketDTO.getTicketId().toString())
//    );
//  }

  /**
   * {@code GET  /tickets} : get all the tickets.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tickets in body.
   */
//  @GetMapping("/tickets")
//  public ResponseEntity<List<TicketDTO>> getAllTickets(Pageable pageable) {
//    log.debug("REST request to get a page of Tickets");
//    Page<TicketDTO> page = ticketService.findAll(pageable);
//    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//    return ResponseEntity.ok().headers(headers).body(page.getContent());
//  }

  /**
   * {@code GET  /tickets/:id} : get the "id" ticket.
   *
   * @param id the id of the ticketDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketDTO, or with status {@code 404 (Not Found)}.
   */
//  @GetMapping("/tickets/{id}")
//  public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
//    log.debug("REST request to get Ticket : {}", id);
//    Optional<TicketDTO> ticketDTO = ticketService.findOne(id);
//    return ResponseUtil.wrapOrNotFound(ticketDTO);
//  }

  /**
   * {@code DELETE  /tickets/:id} : delete the "id" ticket.
   *
   * @param id the id of the ticketDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
//  @DeleteMapping("/tickets/{id}")
//  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
//    log.debug("REST request to delete Ticket : {}", id);
//    ticketService.delete(id);
//    return ResponseEntity
//      .noContent()
//      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
//      .build();
//  }
//
//    //get list history support
//    @GetMapping("/tickets/getListHistorySupports")
//    public ResponseEntity<List<TicketDTO>> getListHistorySupports() {
//        log.debug("REST request to get list of history support");
//        List<TicketDTO> page = ticketService.getListHistorySupports();
//        return new ResponseEntity<>(page, null, HttpStatus.OK);
//    }
}
