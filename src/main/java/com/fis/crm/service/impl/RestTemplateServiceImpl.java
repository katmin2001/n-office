package com.fis.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.crm.service.RestTemplateService;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateServiceImpl implements RestTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
    private static final String REQUEST = "REST REQUEST: URL[{}], HTTP-METHOD[{}], HTTP-HEADERS[{}], REQUEST-BODY[{}]";
    private static final String RESPONSE = "REST RESPONSE: STATUS[{}], MESSAGE[{}], BODY[{}]";
    private final RestTemplate restTemplate;
    final ObjectMapper mapper;

    public RestTemplateServiceImpl(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    @Override
    public <T> ResponseEntity process(HttpMethod httpMethod, String url,
                                      HttpHeaders httpHeaders,
                                      Object requestBody, Class<T> responseType) {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
        }
        logger.debug(REQUEST, url, httpMethod, httpHeaders, requestBody);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<>(requestBody, httpHeaders), responseType);
            HttpStatus httpStatus = responseEntity.getStatusCode();
            logger.debug(RESPONSE, httpStatus.value(), httpStatus.getReasonPhrase(), responseEntity.getBody());
            return responseEntity;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error(ex.getMessage(), ex);
            throw new BusinessException(String.valueOf(HttpStatus.NO_CONTENT.value()), ex.getMessage());
        } catch (ResourceAccessException ex) {
            logger.error(ex.getMessage(), ex);
            logger.debug("Connection timed out!");
            throw new BusinessException(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
        }
    }

    @Override
    public <T> ResponseEntity process(HttpMethod httpMethod, String url, HttpHeaders httpHeaders, Object requestBody, ParameterizedTypeReference<T> responseType) throws Exception {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
        }
        String jsonString = mapper.writeValueAsString(requestBody);
        logger.debug(REQUEST, url, httpMethod, httpHeaders, jsonString);
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<>(requestBody, httpHeaders), responseType);
            HttpStatus httpStatus = responseEntity.getStatusCode();
            logger.debug(RESPONSE, httpStatus.value(), httpStatus.getReasonPhrase(), responseEntity.getBody());
            return responseEntity;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (ResourceAccessException ex) {
            logger.debug("Connection timed out!");
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
