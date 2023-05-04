package com.fis.crm.repository;

import com.fis.crm.service.dto.CampaignResourceTemplateDTO;
import com.fis.crm.service.dto.ListExcelDynamicGroupDTO;
import com.fis.crm.service.dto.ReportDTO;

import java.util.Date;
import java.util.List;

public interface ReportRenderStatisticQuestionRepository {
    List<ReportDTO> getData(String callTimeFrom,
                            String callTimeTo,
                            Long campaignId,
                            Long questionId);

    ListExcelDynamicGroupDTO exportExcel(String callTimeFrom,
                                         String callTimeTo,
                                         Long campaignId,
                                         Long questionId);
}
