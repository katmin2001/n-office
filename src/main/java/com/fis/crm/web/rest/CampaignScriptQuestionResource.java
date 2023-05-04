package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignScriptQuestionService;
import com.fis.crm.service.dto.CampaignScriptQuestionRequestDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import com.fis.crm.service.dto.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/campaign-scripts/{campaignScriptId}/questions")
public class CampaignScriptQuestionResource {
    private final CampaignScriptQuestionService campaignScriptQuestionService;
    private final Logger log = LoggerFactory.getLogger(CampaignScriptQuestionResource.class);

    public CampaignScriptQuestionResource(CampaignScriptQuestionService campaignScriptQuestionService) {
        this.campaignScriptQuestionService = campaignScriptQuestionService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse<CampaignScriptQuestionResponseDTO>> saveNewQuestion(@PathVariable Long campaignScriptId,
                                                                                              @Valid @RequestBody CampaignScriptQuestionRequestDTO dto){
        log.info("Processing save new question.....");
        log.info(dto.toString());
        dto.setCampaignScriptId(campaignScriptId);
        CampaignScriptQuestionResponseDTO result;
        try {
            result = campaignScriptQuestionService.save(dto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(new MessageResponse<>(e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<CampaignScriptQuestionResponseDTO>> updateQuestion (@PathVariable(name="campaignScriptId") Long campaignScriptId,
                                                                             @PathVariable(name="id") Long id,
                                                                             @RequestBody CampaignScriptQuestionRequestDTO dto){
        log.info("Processing update question.....");
        log.info(dto.toString());
        dto.setCampaignScriptId(campaignScriptId);
        dto.setId(id);
        CampaignScriptQuestionResponseDTO result;
        try {
            result = campaignScriptQuestionService.updateQuestion(dto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CampaignScriptQuestionResponseDTO>> getAllQuestionByCampaignScriptId(@PathVariable Long campaignScriptId, @PageableDefault Pageable pageable) {
        log.info("Get all questions of campaignScriptId " +campaignScriptId +".....");
        Page<CampaignScriptQuestionResponseDTO> questionPages = campaignScriptQuestionService.findAllByCampaignScriptIdPageable(campaignScriptId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), questionPages);
        return ResponseEntity.ok().headers(headers).body(questionPages.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteQuestion(@PathVariable(name ="campaignScriptId") Long campaignScriptId,
                                                 @PathVariable(name ="id") Long id) {
        try {
            campaignScriptQuestionService.deleteQuestion(id, campaignScriptId);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse<String>> deleteAllQuestion(@PathVariable(name ="campaignScriptId") Long campaignScriptId) {
        try {
            campaignScriptQuestionService.deleteAllQuestion(campaignScriptId);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
    }

    @GetMapping("/getMaxPosition")
    public ResponseEntity<Integer> getMaxPosition(@PathVariable Long campaignScriptId){
        Integer maxPosition = campaignScriptQuestionService.genPosition(campaignScriptId)-1;
        return new ResponseEntity<>(maxPosition, HttpStatus.OK);
    }

    @GetMapping("/getId/{content}")
    public ResponseEntity<Integer> getId(@PathVariable Long campaignScriptId, @PathVariable String content){
        Integer idQuestion = campaignScriptQuestionService.getId(campaignScriptId, content);
        return new ResponseEntity<>(idQuestion, HttpStatus.OK);
    }


}
