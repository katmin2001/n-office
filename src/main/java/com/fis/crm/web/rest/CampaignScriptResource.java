package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.CampaignScriptService;
import com.fis.crm.service.dto.CampaignScriptDTO;
import com.fis.crm.service.dto.CampaignScriptListCbxDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import com.fis.crm.service.dto.CopyScriptRequestDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.service.response.ConfigScheduleError;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.CodeExisted;
import com.fis.crm.web.rest.errors.WrongFormat;
import io.undertow.util.BadRequestException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.CampaignScript}.
 */
@RestController
@RequestMapping("/api")
public class CampaignScriptResource {

    private final Logger log = LoggerFactory.getLogger(CampaignScriptResource.class);

    private static final String ENTITY_NAME = "campaignScript";
    @Autowired
    private ApplicationContext appContext;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignScriptService campaignScriptService;

    public CampaignScriptResource(CampaignScriptService campaignScriptService) {
        this.campaignScriptService = campaignScriptService;
    }


    @GetMapping("/campaign-scripts/getListCampaignScriptForCombobox")
    public ResponseEntity<List<CampaignScriptListCbxDTO>> getListDepartmentsForCombobox() {
        log.debug("REST request to get list of departments for combobox");
        List<CampaignScriptListCbxDTO> page = campaignScriptService.getListCampaignScriptForCombobox();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * {@code POST  /campaign-scripts} : Create a new campaignScript.
     *
     * @param campaignScriptDTO the campaignScriptDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignScriptDTO, or with status {@code 400 (Bad Request)} if the campaignScript has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-scripts")
    public ResponseEntity<Object> createCampaignScript(@RequestBody CampaignScriptDTO campaignScriptDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignScript : {}", campaignScriptDTO);
        try {
            SecurityUtils.getCurrentUserLogin();
            if (campaignScriptDTO.getId() != null) {
                throw new BadRequestAlertException("A new campaignScript cannot already have an ID", ENTITY_NAME, "idexists");
            }
            if (!campaignScriptService.checkName(campaignScriptDTO.getName())) {
                ConfigScheduleError error = new ConfigScheduleError("401", "name is exists");
                return ResponseEntity.ok().body(error);
            }
            CampaignScriptDTO result = campaignScriptService.save(campaignScriptDTO);
            if (null != result.getErrorCodeConfig() && !("").equals(result.getErrorCodeConfig())) {
                String message = result.getErrorCodeConfig();
                ConfigScheduleError error = new ConfigScheduleError("400", message);
                return ResponseEntity.ok().body(error);
            }
            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
            return ResponseEntity.created(new URI("/api/campaign-scripts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(success);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    /**
     * {@code PUT  /campaign-scripts} : Updates an existing campaignScript.
     *
     * @param campaignScriptDTO the campaignScriptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignScriptDTO,
     * or with status {@code 400 (Bad Request)} if the campaignScriptDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignScriptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-scripts/{id}")
    public ResponseEntity<MessageResponse<CampaignScriptDTO>> updateCampaignScript(@PathVariable(name="id") Long id,
                                                                                   @RequestBody CampaignScriptDTO campaignScriptDTO) throws URISyntaxException {
        if (!campaignScriptService.checkName(campaignScriptDTO.getName())) {
            return new ResponseEntity<>(new MessageResponse<>("nameExist", null), HttpStatus.OK);
        }
        campaignScriptDTO.setId(id);
        CampaignScriptDTO result = campaignScriptService.updateCampaignScript(campaignScriptDTO);
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
    }

    @PutMapping("/campaign-scripts/{id}/delete")
    public ResponseEntity<MessageResponse<CampaignScriptDTO>> updateStatusCampaignScript(@PathVariable(name="id") Long id,
                                                                                   @RequestBody CampaignScriptDTO campaignScriptDTO) throws URISyntaxException {

        campaignScriptDTO.setId(id);
        CampaignScriptDTO result = campaignScriptService.updateCampaignScript(campaignScriptDTO);
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
    }

    /**
     * {@code GET  /campaign-scripts} : get all the campaignScripts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignScripts in body.
     */
    @GetMapping("/campaign-scripts")
    public ResponseEntity<List<CampaignScriptDTO>> getAllCampaignScripts(Pageable pageable) {
        log.debug("REST request to get a page of CampaignScripts");
        Page<CampaignScriptDTO> page = campaignScriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-scripts/:id} : get the "id" campaignScript.
     *
     * @param id the id of the campaignScriptDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignScriptDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-scripts/{id}")
    public ResponseEntity<CampaignScriptDTO> getCampaignScript(@PathVariable Long id) {
        log.debug("REST request to get CampaignScript : {}", id);
        Optional<CampaignScriptDTO> campaignScriptDTO = campaignScriptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignScriptDTO);
    }

    /**
     * {@code DELETE  /campaign-scripts/:id} : delete the "id" campaignScript.
     *
     * @param id the id of the campaignScriptDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-scripts/{id}")
    public ResponseEntity<MessageResponse<String>> deleteCampaignScript(@PathVariable Long id) {
        log.debug("REST request to delete CampaignScript : {}", id);
        try {
            Optional<CampaignScriptDTO> result = campaignScriptService.findOne(id);
            campaignScriptService.delete(id);
//            if (null != result.get().getErrorCodeConfig() && !("").equals(result.get().getErrorCodeConfig())) {
//                String message = result.get().getErrorCodeConfig();
//                ConfigScheduleError error = new ConfigScheduleError("400", message);
//                return ResponseEntity.ok().body(error);
//            }
//            ConfigScheduleError success = new ConfigScheduleError("200", "OK");
//            return ResponseEntity.created(new URI("/api/campaign-scripts/" + result.get().getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.get().getId().toString()))
//                .body(success);
            return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse<>(e.getMessage(), null));
        }

    }


    @GetMapping("campaign-scripts/download-template")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        log.debug("REST request to download template file Campaign Script");
//        String path = "../template/target/templateScript.xlsx";
//        String path = "./bieu_mau_kich_ban.xlsx";
//        File file = new File(path);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
//        Resource resource = new InputStreamResource(byteArrayInputStream);
        Resource resource = new ClassPathResource("bieu_mau_kich_ban.xlsx");
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
        String fileName = "Bieu_mau_kich_ban_" + format.format(new Date());
        return ResponseEntity.ok().header("filename", fileName).body(resource);
    }

    @PostMapping("/campaign-scripts/findAll")
    public ResponseEntity<List<CampaignScriptDTO>> getAllCampaignScripts(@RequestBody CampaignScriptDTO campaignScriptDTO, Pageable pageable) {
        log.debug("REST request to get a page of CampaignScripts");
        Page<CampaignScriptDTO> page = campaignScriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/campaign-scripts/{id}/uploadFile")
    public ResponseEntity<MessageResponse<List<CampaignScriptQuestionResponseDTO>>> uploadFileQuestionAndAnswer(@PathVariable(name = "id") Long campaignScriptId,
                                                                                                                @RequestParam(value = "deleteAllQuestion") String delete,
//                                                                                                                @RequestParam("createUser") Long createUser,
                                                                                                                @RequestParam("file") MultipartFile file) {
        List<CampaignScriptQuestionResponseDTO> result;
        if(!delete.equals("1") && !delete.equals("2")){
            return ResponseEntity.badRequest().body(new MessageResponse<>("Param deleteAllQuestion must be '1' or '2'! 1 - deleteAll, 2- don't delete", null));
        }
        try {
            result = campaignScriptService.uploadFile(file, campaignScriptId, Integer.parseInt(delete));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(e instanceof CodeExisted || e instanceof WrongFormat || e instanceof BadRequestException){
                return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("question.file.wrong-format"), null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new MessageResponse<>("Save data successful", result), HttpStatus.CREATED);
    }

    @PostMapping("/campaign-scripts/{id}/getSampleFile")
    public ResponseEntity<InputStreamResource> getSampleFile() {
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            Resource resource = new ClassPathResource("bieu_mau_kich_ban.xlsx");
            InputStream inputStream = resource.getInputStream();
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);

        } catch (Exception e) {
            log.error("", e.getMessage());
            return new ResponseEntity<>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/campaign-scripts/getCheckCopyScript")
    public ResponseEntity<MessageResponse<Object[]>> getCheckScript(@RequestBody CopyScriptRequestDTO copyScriptRequestDTO){
        String re = campaignScriptService.copyScript(copyScriptRequestDTO);
        if (re == null){
            return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse<>("exist", null), HttpStatus.OK);
        }

    }
}
