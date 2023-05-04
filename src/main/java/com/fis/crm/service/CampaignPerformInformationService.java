package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignPerformDTO;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface CampaignPerformInformationService {
    List<CampaignPerformDTO> getDataInformation(String call_time_from,
                                                String call_time_to,
                                                Long campaign_id,
                                                Long campaign_resource_id,
                                                Long assign_user_id);

    List<CampaignPerformDTO> getCallStatus(Long campaign_id);

    List<CampaignPerformDTO> getSurveyStatus(Long campaign_id);

    List<List<CampaignResourceTemplateDTO>> getCallData(String call_time_from,
                                                        String call_time_to,
                                                        Long campaign_id,
                                                        Long campaign_resource_id,
                                                        Long assign_user_id,
                                                        String type,
                                                        String status,
                                                        String saleStatus);

    ByteArrayInputStream exportExcel(CampaignPerformDTO campaignPerformDTO) throws Exception;

}
