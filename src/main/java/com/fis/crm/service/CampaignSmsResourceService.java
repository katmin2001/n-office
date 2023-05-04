package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignSMSResourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampaignSmsResourceService {
    Page<CampaignSMSResourceDTO> search(Long campaignSmsMarketingId, String status, String fromDate,String toDate, Pageable pageable);

    void changeStatus(Long id);
}
