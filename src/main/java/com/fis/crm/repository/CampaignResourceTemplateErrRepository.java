package com.fis.crm.repository;

import com.fis.crm.domain.CampaignResourceTemplateErr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignResourceTemplateErrRepository extends JpaRepository<CampaignResourceTemplateErr, Long> {

    @Query(value = "select type, code, name, ord, value, editable\n" +
        "from CAMPAIGN_RESOURCE_TEMPLATE_ERR\n" +
        "where CAMPAIGN_ID = :campaignId\n" +
        "  and CAMPAIGN_RESOURCE_ID = :campaignResourceId\n" +
        "  and CAMPAIGN_RESOURCE_DETAIL_ID = :campaignResourceDetailId\n" +
        "  and CODE in (:listCode)\n" +
        "order by id", nativeQuery = true)
    List<Object[]> findTemplateDynamic(@Param("campaignId") Long campaignId, @Param("campaignResourceId") Long campaignResourceId, @Param("campaignResourceDetailId") Long campaignResourceDetailId, @Param("listCode") List<String> listCode);

}
