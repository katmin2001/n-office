package com.fis.crm.web.rest;

import com.fis.crm.domain.User;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.EvaluateAssignmentService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.EvaluateAssignmentDTO;
import com.fis.crm.service.dto.EvaluateAssignmentDataDTO;
import com.fis.crm.service.dto.EvaluateAssignmentSearchDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.fis.crm.domain.EvaluateAssignment}.
 */
@RestController
@RequestMapping("/api")
public class EvaluateAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateAssignmentResource.class);

    private static final String ENTITY_NAME = "evaluateAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluateAssignmentService evaluateAssignmentService;

    private final UserService userService;

    public EvaluateAssignmentResource(EvaluateAssignmentService evaluateAssignmentService,UserService userService) {
        this.evaluateAssignmentService = evaluateAssignmentService;
        this.userService=userService;
    }

    /**
     * Phan cong danh gia
     * @param evaluateAssignmentDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/evaluate-assignments")
    public ResponseEntity<Boolean> createEvaluateAssignment(@RequestBody EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception {
        log.debug("REST request to save EvaluateAssignment : {}", evaluateAssignmentDTO);
        Boolean result = evaluateAssignmentService.createEvaluateAssignment(evaluateAssignmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Sua phan cong
     * @param evaluateAssignmentDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/evaluate-assignments")
    public ResponseEntity<Boolean> updateEvaluateAssignment(@RequestBody EvaluateAssignmentDTO evaluateAssignmentDTO) throws Exception {
        log.debug("REST request to update EvaluateAssignment : {}", evaluateAssignmentDTO);
        if (evaluateAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boolean result = evaluateAssignmentService.updateEvaluateAssignment(evaluateAssignmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Danh sach phan cong
     * @param pageable
     * @return
     */
    @PostMapping("/evaluate-assignments/all")
    public ResponseEntity<List<EvaluateAssignmentDataDTO>> getAllEvaluateAssignments(@RequestBody EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception {
        log.debug("REST request to get a page of EvaluateAssignments");
        Page<EvaluateAssignmentDataDTO> page = evaluateAssignmentService.getAllEvaluateAssignments(evaluateAssignmentDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/evaluate-assignments/search")
    public ResponseEntity<List<EvaluateAssignmentSearchDTO>> onSearch(@RequestBody EvaluateAssignmentDTO evaluateAssignmentDTO, Pageable pageable) throws Exception {
        log.debug("REST request to get a page of EvaluateAssignments");
        User user = userService.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        evaluateAssignmentDTO.setCreateUser(user.getId());
        Page<EvaluateAssignmentSearchDTO> page = evaluateAssignmentService.search(evaluateAssignmentDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Lay chi tiet phan cong
     * @param id
     * @return
     */
    @GetMapping("/evaluate-assignments/{id}")
    public ResponseEntity<EvaluateAssignmentDTO> getEvaluateAssignment(@PathVariable Long id) {
        log.debug("REST request to get EvaluateAssignment : {}", id);
        Optional<EvaluateAssignmentDTO> evaluateAssignmentDTO = evaluateAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluateAssignmentDTO);
    }

    /**
     * Xoa phan cong
     * @param id
     * @return
     */
    @DeleteMapping("/evaluate-assignments/{id}")
    public ResponseEntity<Void> deleteEvaluateAssignment(@PathVariable Long id) {
        log.debug("REST request to delete EvaluateAssignment : {}", id);
        evaluateAssignmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
