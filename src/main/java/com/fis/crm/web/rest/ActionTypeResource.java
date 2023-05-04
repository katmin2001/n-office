package com.fis.crm.web.rest;

import com.fis.crm.service.ActionTypeService;
import com.fis.crm.service.dto.ActionTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActionTypeResource {

    private final Logger log = LoggerFactory.getLogger(ActionTypeResource.class);

    private final ActionTypeService actionTypeService;

    public ActionTypeResource(ActionTypeService actionTypeService) {

        this.actionTypeService = actionTypeService;
    }

    @GetMapping("/action-types")
    public ResponseEntity<List<ActionTypeDTO>> getAllActionTypes() {
        return ResponseEntity.ok().body(actionTypeService.findAll());
    }
}
