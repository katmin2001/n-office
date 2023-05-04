package com.fis.crm.web.rest;

import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.User;
import com.fis.crm.security.SecurityUtils;
import com.fis.crm.service.CampaignPerformInformationService;
import com.fis.crm.service.CampaignService;
import com.fis.crm.service.dto.CampaignDTO;
import com.fis.crm.service.dto.CampaignPerformDTO;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaign-perform-information")
public class CampaignPerformInformationResource {

    @Autowired
    CampaignPerformInformationService campaignPerformInformationService;

    @Autowired
    CampaignService campaignService;

    @GetMapping("/data-information")
    public ResponseEntity<List<CampaignPerformDTO>> getDataInformation(@RequestParam(value = "call_time_from") String call_time_from,
                                                                       @RequestParam(value = "call_time_to") String call_time_to,
                                                                       @RequestParam(value = "campaign_id") Long campaign_id,
                                                                       @RequestParam(value = "campaign_resource_id", required = false) Long campaign_resource_id,
                                                                       @RequestParam(value = "assign_user_id", required = false) Long assign_user_id) {
        try {

            List<CampaignPerformDTO> object = campaignPerformInformationService.getDataInformation(call_time_from,
                call_time_to,
                campaign_id,
                campaign_resource_id,
                assign_user_id);
            return ResponseEntity.ok().body(object);
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @GetMapping("/call-status")
    public ResponseEntity<List<CampaignPerformDTO>> getCallStatus(@RequestParam(value = "campaign_id") Long campaign_id) {
        try {
            List<CampaignPerformDTO> campaignPerformDTOS = campaignPerformInformationService.getCallStatus(campaign_id);
            return ResponseEntity.ok().body(campaignPerformDTOS);
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @GetMapping("/survey-status")
    public ResponseEntity<List<CampaignPerformDTO>> getSurveyStatus(@RequestParam(value = "campaign_id") Long campaign_id) {
        try {
            List<CampaignPerformDTO> campaignPerformDTOS = campaignPerformInformationService.getSurveyStatus(campaign_id);
            return ResponseEntity.ok().body(campaignPerformDTOS);
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }

    @PostMapping("/export")
    public ResponseEntity<?> exportExcel(@RequestBody CampaignPerformDTO campaignPerformDTO) {
        try {
//            String campaign="";
//            Optional<CampaignDTO> c=campaignService.findOne(campaignPerformDTO.getCampaign_id());
//            if(c.isPresent())
//            {
//                CampaignDTO cd=c.get();
//                campaign=cd.getName();
//            }

            ByteArrayInputStream byteArrayInputStream =campaignPerformInformationService.exportExcel(campaignPerformDTO);
            Resource resource = new InputStreamResource(byteArrayInputStream);
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhmm");
            String fileName = "Danh_sach_du_lieu_goi_ra-"+campaignPerformDTO.getCampaign_id()+"-" + format.format(new Date());
            return ResponseEntity.ok().header("filename", fileName).body(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/next-phone-number")
    public ResponseEntity<?> getTheNextPhoneNumber(@RequestParam(value = "call_time_from") String call_time_from,
                                                   @RequestParam(value = "call_time_to") String call_time_to,
                                                   @RequestParam(value = "campaign_id") Long campaign_id,
                                                   @RequestParam(value = "campaign_resource_id", required = false) Long campaign_resource_id,
                                                   @RequestParam(value = "assign_user_id", required = false) Long assign_user_id,
                                                   @RequestParam(value = "type", required = false) String type,
                                                   @RequestParam(value = "status", required = false) String status,
                                                   @RequestParam(value = "saleStatus", required = false) String saleStatus) {
        try {
            return ResponseEntity.ok(campaignPerformInformationService.getCallData(
                call_time_from,
                call_time_to,
                campaign_id,
                campaign_resource_id,
                assign_user_id,
                type,
                status,
                saleStatus));
        } catch (Exception e) {
            throw new BadRequestAlertException(e.getMessage(), "", "");
        }
    }
}
