package com.fis.crm.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface RestTemplateService {

    <T> ResponseEntity process(HttpMethod httpMethod, String url, HttpHeaders httpheaders, Object requestBody, Class<T> responseType);

    <T> ResponseEntity process(HttpMethod httpMethod, String url, HttpHeaders httpheaders, Object requestBody, ParameterizedTypeReference<T> responseType) throws Exception;
}
