package com.fis.crm.repository;

import com.fis.crm.domain.CampaignScriptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignScriptAnswerRepository extends JpaRepository<CampaignScriptAnswer, Long> {
    @Query("select a from CampaignScriptAnswer a join fetch a.question q where q.id=:campaignScriptQuestionId")
    List<CampaignScriptAnswer> findByCampaignScriptQuestionId(@Param("campaignScriptQuestionId") Long campaignScriptQuestionId);

    @Query("select a from CampaignScriptAnswer a join fetch a.question q where q.id=:campaignScriptQuestionId and a.position=:position")
    List<CampaignScriptAnswer> findByCampaignScriptQuestionIdAndPosition(@Param("campaignScriptQuestionId") Long campaignScriptQuestionId, @Param("position") Integer position);

    @Query("select a from CampaignScriptAnswer a join fetch a.question q where q.id=:campaignScriptQuestionId and a.code=:code")
    List<CampaignScriptAnswer> findByCodeAndCampaignScriptQuestionId(@Param("code")String code, @Param("campaignScriptQuestionId") Long campaignScriptQuestionId);
}
