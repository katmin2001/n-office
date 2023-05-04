package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.repository.TicketRequestAttachmentRepository;
import com.fis.crm.service.TicketRequestAttachmentService;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import com.fis.crm.service.dto.ConfirmTicketAttachmentDTO;
import com.fis.crm.service.dto.ServiceResult;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.response.ConfigScheduleError;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing {@link com.fis.crm.domain.TicketRequest}.
 */
@RestController
@RequestMapping("/api")
public class TicketRequestAttachmentResource {

  private final Logger log = LoggerFactory.getLogger(TicketRequestAttachmentResource.class);

  private static final String ENTITY_NAME = "ticket";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final TicketRequestAttachmentService ticketRequestAttachmentService;

  private final TicketRequestAttachmentRepository ticketRequestAttachmentRepository;

  private final Environment env;

  public TicketRequestAttachmentResource(TicketRequestAttachmentService ticketRequestAttachmentService,
                                         TicketRequestAttachmentRepository ticketRequestAttachmentRepository,
                                         Environment env) {
    this.ticketRequestAttachmentService = ticketRequestAttachmentService;
    this.ticketRequestAttachmentRepository = ticketRequestAttachmentRepository;
    this.env = env;
  }

  /**
   * {@code POST  /tickets} : Create a new ticket.
   *
   * @param ticketRequestAttachmentDTO the ticketDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/ticketRequestAttachments/create")
  public ResponseEntity<TicketRequestAttachmentDTO> createTicketRequestAttachment(@RequestBody TicketRequestAttachmentDTO
                                                                                        ticketRequestAttachmentDTO) throws URISyntaxException {
    log.debug("REST request to save ticketRequestAttachments : {}", ticketRequestAttachmentDTO);
    if (ticketRequestAttachmentDTO.getTicketRequestId() != null) {
      throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
    }
      TicketRequestAttachmentDTO result = ticketRequestAttachmentService.save(ticketRequestAttachmentDTO);
    return ResponseEntity
      .created(new URI("/api/ticketRequestAttachments/create/" + result.getTicketRequestAttachmentId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTicketRequestAttachmentId().toString()))
      .body(result);
  }

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
//  public ResponseEntity<TicketDTO> updateTicket(
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
//    TicketDTO result = ticketService.save(ticketDTO);
//    return ResponseEntity
//      .ok()
//      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketDTO.getTicketId().toString()))
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
     * Lay danh sach file dinh kem cua ticket request
     * @param ticketRequestId
     * @return
     */
  @GetMapping("/ticket-request-attachment/{ticketRequestId}")
  public ResponseEntity<List<TicketRequestAttachmentDTO>> getTicket(@PathVariable Long ticketRequestId) {
    log.debug("REST request to get Ticket : {}", ticketRequestId);
      List<TicketRequestAttachmentDTO> result = ticketRequestAttachmentService.findByTicketId(ticketRequestId);
      return ResponseEntity.ok().body(result);
  }



  @PostMapping("/ticket-request-attachment/uploadFile")
  public ResponseEntity<Object>  getSampleFile(@RequestParam("file") MultipartFile file) {
      log.debug("REST request to down file sample ConfigSchedule : {}");
      HttpHeaders responseHeader = new HttpHeaders();
      try{
          String now = new Date().getTime()+"";
          File myFile = new File( "fileError_"+now+".xlsx");
          FileOutputStream out = new FileOutputStream(myFile);
          String p = myFile.getAbsolutePath();
          //FileOutputStream out = new FileOutputStream(new File( "fileError_"+now+".xlsx"));
//          ConfigScheduleDTO result = configScheduleService.importList(file);
          ConfigScheduleDTO result = new ConfigScheduleDTO();
          if( null!=result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig()) ){
              if(!result.getErrorCodeConfig().equals("formatError")) {
                  ConfigScheduleError error = new ConfigScheduleError("400",result.getErrorCodeConfig());
                  responseHeader.setContentType(MediaType.TEXT_PLAIN);
                  return ResponseEntity.ok().headers(responseHeader).body(error);
              }
              else{
                  Workbook workbook = result.getXssfWorkbook();
                  if(null!=workbook && result.getErrorCodeConfig().equals("formatError")) {
                      workbook.write(out);
                      //responseHeader.set("Content-disposition", "attachment; filename=" + file.getName());
                      //responseHeader.setContentLength(data.length);
                      //File newDirectory = new File(DIRECTORY,"fileError_"+new Date().getTime()+".xlsx");
                      out.close();
                      FileInputStream fis = new FileInputStream(new File( "fileError_"+now+".xlsx"));
                      InputStreamResource inputStreamResource = new InputStreamResource(fis);
                      responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                      return new ResponseEntity<>(inputStreamResource, responseHeader  , HttpStatus.OK);
                  }
                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
              }
          }
          ConfigScheduleError success = new ConfigScheduleError("200","OK");
          return ResponseEntity.created(new URI("/api/config-schedules/" + result.getId()))
              .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
              .body(success);
      }
      catch (Exception e){
          log.error(e.getMessage(), e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
  }

  @PostMapping("/ticket-request-attachment/delete")
  public ResponseEntity<?> deleteRequestTicketAttachment(@RequestBody TicketRequestAttachmentDTO ticketRequestAttachmentDTO) {
      if(null != ticketRequestAttachmentDTO.getTicketRequestId()){
          ticketRequestAttachmentService.delete(ticketRequestAttachmentDTO.getTicketRequestAttachmentId());
      }
      //Xoa tren o dia
      String folder = env.getProperty("file-manager.request-file");
      ConfirmTicketResource.deleteFile(folder,ticketRequestAttachmentDTO.getFillNameEncrypt());
      ServiceResult result = new ServiceResult();
      result.setMessage(Translator.toLocale("delete.ticket.attachment.success"));
      result.setStatus(ServiceResult.Status.SUCCESS);
      return ResponseEntity.ok(result);
  }
}
