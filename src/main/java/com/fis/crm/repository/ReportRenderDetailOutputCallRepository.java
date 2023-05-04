package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;

import java.util.List;

public interface ReportRenderDetailOutputCallRepository {
    List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom,
                                                        String callTimeTo,
                                                        Long campaignId,
                                                        String statusCall,
                                                        String assignUserId);

    ListExcelDynamicGroupDTO exportExcel(String callTimeFrom,
                                         String callTimeTo,
                                         Long campaignId,
                                         String statusCall,
                                         Long assignUserId);
}
