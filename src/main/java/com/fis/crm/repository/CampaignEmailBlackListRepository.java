package com.fis.crm.repository;

import com.fis.crm.domain.CampaignEmailBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignEmailBlackListRepository extends JpaRepository<CampaignEmailBlackList, Long> {

    CampaignEmailBlackList findCampaignEmailBlackListByEmailAndStatus(String email, String status);


    List<CampaignEmailBlackList> findByEmail(String email);

    List<CampaignEmailBlackList> findByCampaignEmailId(Long campaignEmailId);
}
