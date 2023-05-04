package com.fis.crm.web.rest;

import com.fis.crm.service.CampaignScriptAnswerService;
import com.fis.crm.service.dto.CampaignScriptAnswerRequestDTO;
import com.fis.crm.service.dto.CampaignScriptAnswerResponseDTO;
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
@RequestMapping("/api/campaign-scripts-questions/{questionId}/answers")
public class CampaignScriptAnswerResource {
    private final Logger log = LoggerFactory.getLogger(CampaignScriptAnswerResource.class);
    private final CampaignScriptAnswerService campaignScriptAnswerService;

    public CampaignScriptAnswerResource(CampaignScriptAnswerService campaignScriptAnswerService) {
        this.campaignScriptAnswerService = campaignScriptAnswerService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse<CampaignScriptAnswerResponseDTO>> saveAnswer(@PathVariable Long questionId,
                                                                                       @Valid @RequestBody CampaignScriptAnswerRequestDTO dto) {
        dto.setCampaignScriptQuestionId(questionId);
        CampaignScriptAnswerResponseDTO result;
        //Bo ky ky  tu dac biet
        if(dto.getContent()!=null) {
            dto.setContent(dto.getContent().replace("<p>", ""));
            dto.setContent(dto.getContent().replace("</p>", ""));
        }

        try {
            result = campaignScriptAnswerService.save(dto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CampaignScriptAnswerResponseDTO>> findAllAnswerByQuestionId(@PathVariable Long questionId,
                                                                                           @PageableDefault Pageable pageable) {
        Page<CampaignScriptAnswerResponseDTO> answersPage = campaignScriptAnswerService.findAllByCampaignScriptQuestionIdPageable(questionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), answersPage);
        return ResponseEntity.ok().headers(headers).body(answersPage.getContent());
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse<String>> deleteAllAnswer(@PathVariable Long questionId) {
        try {
            campaignScriptAnswerService.deleteAllAnswer(questionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse<String>> deleteAnswer(@PathVariable(name = "id") Long id) {
        try {
            campaignScriptAnswerService.deleteAnswer(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse<CampaignScriptAnswerResponseDTO>> updateAnswer(@PathVariable(name = "id") Long id,
                                                                                         @RequestBody CampaignScriptAnswerRequestDTO dto) {
        dto.setId(id);
        CampaignScriptAnswerResponseDTO result = new CampaignScriptAnswerResponseDTO();
        try {
            if( dto.getType() == "4"){
                if (dto.getMin() < 0 || dto.getMax() < 0){
                    return new ResponseEntity<>(new MessageResponse<>("fail", result), HttpStatus.OK);
                }
                if (dto.getMin() >= dto.getMax()){
                    return new ResponseEntity<>(new MessageResponse<>("fail", result), HttpStatus.OK);
                }
            }
            result = campaignScriptAnswerService.updateAnswer(dto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(new MessageResponse<>(e.getMessage(), null));
        }
        return new ResponseEntity<>(new MessageResponse<>("ok", result), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignScriptAnswerResponseDTO> getById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(campaignScriptAnswerService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/getMaxPosition")
    public ResponseEntity<Integer> getMaxPosition(@PathVariable Long questionId){
        Integer maxPosition = campaignScriptAnswerService.genPosition(questionId)-1;
        return new ResponseEntity<>(maxPosition, HttpStatus.OK);
    }
}
