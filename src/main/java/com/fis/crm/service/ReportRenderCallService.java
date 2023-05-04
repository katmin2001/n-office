package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ReportDTO;
import java.io.ByteArrayInputStream;

import java.util.List;

public interface ReportRenderCallService {
    List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom,
                                                        String callTimeTo,
                                                        Long campaignId,
                                                        String saleStatus,
                                                        String assignUser);

    ByteArrayInputStream exportExcel(ReportDTO dto);

}
