package com.fis.crm.service;

import com.fis.crm.service.dto.ReportSentSMSMarketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;

public interface ReportSentSMSMarketingService {

    Page<ReportSentSMSMarketingDTO> search(String id, String fromDate, String toDate, String phoneNumber, String status, Pageable pageable);

    ByteArrayInputStream export(String id, String fromDate, String toDate, String phoneNumber, String status);

}
