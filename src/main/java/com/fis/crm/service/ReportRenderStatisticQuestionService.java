package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ReportDTO;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public interface ReportRenderStatisticQuestionService {
    List<ReportDTO> getData(String callTimeFrom,
                            String callTimeTo,
                            Long campaignId,
                            Long questionId);

    ByteArrayInputStream exportExcel(ReportDTO dto);
}
