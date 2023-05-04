package com.fis.crm.web.rest;

import com.fis.crm.service.ConfigAutoSMSService;
import com.fis.crm.service.dto.ConfigAutoSMSDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("/api/config-auto-sms")
public class ConfigAutoSMSResource {

    private final Logger log = LoggerFactory.getLogger(ConfigAutoSMSResource.class);

    final
    ConfigAutoSMSService configAutoSMSService;

    public ConfigAutoSMSResource(ConfigAutoSMSService configAutoSMSService) {
        this.configAutoSMSService = configAutoSMSService;
    }

    @GetMapping
    public ResponseEntity<List<ConfigAutoSMSDTO>> getAllOrderByCreateDateDesc(Pageable pageable) {
        log.info("Request to method getAllOrderByCreateDateDesc in ConfigAutoEmailResource");
        Page<ConfigAutoSMSDTO> page = configAutoSMSService.findAllByCreateDateDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping
    ResponseEntity<ConfigAutoSMSDTO> create(@RequestBody ConfigAutoSMSDTO configAutoSMSDTO) {
        log.info("REST request to create in ConfigAutoEmailResource");
        ConfigAutoSMSDTO configAutoSMSDTO1 = configAutoSMSService.save(configAutoSMSDTO);
        return ResponseEntity.ok().body(configAutoSMSDTO1);
    }

    @PutMapping("/{id}")
    ResponseEntity<ConfigAutoSMSDTO> update(@RequestBody ConfigAutoSMSDTO configAutoSMSDTO,
                                            @PathVariable("id") Long id) {
        log.info("REST request to update in ConfigAutoEmailResource");
        configAutoSMSDTO.setId(id);
        ConfigAutoSMSDTO configAutoSMSDTO1 = configAutoSMSService.save(configAutoSMSDTO);
        return ResponseEntity.ok().body(configAutoSMSDTO1);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ConfigAutoSMSDTO> delete(@PathVariable("id") Long id) {
        log.info("REST request to delete in ConfigAutoEmailResource");
        ConfigAutoSMSDTO configAutoSMSDTO = configAutoSMSService.delete(id);
        return ResponseEntity.ok().body(configAutoSMSDTO);
    }
}
