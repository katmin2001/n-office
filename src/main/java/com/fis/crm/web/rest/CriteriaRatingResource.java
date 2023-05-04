package com.fis.crm.web.rest;

import com.fis.crm.service.CriteriaRatingService;
import com.fis.crm.service.dto.CriteriaRatingDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CriteriaRatingResource {

    private final Logger log = LoggerFactory.getLogger(CriteriaRatingResource.class);

    private static final String ENTITY_NAME = "criteriaRating";

    private final CriteriaRatingService criteriaRatingService;

    public CriteriaRatingResource(CriteriaRatingService criteriaRatingService) {
        this.criteriaRatingService = criteriaRatingService;
    }

    @PostMapping("/criteriaRatings")
    public ResponseEntity<MessageResponse<CriteriaRatingDTO>> saveCriteriaRating(@RequestBody CriteriaRatingDTO criteriaRatingDTO){
        log.debug("Request to save CriteriaGroup", criteriaRatingDTO);
        if (criteriaRatingDTO.getId() != null) {
            throw new BadRequestAlertException("A new Criteria Rating can not already have an ID", ENTITY_NAME, "idexists");
        }
        CriteriaRatingDTO result;
        String checkName = criteriaRatingDTO.getName().toLowerCase();
        if (!criteriaRatingService.checkName(checkName)){
            return new ResponseEntity<>(new MessageResponse<>("exist", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 1){
            return new ResponseEntity<>(new MessageResponse<>("exist1", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 2){
            return new ResponseEntity<>(new MessageResponse<>("exist2", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 3){
            try {
                result = criteriaRatingService.save(criteriaRatingDTO);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }

//        if (!criteriaRatingService.checkValue(criteriaRatingDTO)){
//            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
//        } else {
//            try {
//                result = criteriaRatingService.save(criteriaRatingDTO);
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
//            }
//            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
//        }
    }

    @PutMapping("/criteriaRatings")
    public ResponseEntity<MessageResponse<CriteriaRatingDTO>> updateCriteriaRating(@RequestBody CriteriaRatingDTO criteriaRatingDTO){
        log.debug("Request to update CriteriaRating", criteriaRatingDTO);
        if (criteriaRatingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CriteriaRatingDTO result;
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 4){
            return new ResponseEntity<>(new MessageResponse<>("exist", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 1){
            return new ResponseEntity<>(new MessageResponse<>("exist1", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 2){
            return new ResponseEntity<>(new MessageResponse<>("exist2", null), HttpStatus.OK);
        }
        if (criteriaRatingService.checkValid(criteriaRatingDTO) == 3){
            try {
                result = criteriaRatingService.save(criteriaRatingDTO);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }
    }


    @PutMapping("/criteriaRatings/delete")
    public ResponseEntity<MessageResponse<CriteriaRatingDTO>> deleteCriteriaRating(@RequestBody CriteriaRatingDTO criteriaRatingDTO){
        log.debug("Request to delete CriteriaRating", criteriaRatingDTO);
        if (criteriaRatingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CriteriaRatingDTO result;
        try {
            result = criteriaRatingService.delete(criteriaRatingDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
    }

    @GetMapping("/criteriaRatings")
    public ResponseEntity<List<CriteriaRatingDTO>> findAllCriteriaRating(Pageable pageable){
        log.debug("Request to get all Criteria Rating");
        Page<CriteriaRatingDTO> page = criteriaRatingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/criteriaRatings/{id}")
    public ResponseEntity<CriteriaRatingDTO> findOneCriteriaRating(@PathVariable("id") Long id) {
        log.debug("Request to get all Criteria Rating");
        Optional<CriteriaRatingDTO> criteriaRatingDTO = criteriaRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criteriaRatingDTO);
    }

    @GetMapping("/criteriaRatings/all")
    public ResponseEntity<List<CriteriaRatingDTO>> findAllCriteriaRating(){
        Optional<List<CriteriaRatingDTO>> result = criteriaRatingService.findAllCriteriaRating();
        return ResponseUtil.wrapOrNotFound(result);
    }
}
