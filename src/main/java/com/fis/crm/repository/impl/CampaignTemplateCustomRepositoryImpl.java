package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.domain.CampaignTemplate;
import com.fis.crm.repository.CampaignTemplateCustomRepository;
import com.fis.crm.service.dto.CampaignTemplateDTO;
import com.fis.crm.service.dto.OptionSetValueDTO;
import com.fis.crm.web.rest.errors.BusinessException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

@Repository
public class CampaignTemplateCustomRepositoryImpl implements CampaignTemplateCustomRepository {
    private final EntityManager entityManager;
    private final String GET_INFO_CODE_SQL = "select code, name\n" +
        "from option_set_value\n" +
        "where status = 1\n" +
        "  AND option_set_id in (select o.option_set_id from option_set o where o.code = 'MA_TRUONG_THONG_TIN' AND STATUS = 1)\n" +
        "ORDER BY ord";
    private final String CHECK_UNUSED_TEMPLATE_SQL = "select count(*) from campaign where status=1 and START_DATE<sysdate and (END_DATE is null or END_DATE>sysdate)\n" +
        "and CAMPAIGN_TEMPLATE_ID =:id";

    private final String GET_ALL_TEMPLATE = "SELECT C.ID,\n" +
        "       C.CAMPAIGN_NAME,\n" +
        "       C.STATUS,\n" +
        "       C.CREATE_DATETIME,\n" +
        "       C.CREATE_USER,\n" +
        "       (select count(*)\n" +
        "        from campaign\n" +
        "        where status = 1\n" +
        "          and START_DATE < sysdate\n" +
        "          and (END_DATE is null or END_DATE > sysdate)\n" +
        "          and CAMPAIGN_TEMPLATE_ID = C.ID) AS IS_USED\n" +
        "FROM CAMPAIGN_TEMPLATE C\n" +
        "WHERE STATUS = 1 ORDER BY CREATE_DATETIME DESC";

    private final String CHECK_EXISTED_TEMPLATE_SQL = "SELECT * FROM CAMPAIGN_TEMPLATE WHERE LOWER(CAMPAIGN_NAME) =:campaignName #id";

    public CampaignTemplateCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<OptionSetValueDTO> getAllInfoCode() {
        Query query = entityManager.createNativeQuery(GET_INFO_CODE_SQL);
        return new MaperUtils(query.getResultList()).add("code").add("name").transform(OptionSetValueDTO.class);
    }

    @Override
    public Boolean checkUnusedTemplate(Long id) {
        Query query = entityManager.createNativeQuery(CHECK_UNUSED_TEMPLATE_SQL);
        query.setParameter("id", id);
        return DataUtil.safeToInt(query.getSingleResult()) <= 0;
    }

    @Override
    public CampaignTemplateDTO insertTemplate(CampaignTemplateDTO dto, Long userId) {
        Session session = entityManager.unwrap(Session.class);
        CampaignTemplateDTO campaignTemplateDTO = new CampaignTemplateDTO();
        try {
            session.doWork(connection -> {
                CallableStatement statement = connection.prepareCall("{call pck_util.create_campain_template(?,?,?,?)}");
                statement.setString(1, dto.getCampaignName());
                statement.setLong(2, userId);
                statement.registerOutParameter(3, Types.INTEGER);
                statement.registerOutParameter(4, Types.NVARCHAR);
                statement.executeUpdate();
                campaignTemplateDTO.setId(DataUtil.safeToLong(statement.getInt(3)));
                campaignTemplateDTO.setErrMessage(statement.getString(4));
            });
            return campaignTemplateDTO;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("error");
        }
    }

    @Override
    public List<CampaignTemplateDTO> findAllAndStatus() {
        Query query = entityManager.createNativeQuery(GET_ALL_TEMPLATE);
        List<Object[]> objects = query.getResultList();
        List<CampaignTemplateDTO> templateDTOS = new MaperUtils(objects).add("id").add("campaignName").add("status").add("createDatetime")
            .add("createUser").add("isUsed").transform(CampaignTemplateDTO.class);
        return templateDTOS;
    }

    @Override
    public CampaignTemplate findFirstByCampaignNameAndIdNot(String campaignName, Long id) {
        Query query = entityManager.createNativeQuery(CHECK_EXISTED_TEMPLATE_SQL.replace("#id", DataUtil.isNullOrEmpty(id) ? "" : "AND ID != :id"), CampaignTemplate.class);
        query.setParameter("campaignName", campaignName.toLowerCase());
        if (!DataUtil.isNullOrEmpty(id)) query.setParameter("id", id);
        return query.getResultList().size() > 0 ? (CampaignTemplate) query.getResultList().get(0) : null;
    }
}
