package com.fis.crm.web.rest;

import com.fis.crm.commons.Translator;
import com.fis.crm.service.CriteriaDetailService;
import com.fis.crm.service.CriteriaService;
import com.fis.crm.service.dto.CriteriaDTO;
import com.fis.crm.service.dto.CriteriaDetailDTO;
import com.fis.crm.service.dto.CriteriaScoresDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CriteriaDetailResource {

    private final Logger log = LoggerFactory.getLogger(CriteriaDetailResource.class);

    private static final String ENTITY_NAME = "Criteria Detail";

    private final CriteriaDetailService criteriaDetailService;

    private final CriteriaService criteriaService;

    public CriteriaDetailResource(CriteriaDetailService criteriaDetailService, CriteriaService criteriaService) {
        this.criteriaDetailService = criteriaDetailService;
        this.criteriaService = criteriaService;
    }

    @PostMapping("/criteriaDetails")
    public ResponseEntity<MessageResponse<CriteriaDetailDTO>> saveCriteriaDetail(@RequestBody CriteriaDetailDTO criteriaDetailDTO) {
        log.debug("Request to save Criteria Detail ");
        if (criteriaDetailDTO.getId() != null){
            throw new BadRequestAlertException("A new Criteria Detail no have an ID", ENTITY_NAME, "idexists");
        }
        Optional<CriteriaDTO> criteriaDTO = criteriaService.findOne(criteriaDetailDTO.getCriteriaId());
        if (criteriaDTO.get().getScores() < criteriaDetailDTO.getScores()){
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }
        CriteriaDetailDTO resul;
        if (criteriaDetailService.checkValue(criteriaDetailDTO.getContent())){
            try {
                resul = criteriaDetailService.save(criteriaDetailDTO);
            } catch (Exception e) {
                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse<>("ok", resul), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }
    }

    @PutMapping("/criteriaDetails")
    public ResponseEntity<MessageResponse<CriteriaDetailDTO>> updateCriteriaDetail(@RequestBody CriteriaDetailDTO criteriaDetailDTO) {
        log.debug("Request to update Criteria Detail");
        if (criteriaDetailDTO.getId() == null){
//            throw new BadRequestAlertException("Invalid id ", ENTITY_NAME, "is null");
            return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("criteria-detail.id.null"), null), HttpStatus.BAD_REQUEST);
        }
        Optional<CriteriaDTO> criteriaDTO = criteriaService.findOne(criteriaDetailDTO.getCriteriaId());
        if (criteriaDTO.get().getScores() < criteriaDetailDTO.getScores()){
            return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("criteria-detail.score.not-valid"), null), HttpStatus.BAD_REQUEST);
        }
        CriteriaDetailDTO result;
//        CriteriaDetailDTO criteriaDetailDTO1 = criteriaDetailService.findOneByName(criteriaDetailDTO.getContent());
        Optional<CriteriaDetailDTO> criteriaDetailDTO1 = criteriaDetailService.findOne(criteriaDetailDTO.getId());
        if(!criteriaDetailDTO1.isPresent()){
            return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("criteria-detail.not.exist"),null), HttpStatus.BAD_REQUEST);
        }
        if(!criteriaDetailDTO.getContent().toLowerCase().equals(criteriaDetailDTO1.get().getContent().toLowerCase())){
            CriteriaDetailDTO checkContent = criteriaDetailService.findOneByName(criteriaDetailDTO.getContent());
            if(checkContent!=null){
                return new ResponseEntity<>(new MessageResponse<>(Translator.toLocale("criteria-detail.content.existed"),null), HttpStatus.BAD_REQUEST);
            }
        }
        try {
            result = criteriaDetailService.update(criteriaDetailDTO);
            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

//        if (criteriaDetailDTO.getId() == criteriaDetailDTO1.getId()){
//            try {
//                result = criteriaDetailService.update(criteriaDetailDTO);
//            } catch (Exception e) {
//                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
//            }
//            return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
//        } else {
//            if (criteriaDetailService.checkValue(criteriaDetailDTO.getContent())){
//                try {
//                    result = criteriaDetailService.update(criteriaDetailDTO);
//                } catch (Exception e) {
//                    return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
//                }
//                return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
//            }
//        }
    }

    @PutMapping("/criteriaDetails/delete")
    public ResponseEntity<MessageResponse<CriteriaDetailDTO>> deleteCriteriaDetail(@RequestBody CriteriaDetailDTO criteriaDetailDTO){
        log.debug("Request to delete Criteria Detail");
        if (criteriaDetailDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id ", ENTITY_NAME, "is null");
        }
        CriteriaDetailDTO result;
        try {
            result = criteriaDetailService.delete(criteriaDetailDTO);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse<>("fail", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
    }

    @GetMapping("/criteriaDetails")
    public ResponseEntity<List<CriteriaDetailDTO>> findAllCriteriaDetail(Pageable pageable){
        log.debug("Request to find all Criteria Detail");
        Page<CriteriaDetailDTO> page = criteriaDetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/criteriaDetails/{id}")
    public ResponseEntity<CriteriaDetailDTO> findOneCriteriaDetail(@PathVariable("id") Long id){
        log.debug("Request to find one Criteria Detail");
        Optional<CriteriaDetailDTO> criteriaDetailDTO = criteriaDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criteriaDetailDTO);
    }

    @GetMapping("/criteriaDetails/getScores/{id}")
    public ResponseEntity<CriteriaScoresDTO> getScores(@PathVariable("id") Long id){
        CriteriaScoresDTO list = criteriaDetailService.getScores(id);
        return ResponseEntity.ok().body(list);
    }
}

