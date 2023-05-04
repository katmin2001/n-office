package com.fis.crm.repository;

import com.fis.crm.domain.CampaignEmailBlackList;
import com.fis.crm.domain.CampaignSMSBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignSMSBlackListRepository extends JpaRepository<CampaignSMSBlackList, Long> {
    CampaignSMSBlackList findCampaignSMSBlackListByPhoneNumberAndStatus(String phoneNumber, String status);

    List<CampaignSMSBlackList> findByPhoneNumber(String phoneNumber);

    List<CampaignSMSBlackList> findByCampaignSMSIdAndStatus(Long campaignSMSId,String status );
}
