package com.fis.crm.repository.impl;

import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.MapperUtils;
import com.fis.crm.repository.CampaignResourceDetailCustomRepository;
import com.fis.crm.service.dto.CampaignResourceDetailDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CampaignResourceDetailCustomRepositoryImpl implements CampaignResourceDetailCustomRepository {

    private final EntityManager entityManager;

    public CampaignResourceDetailCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CampaignResourceDetailDTO> getByCampaignResourceId(Long campaignResourceId) {
        String sqlQuery = "select crd.id,\n" +
            "       crd.CAMPAIGN_RESOURCE_ID,\n" +
            "       crd.PHONE_NUMBER,\n" +
            "       crd.status,\n" +
            "       crd.ASSIGN_USER_ID,\n" +
            "       (select u.FIRST_NAME from JHI_USER u where u.ACTIVATED = 1 and u.ID = crd.ASSIGN_USER_ID) assignUserName,\n" +
            "       crd.GROUP_ID,\n" +
            "       crd.CALL_STATUS,\n" +
            "       crd.CAMPAIGN_ID,\n" +
            "       crd.EVALUATE_STATUS,\n" +
            "       crd.CALLING_STATUS,\n" +
            "       (select ad.NAME\n" +
            "        from AP_DOMAIN ad\n" +
            "        where ad.STATUS = 1\n" +
            "          and ad.TYPE = 'CAMPAIN_TEMPLATE_TYPE_STATUS'\n" +
            "          and ad.code = crd.CALLING_STATUS)                                                      callingStatusName,\n" +
            "       crd.SALE_STATUS,\n" +
            "       (select ad.NAME\n" +
            "        from AP_DOMAIN ad\n" +
            "        where ad.STATUS = 1\n" +
            "          and ad.TYPE = 'CAMPAIN_TEMPLATE_TYPE_SURVEY'\n" +
            "          and ad.code = crd.SALE_STATUS)                                                      saleStatusName,\n" +
            "       to_char(crd.CALL_TIME, 'dd/MM/yyyy HH:MI')\n" +
            "from CAMPAIGN_RESOURCE_DETAIL crd where crd.CAMPAIGN_RESOURCE_ID=:id and crd.STATUS=1";

        StringBuilder stringBuilder = new StringBuilder(sqlQuery);
        Map<String, Object> params = new HashMap<>();
        params.put("id", campaignResourceId);
        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        DbUtils.setParramToQuery(query, params);
        List<Object[]> objects = query.getResultList();
        MapperUtils mapperUtils = new MapperUtils(objects);
        List<CampaignResourceDetailDTO> dtos = mapperUtils.add("id")
            .add("campaignResourceId")
            .add("phoneNumber")
            .add("status")
            .add("assignUserId")
            .add("assignUserName")
            .add("groupId")
            .add("callStatus")
            .add("campaignId")
            .add("evaluateStatus")
            .add("callingStatus")
            .add("callingStatusName")
            .add("saleStatus")
            .add("saleStatusName")
            .add("callTime")
            .transform(CampaignResourceDetailDTO.class);
        return dtos;
    }
}
