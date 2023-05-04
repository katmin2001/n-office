package com.fis.crm.service;

import com.fis.crm.service.dto.ReportProductivityCalloutCampaignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;

public interface ReportProductivityCalloutCampaignService {

    Page<ReportProductivityCalloutCampaignDTO> search(String id, String operator, String fromDate, String toDate, Pageable pageable);

    ByteArrayInputStream export(String id, String operator, String fromDate, String toDate);

}
