package com.fis.crm.repository;

import com.fis.crm.domain.CampaignResourceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignResourceTemplateRepository extends JpaRepository<CampaignResourceTemplate, Long> {

    @Query(value = "select type, code, name, ord, value, editable\n" +
        "from CAMPAIGN_RESOURCE_TEMPLATE\n" +
        "where CAMPAIGN_ID = :campaignId\n" +
        "  and CAMPAIGN_RESOURCE_ID = :campaignResourceId\n" +
        "  and CAMPAIGN_RESOURCE_DETAIL_ID = :campaignResourceDetailId\n" +
        "  and CODE in (:listCode)\n" +
        "order by id", nativeQuery = true)
    List<Object[]> findTemplateDynamic(@Param("campaignId") Long campaignId, @Param("campaignResourceId") Long campaignResourceId, @Param("campaignResourceDetailId") Long campaignResourceDetailId, @Param("listCode") List<String> listCode);

}
