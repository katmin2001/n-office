package com.fis.crm.repository;

import com.fis.crm.domain.CampaignScriptQuestion;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignScriptQuestionRepository extends JpaRepository<CampaignScriptQuestion, Long> {
    List<CampaignScriptQuestion> findByCampaignScriptId(Long campaignScriptId);
    Integer findByCampaignScriptIdAndAndContent(Long campaignScriptId, String content);
    List<CampaignScriptQuestion> findByPositionAndCampaignScriptId(Integer position, Long campaignScriptId);
    List<CampaignScriptQuestion> findByCodeAndCampaignScriptId(String code, Long campaignScriptId);
//    List<CampaignScriptQuestion> findByCampaignScriptIdAndOrderByCreateDatetimeDesc(Long campaignScriptId);

    //find Questionlist of campaign
    @Query(value = "select *\n" +
        "from campaign_script_question\n" +
        "where CAMPAIGN_SCRIPT_ID in (select c.CAMPAIGN_SCRIPT_ID from campaign c where c.id = :campaignId)\n" +
        "  and status = 1", nativeQuery = true)
    List<CampaignScriptQuestion> findAllQuestionByCampaignId(@Param("campaignId") Long campaignId);
}
