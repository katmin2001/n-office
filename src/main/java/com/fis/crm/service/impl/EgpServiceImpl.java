package com.fis.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.crm.config.Constants;
import com.fis.crm.service.EgpService;
import com.fis.crm.service.KeycloakService;
import com.fis.crm.service.RestTemplateService;
import com.fis.crm.service.egp.EGPResponse;
import com.fis.crm.service.egp.EgpRequest;
import com.fis.crm.service.egp.request.*;
import com.fis.crm.service.egp.response.*;
import com.fis.crm.web.rest.errors.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EgpServiceImpl implements EgpService {

    private final Logger log = LoggerFactory.getLogger(EgpServiceImpl.class);

    @Value("${egpApi.crm-get-orginfo.url}")
    private String crmGetOrgInfoUrl;
    @Value("${egpApi.crm-get-orgcode.url}")
    private String crmGetOrgCodeUrl;
    @Value("${egpApi.pm-contractor-status.url}")
    private String pmContractorStatus;
    @Value("${egpApi.get-request-register.url}")
    private String getRequestRegister;
    @Value("${egpApi.report-contractor-suspended.url}")
    private String reportContractorSuspended;
    @Value("${egpApi.categoryManagement-categories.url}")
    private String categoryManagementCategories;
    @Value("${egpApi.categoryManagement-categories-bycodelist.url}")
    private String categoriesByCodeList;
    @Value("${egpApi.categoryManagement-area-bycodelist.url}")
    private String areasByCodeList;

    private final RestTemplateService restTemplateService;
    private final KeycloakService keycloakService;
    private final ObjectMapper objectMapper;

    public EgpServiceImpl(
        RestTemplateService restTemplateService,
        KeycloakService keycloakService,
        ObjectMapper objectMapper
    ) {
        this.restTemplateService = restTemplateService;
        this.keycloakService = keycloakService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<EGPResponse<OrgCodeData>> getOrgCode(CrmData crmData) throws Exception {
        EgpRequest<CrmData> requestBody = new EgpRequest<>(crmData);
        Optional<EGPResponse<OrgCodeData>> response = process(requestBody, crmGetOrgCodeUrl, HttpMethod.POST, true);
        return response;
    }

    @Override
    public Optional<EGPResponse<OrgInfoData>> getOrgInfo(CrmData crmData) throws Exception {
        EgpRequest<CrmData> requestBody = new EgpRequest<>(crmData);
        Optional<EGPResponse<OrgInfoData>> response = process(requestBody, crmGetOrgInfoUrl, HttpMethod.POST, true);
        if (response.isPresent()) {
            OrgInfoData orgInfoData = objectMapper.convertValue(response.get().getData(), OrgInfoData.class);
            OrgInfo orgInfo = orgInfoData != null ? orgInfoData.getOrgInfo() : null;
            if (orgInfo == null) return response;
            List<String> areaCodeList = new ArrayList<>();
            if (orgInfo.getOfficeDis() != null && !orgInfo.getOfficeDis().isEmpty())
                areaCodeList.add(orgInfo.getOfficeDis());
            if (orgInfo.getOfficePro() != null && !orgInfo.getOfficePro().isEmpty())
                areaCodeList.add(orgInfo.getOfficePro());
            if (orgInfo.getOfficeWar() != null && !orgInfo.getOfficeWar().isEmpty())
                areaCodeList.add(orgInfo.getOfficeWar());
            process(new EgpRequest<>(new AreaRequest(areaCodeList)), areasByCodeList, HttpMethod.POST, true)
                .ifPresent(areaResponseEGPResponse -> orgInfo.setAreas(objectMapper.convertValue(areaResponseEGPResponse.getData(), AreaResponse.class).getAreas()));

            orgInfoData.setOrgInfo(orgInfo);
            response.get().setData(orgInfoData);
        }
        return response;
    }

    @Override
    public Optional<EGPResponse<CategoryResponse>> getCategories(PagingRequest request) throws Exception {
        EgpRequest<PagingRequest> requestBody = new EgpRequest<>(request);
        Optional<EGPResponse<CategoryResponse>> response = process(requestBody, categoryManagementCategories, HttpMethod.POST, true);
        return response;
    }

    @Override
    public Optional<EGPResponse<List<Category2Response>>> getCategoriesByCodeList(List<CategoryRequest> request) throws Exception {
        EgpRequest<List<CategoryRequest>> requestBody = new EgpRequest<>(request);
        Optional<EGPResponse<List<Category2Response>>> response = process(requestBody, categoriesByCodeList, HttpMethod.POST, true);
        return response;
    }

    @Override
    public Optional<EGPResponse<AreaResponse>> getAreasByCodeList(AreaRequest request) throws Exception {
        EgpRequest<AreaRequest> requestBody = new EgpRequest<>(request);
        Optional<EGPResponse<AreaResponse>> response = process(requestBody, areasByCodeList, HttpMethod.POST, true);
        return response;
    }

    @Override
    public <T, E> Optional<EGPResponse<T>> process(EgpRequest<E> request, String url, HttpMethod httpMethod, boolean retry) throws Exception {
        Long start = System.currentTimeMillis();
        String accessToken = keycloakService.getAccessToken();
        Long end = System.currentTimeMillis();
        log.debug("Get access token: {}", (float) (end - start) / 1000);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(Constants.EGP.AUTHORIZATION, "Bearer " + accessToken);
        ResponseEntity<EGPResponse<T>> response;
        start = System.currentTimeMillis();
        try {
            response = restTemplateService.process(httpMethod, url, httpHeaders, request, new ParameterizedTypeReference<EGPResponse<T>>() {});
        } catch (Exception ex) {
            if (ex instanceof HttpClientErrorException || ex instanceof HttpServerErrorException) {
                if(ex instanceof HttpClientErrorException.Unauthorized || ((HttpStatusCodeException) ex).getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                    log.debug("Remove token expired: {}", accessToken);
                    keycloakService.removeCurrentToken(accessToken);
                    if(retry) {
                        log.debug("===============Retry call ws===============");
                        return process(request, url, httpMethod, false);
                    }
                }
                throw new BusinessException(String.valueOf(HttpStatus.NO_CONTENT.value()), ex.getMessage());
            }
            if(ex instanceof ResourceAccessException){
                throw new BusinessException(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage());
            }
            throw ex;
        }
        end = System.currentTimeMillis();
        log.debug("Get Data EGP: {} s", (float) (end - start) / 1000);
        if (response != null) {
            return Optional.of(response.getBody());
        }
        return Optional.empty();
    }

    @Override
    public Optional<EGPResponse<PmContractorStatusData>> pmContractorStatus(PmContractorStatus pmContractorStatus) throws Exception {
        EgpRequest<PmContractorStatus> requestBody = new EgpRequest<>(pmContractorStatus);
        Optional<EGPResponse<PmContractorStatusData>> response = process(requestBody, this.pmContractorStatus, HttpMethod.POST, true);
        return response;
    }

    @Override
    public Optional<EGPResponse<ReportRegisterData>> getRequestRegister(RequestRegister requestRegister) throws Exception {
        EgpRequest<RequestRegister> requestBody = new EgpRequest<>(requestRegister);
        Optional<EGPResponse<ReportRegisterData>> response = process(requestBody, this.getRequestRegister, HttpMethod.POST, true);
        return response;
    }

    @Override
    public Optional<EGPResponse<ReportContractorSuspendedData>> reportContractorSuspended(ReportContractorSuspended reportContractorSuspended) throws Exception {
        EgpRequest<ReportContractorSuspended> requestBody = new EgpRequest<>(reportContractorSuspended);
        Optional<EGPResponse<ReportContractorSuspendedData>> response = process(requestBody, this.reportContractorSuspended, HttpMethod.POST, true);
        return response;
    }
}
