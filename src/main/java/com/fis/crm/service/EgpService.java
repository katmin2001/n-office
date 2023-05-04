package com.fis.crm.service;

import com.fis.crm.service.egp.EGPResponse;
import com.fis.crm.service.egp.EgpRequest;
import com.fis.crm.service.egp.request.AreaRequest;
import com.fis.crm.service.egp.request.CategoryRequest;
import com.fis.crm.service.egp.request.CrmData;
import com.fis.crm.service.egp.request.PagingRequest;
import com.fis.crm.service.egp.request.PmContractorStatus;
import com.fis.crm.service.egp.request.ReportContractorSuspended;
import com.fis.crm.service.egp.request.RequestRegister;
import com.fis.crm.service.egp.response.*;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Optional;

public interface EgpService {

    Optional<EGPResponse<OrgCodeData>> getOrgCode(CrmData crmData) throws Exception;

    Optional<EGPResponse<OrgInfoData>> getOrgInfo(CrmData crmData) throws Exception;

    Optional<EGPResponse<CategoryResponse>> getCategories(PagingRequest request) throws Exception;

    Optional<EGPResponse<List<Category2Response>>> getCategoriesByCodeList(List<CategoryRequest> request) throws Exception;

    Optional<EGPResponse<AreaResponse>> getAreasByCodeList(AreaRequest request) throws Exception;

    <T, E> Optional<EGPResponse<T>> process(EgpRequest<E> request, String url, HttpMethod httpMethod, boolean retry) throws Exception;

    Optional<EGPResponse<PmContractorStatusData>> pmContractorStatus(PmContractorStatus pmContractorStatus) throws Exception;

    Optional<EGPResponse<ReportRegisterData>> getRequestRegister(RequestRegister requestRegister) throws Exception;

    Optional<EGPResponse<ReportContractorSuspendedData>>  reportContractorSuspended(ReportContractorSuspended reportRegister) throws Exception;
}
