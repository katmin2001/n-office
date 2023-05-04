package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ReportDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportRenderDetailOutputCallService {
    List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom,
                                                        String callTimeTo,
                                                        Long campaignId,
                                                        String statusCall,
                                                        String assignUserId);

    ByteArrayInputStream exportExcel(ReportDTO dto);
}
