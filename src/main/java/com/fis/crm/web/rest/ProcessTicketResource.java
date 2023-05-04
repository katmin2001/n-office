package com.fis.crm.web.rest;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ProcessTicketRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ProcessTicketAttachmentService;
import com.fis.crm.service.ProcessTicketService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;

import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing {@link com.fis.crm.domain.Ticket}.
 */
@RestController
@RequestMapping("/api")
public class ProcessTicketResource {

    private final Logger log = LoggerFactory.getLogger(ProcessTicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessTicketService processTicketService;

    private final ProcessTicketAttachmentService processTicketAttachmentService;

    private final UserService userService;
    private final Environment env;

    public ProcessTicketResource(ProcessTicketService processTicketService, ProcessTicketRepository processTicketRepository, ProcessTicketAttachmentService processTicketAttachmentService, UserService userService, Environment env) {
        this.processTicketService = processTicketService;
        this.processTicketAttachmentService = processTicketAttachmentService;
        this.userService = userService;
        this.env = env;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param processTicketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-tickets/create")
    public ResponseEntity<ServiceResult> createProcessTicket(@RequestBody ProcessTicketDTO processTicketDTO) throws URISyntaxException {
        log.debug("REST request to save process Ticket : {}", processTicketDTO);
        if (processTicketDTO.getProcessTicketId() != null) {
            throw new BadRequestAlertException("A new process ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
//        if (!user.getProcessTicket()) {
//            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
//        }
        return new ResponseEntity<>(processTicketService.save(processTicketDTO), HttpStatus.OK);
    }

    //get list history process ticket
    @PostMapping("/process-tickets/getListHistoryProcessTickets")
    public ResponseEntity<List<ProcessTicketDTO>> getListHistoryProcessTickets(@RequestBody ProcessTicketDTO processTicketDTO) {
        log.debug("REST request to get list of history process tickets");
        List<ProcessTicketDTO> page = processTicketService.getListHistoryProcessTickets(processTicketDTO);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    //get list history process ticket
    @PostMapping("/process-tickets/getListFileTicketProcessAttachment")
    public ResponseEntity<List<ProcessTicketAttachmentDTO>> getListAttachment(@RequestBody ProcessTicketDTO processTicketDTO) {
        log.debug("REST request to get list of history process tickets");
        List<ProcessTicketAttachmentDTO> page = processTicketAttachmentService.findAllByTicketRequestId(processTicketDTO.getTicketRequestId());
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping(value = "/process-tickets/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ConfirmTicketAttachmentDTO>> uploadFile(@RequestParam("files") MultipartFile[] files) {
        ServiceResult serviceResult = new ServiceResult();
        List<ConfirmTicketAttachmentDTO> confirmTicketAttachmentDTOS = new ArrayList<>();
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (!user.getConfirmTicket() || null == user.getConfirmTicket()) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
        }
        Double maxFileSize = DataUtil.safeToDouble(env.getProperty("file-manager.max-size"));
        String fileExt = env.getProperty("file-manager.file-ext");
        for (MultipartFile file : files) {
            Long validate = FileUtils.validateAttachFile(file, fileExt, maxFileSize);
            if (!validate.equals(0L)) {
                if (validate.equals(24L)) {
                    serviceResult.setStatus(ServiceResult.Status.FAILED);
                    throw new BadRequestAlertException(Translator.toLocale("error.format.file"), "ticketDTO", "error.format.file");
                }
                if (validate.equals(25L)) {
                    throw new BadRequestAlertException(Translator.toLocale("error.file.size"), "ticketDTO", "error.file.size");
                }
            }
            try {
                String encryptFileName = saveFile(file);
                ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO = new ConfirmTicketAttachmentDTO();
                confirmTicketAttachmentDTO.setFileName(file.getOriginalFilename());
                confirmTicketAttachmentDTO.setFillNameEncrypt(encryptFileName);
                confirmTicketAttachmentDTO.setCreateDatetime(Instant.now());
                confirmTicketAttachmentDTOS.add(confirmTicketAttachmentDTO);

            } catch (Exception e) {
                throw new BadRequestAlertException(Translator.toLocale("error.upload.file"), "ticketDTO", "error.upload.file");
            }
        }
        return ResponseEntity.ok().body(confirmTicketAttachmentDTOS);
    }

    private String saveFile(MultipartFile file) {
        try {
            String folder = env.getProperty("file-manager.process-file");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String fileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename().replace(" ", "_").replaceAll("[\\\\/:*?\"<>|]", "_");
            String filePath = folder + fileName;
            FileUtils.saveFile(filePath, file);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/process-tickets/get-ticket-process-attachment/{id}")
    public ResponseEntity<?> getProcessTicketAttachment(@PathVariable(name = "id") Long ticketRequestId) {
        log.debug("REST request to get list process ticket attachment");
        List<ProcessTicketAttachmentDTO> results = processTicketAttachmentService.findByTicketRequestId(ticketRequestId);
        return new ResponseEntity<>(results, null, HttpStatus.OK);
    }

    @PostMapping("/process-tickets/delete-process-ticket-attachment")
    public ResponseEntity<?> deleteProcessTicketAttachment(@RequestBody ProcessTicketAttachmentDTO processTicketAttachmentDTO) {
        log.debug("REST request to delete list process ticket attachment");
        if (null != processTicketAttachmentDTO.getProcessTicketAttachmentId()) {
            processTicketAttachmentService.delete(processTicketAttachmentDTO.getProcessTicketAttachmentId());
        }
        //Xoa tren o dia
        String folder = env.getProperty("file-manager.process-file");
        ConfirmTicketResource.deleteFile(folder, processTicketAttachmentDTO.getFillNameEncrypt());
        ServiceResult result = new ServiceResult();
        result.setMessage(Translator.toLocale("delete.ticket.attachment.success"));
        result.setStatus(ServiceResult.Status.SUCCESS);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/process-tickets/download-file-attachment/{nameFile}")
    public Object downloadFileAttachment(@PathVariable(name = "nameFile") String nameFile){
        String path = env.getProperty("file-manager.process-file");
        return FileUtils.downloadFile(path, nameFile);
    }

    @PostMapping("/tickets/create-close")
    public ResponseEntity<ServiceResult> createClose(@RequestBody TicketProcessDTO ticketProcessDTO){
        try {
            ServiceResult result = processTicketService.saveFRC(ticketProcessDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/process-tickets/get-file")
    public ResponseEntity<Resource> getFile (@RequestParam(value = "file-name") String fileName) {
        try {
            InputStreamResource resource = null;
            String path = env.getProperty("file-manager.process-file");
            path = path + fileName;
            File file = new File(path);
            if (!file.exists()) {
                throw new BadRequestAlertException("Tải file thất bại", ENTITY_NAME, "");
            }

            resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (FileNotFoundException e) {
            throw new BadRequestAlertException("Tải file thất bại", ENTITY_NAME, "");
        }
    }
}
