package com.fis.crm.web.rest;

import com.fis.crm.domain.User;
import com.fis.crm.service.CriteriaGroupService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.CriteriaGroupDTO;
import com.fis.crm.service.dto.CriteriaGroupLoadDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CriteriaGroupResource {

    private final Logger log = LoggerFactory.getLogger(CriteriaGroupResource.class);

    private static final String ENTITY_NAME = "criteriaGroup";

    private final CriteriaGroupService criteriaGroupService;

    private final UserService userService;

    public CriteriaGroupResource(CriteriaGroupService criteriaGroupService, UserService userService) {
        this.criteriaGroupService = criteriaGroupService;
        this.userService = userService;
    }

    @GetMapping("/criteriaGroup/loadToCbx")
    public ResponseEntity<List<CriteriaGroupLoadDTO>> getListDepartmentsForCombobox() {
        log.debug("REST request to get list of departments for combobox");
        List<CriteriaGroupLoadDTO> page = criteriaGroupService.loadToCbx();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping("/criteriaGroup")
    public ResponseEntity<MessageResponse<CriteriaGroupDTO>> createCriteriaGroup(@RequestBody CriteriaGroupDTO criteriaGroupDTO) throws URISyntaxException {
        log.debug("Request to save CriteriaGroup", criteriaGroupDTO);
        if (criteriaGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new Criteria Group can not already have an ID", ENTITY_NAME, "idexists");
        }
        Instant instant = Instant.now();
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaGroupDTO.setCreateUser(user.get().getId());
        criteriaGroupDTO.setCreateDatetime(instant);
        criteriaGroupDTO.setUpdateUser(user.get().getId());
        criteriaGroupDTO.setUpdateDatetime(instant);
        criteriaGroupDTO.setStatus("1");
        CriteriaGroupDTO result;
        String checkName = criteriaGroupDTO.getName().toLowerCase();
        if (criteriaGroupService.checkName(checkName)) {
            try {
                result = criteriaGroupService.save(criteriaGroupDTO);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageResponse<>("exist", null), HttpStatus.OK);
        }
    }

    @PutMapping("/criteriaGroup")
    public ResponseEntity<Object> updateCriteriaGroup(@RequestBody CriteriaGroupDTO criteriaGroupDTO) {
        log.debug("Request to update CriteriaGroup", criteriaGroupDTO);
        if (criteriaGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Instant instant = Instant.now();
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaGroupDTO.setUpdateUser(user.get().getId());
        criteriaGroupDTO.setUpdateDatetime(instant);
        criteriaGroupDTO.setStatus("1");
        CriteriaGroupDTO result;
        String checkName = criteriaGroupDTO.getName().toLowerCase();
        Optional<CriteriaGroupDTO> criteriaGroupDTO1 = criteriaGroupService.findOne(criteriaGroupDTO.getId());
        criteriaGroupDTO.setCreateDatetime(criteriaGroupDTO1.get().getCreateDatetime());
        criteriaGroupDTO.setCreateUser(criteriaGroupDTO1.get().getCreateUser());
        if (checkName.equalsIgnoreCase(criteriaGroupDTO1.get().getName())){
            try {
                result = criteriaGroupService.save(criteriaGroupDTO);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
        } else {
            if (criteriaGroupService.checkName(checkName)) {
                try {
                    result = criteriaGroupService.save(criteriaGroupDTO);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
                }
                return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new MessageResponse<>("exist", null), HttpStatus.OK);
            }
        }

    }

    @PutMapping("/criteriaGroup/delete")
    public ResponseEntity<Object> deleteStatusCriteriaGroup(@RequestBody CriteriaGroupDTO criteriaGroupDTO) {
        log.debug("Request to update CriteriaGroup", criteriaGroupDTO);
        if (criteriaGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<CriteriaGroupDTO> criteriaGroup = criteriaGroupService.findOne(criteriaGroupDTO.getId());
        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant();
        Optional<User> user = userService.getUserWithAuthorities();
        criteriaGroupDTO.setCreateUser(criteriaGroup.get().getCreateUser());
        criteriaGroupDTO.setCreateDatetime(criteriaGroup.get().getCreateDatetime());
        criteriaGroupDTO.setUpdateUser(user.get().getId());
        criteriaGroupDTO.setUpdateDatetime(instant);
        criteriaGroupDTO.setStatus("2");
        CriteriaGroupDTO result;
        try {
            result = criteriaGroupService.save(criteriaGroupDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>("fail", null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
    }

    @DeleteMapping("/criteriaGroup/{id}")
    public ResponseEntity<Object> deleteCriteriaGroup(@PathVariable("id") Long id) {
        log.debug("REST request to delete Criteria Group : {}", id);
        criteriaGroupService.delete(id);
        return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
    }

    @GetMapping("/criteriaGroup")
    public ResponseEntity<List<CriteriaGroupDTO>> findAllCriteriaGroup(@RequestParam(name = "search", required = false) String search, Pageable pageable) {
        log.debug("Request to get all Criteria Group");
        Page<CriteriaGroupDTO> page = criteriaGroupService.findAll(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/criteriaGroup/list")
    public ResponseEntity<List<CriteriaGroupDTO>> findAllCriteriaGroupList() {
        log.debug("Request to get all Criteria Group");
        List<CriteriaGroupDTO> list = criteriaGroupService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/criteriaGroup/{id}")
    public ResponseEntity<CriteriaGroupDTO> findOneCriteriaGroup(@PathVariable("id") Long id) {
        log.debug("Request to get all Criteria Group");
        Optional<CriteriaGroupDTO> criteriaGroupDTO = criteriaGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criteriaGroupDTO);
    }

    @GetMapping("/criteriaGroup/detail")
    public ResponseEntity<List<CriteriaGroupDTO>> findCriteriaGroupDetail() {
        Optional<List<CriteriaGroupDTO>> criteriaGroupDTO = criteriaGroupService.findCriteriaGroupDetail();
        return ResponseUtil.wrapOrNotFound(criteriaGroupDTO);
    }

    @PostMapping("/criteriaGroup/getRemainingScore")
    public ResponseEntity<Double> getRemainingScore(@RequestParam("criteriaGroupId") String criteriaGroupId, @RequestParam("criteriaId") String criteriaId) {
        return ResponseEntity.ok().body(criteriaGroupService.getRemainingScore(Long.valueOf(criteriaGroupId), Long.valueOf(criteriaId)));
    }
}
