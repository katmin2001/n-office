package com.fis.crm.web.rest;

import com.fis.crm.service.CallInfoService;
import com.fis.crm.service.dto.CallInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/getCallInfo")
public class CallInfoResource {

    private final Logger log = LoggerFactory.getLogger(CallInfoResource.class);

    private final CallInfoService callInfoService;

    public CallInfoResource(CallInfoService callInfoService) {
        this.callInfoService = callInfoService;
    }

    @PostMapping("")
    public ResponseEntity<?> updateTicket(@RequestBody CallInfoDTO callInfoDTO) throws Exception {
        log.debug("REST request to save TestDTO : {}", callInfoDTO);
        System.out.println("REST request to save TestDTO : " + callInfoDTO.toString());
        return ResponseEntity.ok().body(callInfoService.create(callInfoDTO));
    }
}
