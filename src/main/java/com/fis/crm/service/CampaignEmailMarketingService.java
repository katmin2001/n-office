package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignEmailMarketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CampaignEmailMarketingService {

    Page<CampaignEmailMarketingDTO> search(Long id, String fromDate, String toDate, String status, Pageable pageable);

    CampaignEmailMarketingDTO save(CampaignEmailMarketingDTO dto);

    void delete(Long id);

    List<CampaignEmailMarketingDTO> findAll();

    List<CampaignEmailMarketingDTO> findAllSearch();

    CampaignEmailMarketingDTO findOne(Long id);

    List<CampaignEmailMarketingDTO> findAllOrderByNameASC();

    Map<String, Long> importFile(MultipartFile file, Long id, Long duplicateFilter);
}
