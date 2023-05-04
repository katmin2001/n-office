package com.fis.crm.web.rest;

import com.fis.crm.domain.TicketRequest;
import com.fis.crm.repository.TicketRepository;
import com.fis.crm.service.TicketService;
import com.fis.crm.service.dto.TicketDTO;

import com.fis.crm.repository.TicketRequestRepository;
import com.fis.crm.service.TicketRequestService;
import com.fis.crm.service.dto.TicketRequestDTO;

import com.fis.crm.service.dto.TicketRequestExtDTO;
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
 * REST controller for managing {@link com.fis.crm.domain.TicketRequest}.
 */
@RestController
@RequestMapping("/api")
public class TicketRequestResource {

    private final Logger log = LoggerFactory.getLogger(TicketRequestResource.class);

    private static final String ENTITY_NAME = "ticket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketRequestService ticketRequestService;

    private final TicketRequestRepository ticketRequestRepository;

    public TicketRequestResource(TicketRequestService ticketRequestService, TicketRequestRepository ticketRequestRepository) {
        this.ticketRequestService = ticketRequestService;
        this.ticketRequestRepository = ticketRequestRepository;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param ticketRequestDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticketRequests/create")
    public ResponseEntity<TicketRequestDTO> createTicketRequest(@RequestBody TicketRequestDTO ticketRequestDTO) throws URISyntaxException {
        log.debug("REST request to save TicketRequest : {}", ticketRequestDTO);
        if (ticketRequestDTO.getTicketRequestId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketRequestDTO result = ticketRequestService.save(ticketRequestDTO);
        return ResponseEntity
            .created(new URI("/api/ticketRequests/create/" + result.getTicketRequestId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTicketRequestId().toString()))
            .body(result);
    }

    @PostMapping("/ticket-resource/getTicketRequestByTicketId")
    public ResponseEntity<List<TicketRequestExtDTO>> getTicketRequestByTicketId(@RequestBody TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketRequestService.getTicketRequestByTicketId(ticketDTO), HttpStatus.OK);
    }


}
