package com.fis.crm.web.rest;

import com.fis.crm.service.ConfigAutoEmailService;
import com.fis.crm.service.dto.ConfigAutoEmailDTO;
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
@RequestMapping("/api/config-auto-email")
public class ConfigAutoEmailResource {

    private final Logger log = LoggerFactory.getLogger(ConfigAutoEmailResource.class);

    final
    ConfigAutoEmailService configAutoEmailService;

    public ConfigAutoEmailResource(ConfigAutoEmailService configAutoEmailService) {
        this.configAutoEmailService = configAutoEmailService;
    }

    @GetMapping
    public ResponseEntity<List<ConfigAutoEmailDTO>> getAllOrderByCreateDateDesc(Pageable pageable) {
        log.info("Request to method getAllOrderByCreateDateDesc in ConfigAutoEmailResource");
        Page<ConfigAutoEmailDTO> page = configAutoEmailService.findAllByCreateDateDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping
    ResponseEntity<ConfigAutoEmailDTO> create(@RequestBody ConfigAutoEmailDTO configAutoEmailDTO) {
        log.info("REST request to create in ConfigAutoEmailResource");
        ConfigAutoEmailDTO configAutoEmailDTO1 = configAutoEmailService.save(configAutoEmailDTO);
        return ResponseEntity.ok().body(configAutoEmailDTO1);
    }

    @PutMapping("/{id}")
    ResponseEntity<ConfigAutoEmailDTO> update(@RequestBody ConfigAutoEmailDTO configAutoEmailDTO,
                                              @PathVariable("id") Long id) {
        log.info("REST request to update in ConfigAutoEmailResource");
        configAutoEmailDTO.setId(id);
        ConfigAutoEmailDTO configAutoEmailDTO1 = configAutoEmailService.save(configAutoEmailDTO);
        return ResponseEntity.ok().body(configAutoEmailDTO1);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ConfigAutoEmailDTO> delete(@PathVariable("id") Long id) {
        log.info("REST request to delete in ConfigAutoEmailResource");
        ConfigAutoEmailDTO configAutoEmailDTO = configAutoEmailService.delete(id);
        return ResponseEntity.ok().body(configAutoEmailDTO);
    }
}
