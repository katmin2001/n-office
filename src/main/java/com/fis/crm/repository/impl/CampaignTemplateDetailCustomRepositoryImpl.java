package com.fis.crm.repository.impl;

import com.fis.crm.commons.MaperUtils;
import com.fis.crm.repository.CampaignTemplateDetailCustomRepository;
import com.fis.crm.service.dto.CampaignTemplateDetailDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CampaignTemplateDetailCustomRepositoryImpl implements CampaignTemplateDetailCustomRepository {
    private final EntityManager entityManager;
    private final String FIND_BY_CAMPAIGN_ID = "SELECT C.ID,\n" +
        "       C.NAME,\n" +
        "       C.CODE,\n" +
        "       C.ORD,\n" +
        "       C.OPTION_VALUE,\n" +
        "       C.DEFAULT_VALUE,\n" +
        "       AD.NAME AS TYPENAME,\n" +
        "       C.EXPORT_EXCEL,\n" +
        "       C.DISPLAY,\n" +
        "       C.EDITABLE,\n" +
        "       CT.CAMPAIGN_NAME,\n" +
        "       C.IS_DEFAULT,\n" +
        "       C.EDIT_TEMPLATE,\n" +
        "       C.TYPE\n" +
        "FROM CAMPAIGN_TEMPLATE_DETAIL C\n" +
        "         LEFT JOIN CAMPAIGN_TEMPLATE CT ON CT.ID = C.CAMPAIGN_ID\n" +
        "         LEFT JOIN AP_DOMAIN AD on C.TYPE = AD.ID\n" +
        "WHERE C.STATUS = 1 AND C.CAMPAIGN_ID=:ID ORDER BY C.ORD ASC\n";

    public CampaignTemplateDetailCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CampaignTemplateDetailDTO> findAllByCampaignId(Long id) {
        Query query = entityManager.createNativeQuery(FIND_BY_CAMPAIGN_ID);
        query.setParameter("ID", id);
        List<Object[]> objects = query.getResultList();
        List<CampaignTemplateDetailDTO> result = new MaperUtils(objects).add("id").add("name").add("code").add("ord").add("optionValue").add("defaultValue")
            .add("typeName").add("exportExcel").add("display").add("editable").add("campaignName").add("isDefault").add("editTemplate").add("type").transform(CampaignTemplateDetailDTO.class);
        return result;
    }
}
