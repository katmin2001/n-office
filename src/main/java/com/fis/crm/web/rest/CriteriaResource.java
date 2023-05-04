package com.fis.crm.web.rest;

import com.fis.crm.service.CriteriaService;
import com.fis.crm.service.dto.CriteriaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private static final String ENTITY_NAME = "campaignResource";

    private final CriteriaService criteriaService;


    public CriteriaResource(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }


    @GetMapping("/criteria/list")
    public ResponseEntity<List<CriteriaDTO>> getListCriteria(){
        List<CriteriaDTO> criteriaDTOS = criteriaService.listCriteria();
        return ResponseEntity.ok().body(criteriaDTOS);
    }

    @GetMapping("/criteria/criteriaGroup/{id}")
    public ResponseEntity<List<CriteriaDTO>> getListCriteriaByCriteriaGroup(@PathVariable("id") Long id){
        List<CriteriaDTO> criteriaDTOS = criteriaService.getListCriteriaByCriteriaGroupId(id);
        return ResponseEntity.ok().body(criteriaDTOS);
    }

    @GetMapping("/criteria/{id}")
    public ResponseEntity<Optional<CriteriaDTO>> getOneCriteria(@PathVariable("id") Long id){
        Optional<CriteriaDTO> criteriaDTOS = criteriaService.findOne(id);
        return ResponseEntity.ok().body(criteriaDTOS);
    }

    @PostMapping("criteria/search")
    public ResponseEntity<List<CriteriaDTO>> onSearch(@RequestParam(value = "status", required = false) String status,
                                                      @RequestParam(value = "criteriaGroupId", required = false) String criteriaGroupId,
                                                      @RequestParam(value = "keyWord", required = false) String keyWord,
                                                      Pageable pageable) {
        Page<CriteriaDTO> page = criteriaService.search(status, Long.valueOf(criteriaGroupId), keyWord, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("criteria/save")
    public ResponseEntity<CriteriaDTO> onSave(@RequestBody CriteriaDTO dto) {
        return ResponseEntity.ok().body(criteriaService.save(dto));
    }

    @PostMapping("criteria/change-status")
    public ResponseEntity<CriteriaDTO> onChangeStatus(@RequestParam(value = "id") String id, @RequestParam(value = "isDelete") String isDelete) {
        return ResponseEntity.ok().body(criteriaService.changeStatus(Long.valueOf(id), Boolean.valueOf(isDelete)));
    }
}
