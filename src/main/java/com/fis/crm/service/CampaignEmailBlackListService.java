package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignEmailBlackListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CampaignEmailBlackListService {

    Page<CampaignEmailBlackListDTO> findCampaignEmailBlackList(Long campaignEmailId,
                                                               String email,
                                                               String fullName,
                                                               Pageable pageable);

    CampaignEmailBlackListDTO save(CampaignEmailBlackListDTO campaignEmailBlackListDTO);

    CampaignEmailBlackListDTO delete(Long id);

    Boolean isExistEmail(String email);

    Boolean isExistEmail(Long campaignId,String email);

    CampaignEmailBlackListDTO importFile(MultipartFile file, Long campaignEmailId);

    List<CampaignEmailBlackListDTO> findAllByCampaignEmailIdAndEmailLikeAndFullNameLike(Long campaignEmailId, String email, String fullName);
}
