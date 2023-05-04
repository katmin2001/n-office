package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignSMSMarketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CampaignSMSMarketingService {

    Page<CampaignSMSMarketingDTO> search(Long id, String startDate, String endDate, String status, Pageable pageable);

    List<CampaignSMSMarketingDTO> findAll();

    List<CampaignSMSMarketingDTO> findAllSearch();

    CampaignSMSMarketingDTO save(CampaignSMSMarketingDTO dto);

    CampaignSMSMarketingDTO findOne(Long id);

    void delete(Long id);

    Map<String, Long> importFile(MultipartFile file, Long id, Long duplicateFilter);

    List<CampaignSMSMarketingDTO> findAllOrderByNameASC();
}
