package com.fis.crm.web.rest;

import com.fis.crm.service.DashboardService;
import com.fis.crm.service.dto.CricleObj;
import com.fis.crm.service.dto.LineBarObj;
import com.fis.crm.service.dto.SummaryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * REST controller for dashboard.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    public static final int CHART_SUMMARY = 1;
    public static final int CHART_SU_VU_HE_THONG = 2;
    public static final int CHART_YEUCAU_TIEP_NHAN_VA_CHUYEN_PHONG_BAN = 3;
    public static final int CHART_TI_LE_YEU_CAU_THEO_PHONG_BAN = 4;
    public static final int CHART_TI_LE_YEU_CAU_THEO_NGHIEP_VU = 5;
    public static final int CHART_TI_LE_GOI_RA_THEO_NHOM = 6;
    public static final int CHART_DANH_GIA_CHAT_LUONG_GOI_RA = 7;
    public static final int CHART_DANH_GIA_CHAT_LUONG_GOI_VAO = 8;
    public static final int CHART_TONG_KET_EMAIL_MARKETING = 9;
    public static final int CHART_TONG_KET_SMS_MARKETING = 10;
    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private static final String ENTITY_NAME = "dashboard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DashboardService dashboardService;


    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * @param type = loai du lieu
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/summary")
    public ResponseEntity<List<SummaryDTO>> getSummary(@RequestParam String type, @RequestParam String date) {
        log.debug("REST request to get list of dashboard summary");
        List<SummaryDTO> summaryDTOS = dashboardService.getSummary(type, date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/suvu-hethong")
    public ResponseEntity<List<LineBarObj>> getSuVuHeThong(@RequestParam String date) {
        log.debug("REST request to get list of getSuVuHeThong");
        List<LineBarObj> summaryDTOS = dashboardService.getSuVuHeThong(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/received-assigned-dept")
    public ResponseEntity<List<LineBarObj>> getReceivedAssignedDept(@RequestParam String date) {
        log.debug("REST request to get list of getReceivedAssignedDept");
        List<LineBarObj> summaryDTOS = dashboardService.getReceivedAssignedDept(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * Ti le theo yeu cau phong ban
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/dept-percent")
    public ResponseEntity<List<CricleObj>> getDeptPercent(@RequestParam String date) {
        log.debug("REST request to get list of getDeptPercent");
        List<CricleObj> summaryDTOS = dashboardService.getDeptPercent(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }


    /**
     * Ti le theo yeu cau loai nghiep vu
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/business-type-percent")
    public ResponseEntity<List<CricleObj>> getBusinessTypePercent(@RequestParam String date) {
        log.debug("REST request to get list of getBusinessTypePercent");
        List<CricleObj> summaryDTOS = dashboardService.getBusinessTypePercent(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }


    /**
     * Ti le goi ra theo nhom
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/call-out-group")
    public ResponseEntity<List<CricleObj>> getCallOutGroup(@RequestParam String date) {
        log.debug("REST request to get list of getCallOutGroup");
        List<CricleObj> summaryDTOS = dashboardService.getCallOutGroup(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }


    /**
     * Thong ke danh gia chat luong cuoc goi ra
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/callout-evaludate-summary")
    public ResponseEntity<List<LineBarObj>> callOutEvaluateSummary(@RequestParam String date) {
        log.debug("REST request to get list of callOutEvaluateSummary");
        List<LineBarObj> summaryDTOS = dashboardService.callOutEvaluateSummary(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * Thong ke danh gia chat luong cuoc goi vao
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/callin-evaludate-summary")
    public ResponseEntity<List<LineBarObj>> callInEvaluateSummary(@RequestParam String date) {
        log.debug("REST request to get list of callInEvaluateSummary");
        List<LineBarObj> summaryDTOS = dashboardService.callInEvaluateSummary(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * Thong ke gui email marketing
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/email-marketing-summary")
    public ResponseEntity<List<LineBarObj>> emailMarketingSummary(@RequestParam String date) {
        log.debug("REST request to get list of emailMarketingSummary");
        List<LineBarObj> summaryDTOS = dashboardService.emailMarketingSummary(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * Thong ke gui email marketing
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/sms-marketing-summary")
    public ResponseEntity<List<LineBarObj>> smsMarketingSummary(@RequestParam String date) {
        log.debug("REST request to get list of smsMarketingSummary");
        List<LineBarObj> summaryDTOS = dashboardService.smsMarketingSummary(date);
        return new ResponseEntity<>(summaryDTOS, null, HttpStatus.OK);
    }

    /**
     * Dashboard API
     *
     * @param date = yyyyMMdd
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Object>> getDashboard(@PathVariable("id") Integer chartId, @RequestParam String date) {
        log.debug("REST request to get list of getDashboard");
        switch (chartId) {
            case CHART_SUMMARY:
                List<SummaryDTO> summaryDTOS = dashboardService.getSummary(null, date);
                return new ResponseEntity<>(Collections.singletonList(summaryDTOS), null, HttpStatus.OK);
            case CHART_SU_VU_HE_THONG:
                List<LineBarObj> suVuHeThong = dashboardService.getSuVuHeThong(date);
                return new ResponseEntity<>(Collections.singletonList(suVuHeThong), null, HttpStatus.OK);
            case CHART_YEUCAU_TIEP_NHAN_VA_CHUYEN_PHONG_BAN:
                List<LineBarObj> receivedAssignedDept = dashboardService.getReceivedAssignedDept(date);
                return new ResponseEntity<>(Collections.singletonList(receivedAssignedDept), null, HttpStatus.OK);
            case CHART_TI_LE_YEU_CAU_THEO_PHONG_BAN:
                List<CricleObj> deptPercent = dashboardService.getDeptPercent(date);
                return new ResponseEntity<>(Collections.singletonList(deptPercent), null, HttpStatus.OK);
            case CHART_TI_LE_YEU_CAU_THEO_NGHIEP_VU:
                List<CricleObj> businessTypePercent = dashboardService.getBusinessTypePercent(date);
                return new ResponseEntity<>(Collections.singletonList(businessTypePercent), null, HttpStatus.OK);
            case CHART_TI_LE_GOI_RA_THEO_NHOM:
                List<CricleObj> callOutGroup = dashboardService.getCallOutGroup(date);
                return new ResponseEntity<>(Collections.singletonList(callOutGroup), null, HttpStatus.OK);
            case CHART_DANH_GIA_CHAT_LUONG_GOI_RA:
                List<LineBarObj> callOut = dashboardService.callOutEvaluateSummary(date);
                return new ResponseEntity<>(Collections.singletonList(callOut), null, HttpStatus.OK);
            case CHART_DANH_GIA_CHAT_LUONG_GOI_VAO:
                List<LineBarObj> callIn = dashboardService.callInEvaluateSummary(date);
                return new ResponseEntity<>(Collections.singletonList(callIn), null, HttpStatus.OK);
            case CHART_TONG_KET_EMAIL_MARKETING:
                List<LineBarObj> emailMarketing = dashboardService.emailMarketingSummary(date);
                return new ResponseEntity<>(Collections.singletonList(emailMarketing), null, HttpStatus.OK);
            case CHART_TONG_KET_SMS_MARKETING:
                List<LineBarObj> smsMarketingSummary = dashboardService.smsMarketingSummary(date);
                return new ResponseEntity<>(Collections.singletonList(smsMarketingSummary), null, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList(), null, HttpStatus.OK);
    }

}
