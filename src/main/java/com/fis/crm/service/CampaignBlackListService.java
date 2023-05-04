package com.fis.crm.service;

import com.fis.crm.domain.CampaignBlacklist;
import com.fis.crm.service.dto.CampaignBlackListDTO;
import io.undertow.util.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampaignBlackListService {
    Page<CampaignBlackListDTO> findCampaignBlackList(Long campaignId, String phoneNumber, Pageable pageable);

    void save(CampaignBlackListDTO campaignBlackListDTO) throws Exception;

    CampaignBlacklist delete(Long id);

    List<CampaignBlackListDTO> findAll();

    void deleteAll(List<CampaignBlackListDTO> campaignBlackListDTOS);
}
