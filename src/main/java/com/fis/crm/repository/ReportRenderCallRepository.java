package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;

import java.util.List;

public interface ReportRenderCallRepository {
    List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom,
                                                        String callTimeTo,
                                                        Long campaignId,
                                                        String saleStatus,
                                                        String assignUser);

    ListExcelDynamicGroupDTO exportExcel(String callTimeFrom,
                                         String callTimeTo,
                                         Long campaignId,
                                         String statusCall,
                                         String assignUser);
}
