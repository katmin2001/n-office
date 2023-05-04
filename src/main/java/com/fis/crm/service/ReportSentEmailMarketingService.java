package com.fis.crm.service;

import com.fis.crm.service.dto.ReportSentEmailMarketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;

public interface ReportSentEmailMarketingService {

    Page<ReportSentEmailMarketingDTO> search(String id, String fromDate, String toDate, String email, String status, Pageable pageable);

    ByteArrayInputStream export(String id, String fromDate, String toDate, String email, String status);

}
