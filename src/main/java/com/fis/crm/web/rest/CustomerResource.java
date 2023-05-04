package com.fis.crm.web.rest;

import com.fis.crm.repository.TicketRepository;
import com.fis.crm.service.TicketService;
import com.fis.crm.service.dto.TicketDTO;

import com.fis.crm.repository.CustomerRepository;
import com.fis.crm.service.CustomerService;
import com.fis.crm.service.dto.CustomerDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.Customer}.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

  private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

  private static final String ENTITY_NAME = "customer";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CustomerService customerService;

  private final CustomerRepository customerRepository;

  public CustomerResource(CustomerService customerService, CustomerRepository customerRepository) {
    this.customerService = customerService;
    this.customerRepository = customerRepository;
  }

  /**
   * {@code POST  /tickets} : Create a new ticket.
   *
   * @param customerDTO the ticketDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/customers/create")
  public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) throws URISyntaxException {
    log.debug("REST request to save customer : {}", customerDTO);
    if (customerDTO.getCustomerId() != null) {
      throw new BadRequestAlertException("A new customer cannot already have an ID", ENTITY_NAME, "idexists");
    }
    CustomerDTO result = customerService.save(customerDTO);
    return ResponseEntity
      .created(new URI("/api/customer/create/" + result.getCustomerId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCustomerId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /tickets/:id} : Updates an existing ticket.
   *
   * @param customerId the id of the ticketDTO to save.
   * @param customerDTO the ticketDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketDTO,
   * or with status {@code 400 (Bad Request)} if the ticketDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the ticketDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/customers/{customerId}")
  public ResponseEntity<CustomerDTO> updateCusotmer(
    @PathVariable(value = "customerId", required = false) final Long customerId,
    @RequestBody CustomerDTO customerDTO
  ) throws URISyntaxException {
    log.debug("REST request to update Customer : {}, {}", customerId, customerDTO);
    if (customerDTO.getCustomerId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(customerId, customerDTO.getCustomerId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!customerRepository.existsById(customerId)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    CustomerDTO result = customerService.save(customerDTO);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerDTO.getCustomerId().toString()))
      .body(result);
  }

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
  @GetMapping("/customers/search")
  public ResponseEntity<List<CustomerDTO>> getAllCustomers(Pageable pageable) {
    log.debug("REST request to get a page of customers");
    Page<CustomerDTO> page = customerService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /tickets/:id} : get the "id" ticket.
   *
   * @param customerId the id of the ticketDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/customers/{customerId}")
  public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
    log.debug("REST request to get customer : {}", customerId);
    Optional<CustomerDTO> customerDTO = customerService.findOne(customerId);
    return ResponseUtil.wrapOrNotFound(customerDTO);
  }

  /**
   * {@code DELETE  /tickets/:id} : delete the "id" ticket.
   *
   * @param customerId the id of the ticketDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/customers/{customerId}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
    log.debug("REST request to delete customers : {}", customerId);
    customerService.delete(customerId);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, customerId.toString()))
      .build();
  }

    @GetMapping("/customers/getListCustomersForCombobox")
    public ResponseEntity<List<CustomerDTO>> getListCustomersForCombobox() {
        log.debug("REST request to get list of customer for combobox");
        List<CustomerDTO> page = customerService.getListCustomersForCombobox();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }


}
