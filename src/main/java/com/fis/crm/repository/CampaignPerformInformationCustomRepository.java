package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignPerformDTO;
import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;

import java.util.List;

public interface CampaignPerformInformationCustomRepository {
    List<CampaignPerformDTO> getDataInformation(String call_time_from,
                                                String call_time_to,
                                                Long campaign_id,
                                                Long campaign_resource_id,
                                                Long assign_user_id);

    List<CampaignPerformDTO> getCallStatus(Long campaign_id, String code);

    List<List<CampaignResourceTemplateDTO>> getCallData(String call_time_from,
                                                        String call_time_to,
                                                        Long campaign_id,
                                                        Long campaign_resource_id,
                                                        Long assign_user_id,
                                                        String type,
                                                        String status,
                                                        String saleStatus);

    ListExcelDynamicGroupDTO exportExcel(String call_time_from,
                                         String call_time_to,
                                         Long campaign_id,
                                         Long campaign_resource_id,
                                         Long assign_user_id,
                                         String type,
                                         String status,
                                         String saleStatus);
}
