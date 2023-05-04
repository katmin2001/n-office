package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.repository.ReportRenderStatisticQuestionRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ReportRenderStatisticQuestionRepositoryImpl implements ReportRenderStatisticQuestionRepository {

    @PersistenceContext
    final
    EntityManager entityManager;

    final
    UserRepository userRepository;

    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;

    public ReportRenderStatisticQuestionRepositoryImpl(EntityManager entityManager,
                                                       UserRepository userRepository,
                                                       CampaignResourceTemplateMapper campaignResourceTemplateMapper) {
        this.userRepository = userRepository;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
        this.entityManager = entityManager;
    }

//    private final String GET_TEMP_OPTION_SQL = "select code,name from CAMPAIGN_RESOURCE_TEMP_OPTION where status=1 and CAMPAIGN_RESOURCE_TEMPLATE_ID=:tempId order by name asc";

    @Override
    public List<ReportDTO> getData(String callTimeFrom, String callTimeTo, Long campaignId, Long questionId) {
        StringBuilder GET_ID = new StringBuilder();
        GET_ID.append("select content, sum(total) total\n" +
            "            from (\n" +
            "                     select c.content, case when c.value is not null and value != 'false' then 1 else 0 end total\n" +
            "                     from campaign_calling_result c,\n" +
            "                          CAMPAIGN_RESOURCE_DETAIL r\n" +
            "                                               where c.campaign_resource_detail_id = r.id\n" +
            "                       and c.campaign_id = :campaignId\n" +
            "                       and c.campaign_script_question_id = :questionId\n" +
            "                       and r.call_status = 2\n" +
            "                       and r.CALL_TIME >= to_date(:fromDate, 'DD-MM-YYYY')\n" +
            "                       and r.CALL_TIME < to_date(:toDate, 'DD-MM-YYYY') + 1\n" +
            "                 )\n" +
            "            group by content\n" +
            " union all " +
            "  select 'Không chọn', (select count(*) from CAMPAIGN_RESOURCE_DETAIL r where  1=1\n" +
            "                       and r.campaign_id = :campaignId\n" +
            "                       and r.call_status = 2\n" +
            "                       and r.CALL_TIME >= to_date(:fromDate, 'DD-MM-YYYY')\n" +
            "                       and r.CALL_TIME < to_date(:toDate, 'DD-MM-YYYY') + 1\n" +
            "                     )-(select sum(total) total\n" +
            "            from (\n" +
            "                     select  case when c.value is not null and value != 'false' then 1 else 0 end total\n" +
            "                     from campaign_calling_result c,\n" +
            "                          CAMPAIGN_RESOURCE_DETAIL r\n" +
            "                                               where c.campaign_resource_detail_id = r.id\n" +
            "                       and c.campaign_id = :campaignId\n" +
            "                       and c.campaign_script_question_id = :questionId\n" +
            "                       and r.call_status = 2\n" +
            "                       and r.CALL_TIME >= to_date(:fromDate, 'DD-MM-YYYY')\n" +
            "                       and r.CALL_TIME < to_date(:toDate, 'DD-MM-YYYY') + 1\n" +
            "                 )) from dual " +
            "union all\n" +
            "select 'Tổng',(select count(*) from CAMPAIGN_RESOURCE_DETAIL r where  1=1\n" +
            "                       and r.campaign_id = :campaignId\n" +
            "                       and r.call_status = 2\n" +
            "                       and r.CALL_TIME >= to_date(:fromDate, 'DD-MM-YYYY')\n" +
            "                       and r.CALL_TIME < to_date(:toDate, 'DD-MM-YYYY') + 1\n" +
            "                     ) from dual");

        Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        query.setParameter("campaignId", campaignId);
        query.setParameter("questionId", questionId);
        query.setParameter("fromDate", callTimeFrom);
        query.setParameter("toDate", callTimeTo);

        List<ReportDTO> result = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        if (objects.size() > 0) {
            for (Object[] object : objects) {
                ReportDTO report = new ReportDTO();
                report.setContent(DataUtil.safeToString(object[0]));
                report.setTotal(DataUtil.safeToInt(object[1]));
                result.add(report);
            }
        }
        return result;
    }

    @Override
    public ListExcelDynamicGroupDTO exportExcel(String callTimeFrom, String callTimeTo, Long campaignId, Long questionId) {
        ListExcelDynamicGroupDTO excelGroup = new ListExcelDynamicGroupDTO();
        List<ListExcelDynamicDTO> lsListExcelDynamicDTO = new ArrayList<ListExcelDynamicDTO>();

        List<ReportDTO> objects = this.getData(callTimeFrom, callTimeTo, campaignId, questionId);
        if (objects.size() > 0) {
            Integer count = 0;
            for (ReportDTO object : objects) {
                String answer = object.getContent();
                String total = object.getTotal().toString();
                List<ExcelDynamicDTO> lsData = new ArrayList<>();
                int idx = 0;

                ExcelDynamicDTO dto = new ExcelDynamicDTO();
                idx = idx + 1;
                ++count;
                dto.setName("STT");
                dto.setValue(count.toString());
                dto.setOrd(idx);
                lsData.add(dto);
                dto = new ExcelDynamicDTO();
                idx = idx + 1;
                dto.setName("Câu trả lời");
                dto.setValue(answer);
                dto.setOrd(idx);
                lsData.add(dto);
                dto = new ExcelDynamicDTO();
                idx = idx + 1;
                dto.setName("Số lượng");
                dto.setValue(total);
                dto.setOrd(idx);
                lsData.add(dto);

                ListExcelDynamicDTO returns = new ListExcelDynamicDTO();
                returns.setLsDynamicDTO(lsData);
                lsListExcelDynamicDTO.add(returns);
            }
        }
        excelGroup.setLsData(lsListExcelDynamicDTO);
        return excelGroup;
    }
}
