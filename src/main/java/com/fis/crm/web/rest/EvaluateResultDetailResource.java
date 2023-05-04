package com.fis.crm.web.rest;

import com.fis.crm.domain.EvaluateResultDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link EvaluateResultDetail}.
 */
@RestController
@RequestMapping("/api")
public class EvaluateResultDetailResource {

    private final Logger log = LoggerFactory.getLogger(EvaluateResultDetailResource.class);

    private static final String ENTITY_NAME = "evaluateResultDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    public EvaluateResultDetailResource() {
    }
}
