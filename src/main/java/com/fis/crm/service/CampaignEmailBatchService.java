package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignEmailBatchDTO;

import java.util.List;

public interface CampaignEmailBatchService {
    void saveCampaignEmailBatch(List<CampaignEmailBatchDTO> campaignEmailBatchDTOS) throws Exception;

    void saveEmailOne(CampaignEmailBatchDTO campaignEmailBatchDTO);

    void update(List<CampaignEmailBatchDTO> campaignEmailBatchDTOS);

}
