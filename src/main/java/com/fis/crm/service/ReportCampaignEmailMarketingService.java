package com.fis.crm.service;

import com.fis.crm.service.dto.ReportCampaignEmailMarketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;

public interface ReportCampaignEmailMarketingService {

    Page<ReportCampaignEmailMarketingDTO> search(String id, String fromDate, String toDate, Pageable pageable);

    ByteArrayInputStream export(String id, String fromDate, String toDate);

}
