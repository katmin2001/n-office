package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignEmailResourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

public interface CampaignEmailResourceService {
    Page<CampaignEmailResourceDTO> search(Long campaignEmailMarketingId,
                                          String status,
                                          String fromDate,
                                          String toDate,
                                          Pageable pageable);

    void changeStatus(Long id);
}
