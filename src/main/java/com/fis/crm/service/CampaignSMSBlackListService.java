package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignEmailBlackListDTO;
import com.fis.crm.service.dto.CampaignSMSBlackListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CampaignSMSBlackListService {
    Page<CampaignSMSBlackListDTO> findCampaignSMSBlackList(Long campaignSMSId,
                                                             String phoneNumber,
                                                             String fullName,
                                                             Pageable pageable);

    CampaignSMSBlackListDTO save(CampaignSMSBlackListDTO campaignSMSBlackListDTO);

    CampaignSMSBlackListDTO delete(Long id);

    Boolean isExistPhoneNumber(String phoneNumber);

    Boolean isExistPhoneNumber(Long campaignId,String phoneNumber);

    CampaignSMSBlackListDTO importFile(MultipartFile file, Long campaignSmsId);

    List<CampaignSMSBlackListDTO> findAllByCampaignSMSIdAndPhoneNumberLikeAndFullNameLike(Long campaignSMSId, String phoneNumber, String fullName);

}
