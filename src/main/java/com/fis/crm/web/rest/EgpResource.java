package com.fis.crm.web.rest;

import com.fis.crm.service.EgpService;
import com.fis.crm.service.egp.EGPResponse;
import com.fis.crm.service.egp.request.AreaRequest;
import com.fis.crm.service.egp.request.CategoryRequest;
import com.fis.crm.service.egp.request.CrmData;
import com.fis.crm.service.egp.request.PagingRequest;
import com.fis.crm.service.egp.request.PmContractorStatus;
import com.fis.crm.service.egp.request.ReportContractorSuspended;
import com.fis.crm.service.egp.request.RequestRegister;
import com.fis.crm.service.egp.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

/**
 * fake api egp
 */
@RestController
@RequestMapping("/api")
public class EgpResource {

    private final Logger log = LoggerFactory.getLogger(EgpResource.class);

    private final EgpService egpService;

    public EgpResource(EgpService egpService) {
        this.egpService = egpService;
    }

    @PostMapping(value = "/egp/crm-get-orgcode")
    public ResponseEntity<EGPResponse<OrgCodeData>> getOrgCode(@RequestBody CrmData crmData) throws Exception {
        log.debug("REST request to getOrgCode : {}", crmData);
        Optional<EGPResponse<OrgCodeData>> result = egpService.getOrgCode(crmData);
        return ResponseUtil.wrapOrNotFound(result);
    }
    @PostMapping(value = "/egp/crm-get-orginfo")
    public ResponseEntity<EGPResponse<OrgInfoData>> getOrgInfo(@RequestBody CrmData crmData) throws Exception {
        log.debug("REST request to getOrgInfo : {}", crmData);
        Optional<EGPResponse<OrgInfoData>> result = egpService.getOrgInfo(crmData);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/crm-get-categories")
    public ResponseEntity<EGPResponse<CategoryResponse>> getCategories(@RequestBody PagingRequest request) throws Exception {
        log.debug("REST request to getCategories : {}", request);
        Optional<EGPResponse<CategoryResponse>> result = egpService.getCategories(request);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/crm-get-categories-by-code-list")
    public ResponseEntity<EGPResponse<List<Category2Response>>> getCategoriesByCodeList(@RequestBody List<CategoryRequest> request) throws Exception {
        log.debug("REST request to getCategoriesByCodeList : {}", request);
        Optional<EGPResponse<List<Category2Response>>> result = egpService.getCategoriesByCodeList(request);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/crm-get-areas-by-code")
    public ResponseEntity<EGPResponse<AreaResponse>> getAreasByCodeList(@RequestBody AreaRequest request) throws Exception {
        log.debug("REST request to getAreasByCodeList : {}", request);
        Optional<EGPResponse<AreaResponse>> result = egpService.getAreasByCodeList(request);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/pm-contractor-status/query")
    public ResponseEntity<EGPResponse<PmContractorStatusData>> pmContractorStatus(@RequestBody PmContractorStatus pmContractorStatus) throws Exception {
        log.debug("REST request to pmContractorStatus : {}", pmContractorStatus);
        Optional<EGPResponse<PmContractorStatusData>> result = egpService.pmContractorStatus(pmContractorStatus);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/get-request-register")
    public ResponseEntity<EGPResponse<ReportRegisterData>> getRequestRegister(@RequestBody RequestRegister requestRegister) throws Exception {
        log.debug("REST request to getRequestRegister : {}", requestRegister);
        Optional<EGPResponse<ReportRegisterData>> result = egpService.getRequestRegister(requestRegister);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @PostMapping(value = "/egp/report-contractor-suspended")
    public ResponseEntity<EGPResponse<ReportContractorSuspendedData>> reportContractorSuspended(@RequestBody ReportContractorSuspended reportContractorSuspended) throws Exception {
        Optional<EGPResponse<ReportContractorSuspendedData>> result = egpService.reportContractorSuspended(reportContractorSuspended);
        return ResponseUtil.wrapOrNotFound(result);
    }

//    @PostMapping(value = "/egp/crm-get-orgcode", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getOrgCode(@RequestBody CrmData crmData) throws Exception {
//        log.debug("REST request to getOrgCode : {}", crmData);
//        String data = "";
//        if("0946783567".equals(crmData.getRecPhone()) || "hientv@fpt.com.vn".equals(crmData.getRecEmail()) ) {
//            data = "{\n" +
//                "  \"responseCode\": \"200\",\n" +
//                "  \"responseMessage\": \"successful\",\n" +
//                "  \"responseEntityMessages\": null,\n" +
//                "  \"body\": {\n" +
//                "    \"crmId\": \"05789467456\",\n" +
//                "    \"egpId\": \"MqBFcYOtsMJz\",\n" +
//                "    \"infos\": [\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}";
//        } else if("0946783568".equals(crmData.getRecPhone()) || "hientv1@fpt.com.vn".equals(crmData.getRecEmail())){
//            data = "{\n" +
//                "  \"responseCode\": \"200\",\n" +
//                "  \"responseMessage\": \"successful\",\n" +
//                "  \"responseEntityMessages\": null,\n" +
//                "  \"body\": {\n" +
//                "    \"crmId\": \"05789467456\",\n" +
//                "    \"egpId\": \"MqBFcYOtsMJz\",\n" +
//                "    \"infos\": [\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0946783568\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109588654\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}";
//        } else {
//            data = "{\n" +
//                "  \"responseCode\": \"200\",\n" +
//                "  \"responseMessage\": \"successful\",\n" +
//                "  \"responseEntityMessages\": null,\n" +
//                "  \"body\": {\n" +
//                "    \"crmId\": \"05789467456\",\n" +
//                "    \"egpId\": \"MqBFcYOtsMJz\",\n" +
//                "    \"infos\": [\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0964878\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109588654\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0946783568\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0314686238\",\n" +
//                "        \"recName\": \"hientv9\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0956567\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109677223\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09567453\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109649346\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"095674\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109649339\",\n" +
//                "        \"recName\": \"095987\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"096784456\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109650084\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"098678\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109652042\",\n" +
//                "        \"recName\": \"OneHitMan-edit2\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"9089068908\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn3002226512\",\n" +
//                "        \"recName\": \"456456\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"093447346\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0314686238\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"095676745\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109642319\",\n" +
//                "        \"recName\": \"096746\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"079658676\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109668807\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09567453\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109642453\",\n" +
//                "        \"recName\": \"hientv9@fpt.com.vn\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09678679\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0314686238\",\n" +
//                "        \"recName\": \"hientv9\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"012384123412341\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0123456789555\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"7896783567\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vnz000000029\",\n" +
//                "        \"recName\": \"hientv9@fpt.com.vn\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0978967\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109650327\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09564345\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109645084\",\n" +
//                "        \"recName\": \"rec-hientv9\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"3582462\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vnz000000028\",\n" +
//                "        \"recName\": \"0672456\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"094564564231\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109660886\",\n" +
//                "        \"recName\": \"OneHitMan-edit1\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0967863\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109669776\",\n" +
//                "        \"recName\": \"0984678\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09567\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109660685\",\n" +
//                "        \"recName\": \"OneHitMan\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09678457\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0314686238\",\n" +
//                "        \"recName\": \"hientv9\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09678678\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109662040\",\n" +
//                "        \"recName\": \"OneHitMan-edit 2\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"09805705\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109682777\",\n" +
//                "        \"recName\": \"097689\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"recPhone\": \"0967867\",\n" +
//                "        \"recEmail\": \"hientv9@fpt.com.vn\",\n" +
//                "        \"orgCode\": \"vn0109660886\",\n" +
//                "        \"recName\": \"hientv9\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}";
//        }
//        return ResponseEntity.ok().body(data);
//    }


//    @PostMapping(value = "/egp/crm-get-orginfo", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getOrgInfo(@RequestBody CrmData crmData) throws Exception {
//        log.debug("REST request to getOrgInfo : {}", crmData);
//        String data = "";
//        if("vn0109588654".equals(crmData.getOrgCode())) {
//            data = "{\n" +
//                "  \"responseCode\": \"200\",\n" +
//                "  \"responseMessage\": \"successful\",\n" +
//                "  \"responseEntityMessages\": null,\n" +
//                "  \"body\": {\n" +
//                "    \"crmId\": \"578946784\",\n" +
//                "    \"egpId\": \"VKRoEpRYLFgr\",\n" +
//                "    \"orgInfo\": {\n" +
//                "      \"orgCode\": \"vn0107287757\",\n" +
//                "      \"taxCode\": \"0107287757\",\n" +
//                "      \"orgFullName\": \"CÔNG TY TNHH DỊCH VỤ KỸ THUẬT TỔNG HỢP NGA TÙNG\",\n" +
//                "      \"officePro\": \"81\",\n" +
//                "      \"officeDis\": \"1036\",\n" +
//                "      \"officeWar\": \"12077\",\n" +
//                "      \"officeAdd\": \"Số 8, ngõ 1, ngách 1/34 phố Phan Đình Giót\",\n" +
//                "      \"feeDebtInfos\": [\n" +
//                "        {\n" +
//                "          \"feeType\": 1,\n" +
//                "          \"status\": 0,\n" +
//                "          \"paymentExpDate\": \"2021-06-22T09:56:08Z\",\n" +
//                "          \"feeTotal\": 550000\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"caInfos\": [\n" +
//                "        {\n" +
//                "          \"userName\": \"vn0107287757-01\",\n" +
//                "          \"fullName\": \"daâ\",\n" +
//                "          \"status\": 2,\n" +
//                "          \"caExpDate\": \"2021-12-31T14:09:27Z\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"userName\": \"vn0107287757\",\n" +
//                "          \"fullName\": \"da reczzz\",\n" +
//                "          \"status\": 2,\n" +
//                "          \"caExpDate\": \"2021-06-23T09:07:16Z\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"userName\": \"vn0107287757-02\",\n" +
//                "          \"fullName\": \"daaaa\",\n" +
//                "          \"status\": 0,\n" +
//                "          \"caExpDate\": \"2023-06-25T08:37:36Z\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"userName\": \"vn0107287757-04\",\n" +
//                "          \"fullName\": \"da\",\n" +
//                "          \"status\": 1,\n" +
//                "          \"caExpDate\": \"2023-06-30T03:18:36Z\"\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";
//        }
//        return ResponseEntity.ok().body(data);
//    }
}
