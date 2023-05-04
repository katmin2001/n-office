package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignResourceDTO;
import com.fis.crm.service.dto.ComboDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface CampaignResourceService {

    List<CampaignResourceDTO> findAllByStatusAndCampaignId(Long campaign_id);

    List<CampaignResourceDTO> findAll();

    Page<CampaignResourceDTO> search(Long campaignId, String fromDate, String toDate, Pageable pageable);

    List<ComboDTO> getCampaignCombo();

    Long uploadFile(MultipartFile file, Long campaignId);

    Map<String, Long> importFile(MultipartFile file, Long campaignId, Long distinct);

    void changeStatus(Long id, String status);

    ByteArrayInputStream exportCampaignResourceDetail(Long campaignResourceId);

    ByteArrayInputStream exportCampaignResourceError(Long campaignResourceId);

    ByteArrayInputStream getTemplate(Long id);
}
