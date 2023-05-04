package com.fis.crm.web.rest;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.FileUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.User;
import com.fis.crm.repository.ConfirmTicketRepository;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.ConfirmTicketAttachmentService;
import com.fis.crm.service.ConfirmTicketService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.ConfirmTicketAttachmentDTO;
import com.fis.crm.service.dto.ConfirmTicketDTO;
import com.fis.crm.service.dto.ServiceResult;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

/**
 * REST controller for managing {@link com.fis.crm.domain.ConfirmTicket}.
 */
@RestController
@RequestMapping("/api")
public class ConfirmTicketResource {

    private final Logger log = LoggerFactory.getLogger(ConfirmTicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfirmTicketService confirmTicketService;

    private final ConfirmTicketAttachmentService confirmTicketAttachmentService;

    private final ConfirmTicketRepository confirmTicketRepository;

    private final UserService userService;

    private final Environment env;

    public ConfirmTicketResource(ConfirmTicketService confirmTicketService,
                                 ConfirmTicketRepository confirmTicketRepository,
                                 UserService userService,
                                 ConfirmTicketAttachmentService confirmTicketAttachmentService, Environment env) {
        this.confirmTicketService = confirmTicketService;
        this.confirmTicketRepository = confirmTicketRepository;
        this.userService = userService;
        this.confirmTicketAttachmentService = confirmTicketAttachmentService;
        this.env = env;
    }

    /**
     * {@code POST  /tickets} : Create a new ticket.
     *
     * @param confirmTicketDTO the ticketDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketDTO, or with status {@code 400 (Bad Request)} if the ticket has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confirm-tickets/create")
    public ResponseEntity<ServiceResult> createConfirmTicket(@RequestBody ConfirmTicketDTO confirmTicketDTO) throws URISyntaxException {
        log.debug("REST request to save process Ticket : {}", confirmTicketDTO);
        if (confirmTicketDTO.getConfirmTicketId() != null) {
            throw new BadRequestAlertException("A new process ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        if (!user.getConfirmTicket()) {
            throw new BadRequestAlertException(Translator.toLocale("user.has.no.right.to.process.ticket"), "ticketDTO", "user.has.no.right.to.process.ticket");
        }
        confirmTicketDTO.setCreateUser(user.getId());
        return new ResponseEntity<>(confirmTicketService.save(confirmTicketDTO), HttpStatus.OK);
    }

    //get list history confirm ticket
    @PostMapping("/confirm-tickets/getListHistoryConfirmTickets")
    public ResponseEntity<List<ConfirmTicketDTO>> getListHistoryConfirmTickets(@RequestBody ConfirmTicketDTO confirmTicketDTO) {
        log.debug("REST request to get list of history confirm tickets");
        if (confirmTicketDTO.getTicketId() == null) {
            throw new BadRequestAlertException("Require ticketId", ENTITY_NAME, "Require ticketId");
        }
        List<ConfirmTicketDTO> page = confirmTicketService.getListHistoryConfirmTickets(confirmTicketDTO.getTicketId());
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @GetMapping("/confirm-tickets/get-by-ticket-id")
    public ResponseEntity<List<ConfirmTicketDTO>> getAllByTicketId(Long ticketId) {
        log.debug("REST request to get list of history confirm tickets");
        List<ConfirmTicketDTO> results = confirmTicketService.getAllByTicketId(ticketId);
        return new ResponseEntity<>(results, null, HttpStatus.OK);
    }

    @GetMapping("/confirm-tickets/get-list-confirm-ticket-attachment/{id}")
    public ResponseEntity<?> getListConfirmTicketAttachment(@PathVariable(value = "id") Long ticketId) {
        log.debug("REST request to get list confirm ticket attachment");
        List<ConfirmTicketAttachmentDTO> results = confirmTicketAttachmentService.getAllByTicketId(ticketId);
        return new ResponseEntity<>(results, null, HttpStatus.OK);
    }

    @PostMapping("/confirm-tickets/delete-confirm-attachment")
    public ResponseEntity<?> deleteConfirmAttachment(@RequestBody ConfirmTicketAttachmentDTO confirmTicketAttachmentDTO) {
        log.debug("REST request to delete list confirm ticket attachment");
        if (null != confirmTicketAttachmentDTO.getConfirmTicketAttachmentId()) {
            confirmTicketAttachmentService.delete(confirmTicketAttachmentDTO.getConfirmTicketAttachmentId());
        }
        //Xoa tren o dia
        String folder = env.getProperty("file-manager.confirm-file");
        deleteFile(folder, confirmTicketAttachmentDTO.getFillNameEncrypt());
        ServiceResult result = new ServiceResult();
        result.setMessage(Translator.toLocale("delete.confirm.ticket.attachment.success"));
        result.setStatus(ServiceResult.Status.SUCCESS);
        return ResponseEntity.ok(result);
    }

    public static void deleteFile(String folder, String fileName) {
        Path path = FileSystems.getDefault().getPath(folder + fileName);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    @PostMapping(value = "/confirm-tickets/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
            String folder = env.getProperty("file-manager.confirm-file");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            String fileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename().replace(" ", "_").replaceAll("[\\\\/:*?\"<>|]", "_");
            String filePath = folder + fileName;
            FileUtils.saveFile(filePath, file);
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/confirm-tickets/download-file-attachment/{nameFile}")
    public Object downloadFileAttachment(@PathVariable(name = "nameFile") String nameFile){
        String path = env.getProperty("file-manager.confirm-file");
        return FileUtils.downloadFile(path, nameFile);
    }

    @GetMapping("/confirm-tickets/get-file")
    public ResponseEntity<Resource> getFile (@RequestParam(value = "file-name") String fileName) {
        try {
            InputStreamResource resource = null;
            String path = env.getProperty("file-manager.confirm-file");
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
