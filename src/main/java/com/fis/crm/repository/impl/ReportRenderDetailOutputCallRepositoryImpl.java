package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.repository.ReportRenderDetailOutputCallRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportRenderDetailOutputCallRepositoryImpl implements ReportRenderDetailOutputCallRepository {

    @PersistenceContext
    final
    EntityManager entityManager;

    final
    UserRepository userRepository;

    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;


    private final String GET_TEMP_OPTION_SQL = "select code,name from CAMPAIGN_RESOURCE_TEMP_OPTION where status=1 and CAMPAIGN_RESOURCE_TEMPLATE_ID=:tempId order by name asc";

    public ReportRenderDetailOutputCallRepositoryImpl(EntityManager entityManager, UserRepository userRepository, CampaignResourceTemplateMapper campaignResourceTemplateMapper) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
    }

    @Override
    public List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom, String callTimeTo, Long campaignId, String statusCall, String assignUser) {
        try {
            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select d.id,u.first_name" +
                " from campaign_resource_detail d\n" +
                " , jhi_user u where d.assign_user_id = u.id and d.campaign_id=:campaignId ");
            if (assignUser != null && !assignUser.equals("")) {
                GET_ID.append(" and upper(u.login) like '%'||upper(:assignUser)||'%'");
            }
            if (statusCall != null && !statusCall.equals("")) {
                GET_ID.append(" and d.sale_status=:saleStatus ");
            }
            if (callTimeFrom != null && !callTimeFrom.equals("")) {
                GET_ID.append(" and d.CALL_TIME>=to_date(:callTimeFrom,'dd/mm/yyyy') ");
            }
            if (callTimeTo != null && !callTimeTo.equals("")) {
                GET_ID.append(" and d.CALL_TIME<to_date(:callTimeTo,'dd/mm/yyyy')+1 ");
            }
            GET_ID.append(" order by u.login, d.call_time");

            Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
            query.setParameter("campaignId", campaignId);
            if (assignUser != null && !assignUser.equals("")) {
                query.setParameter("assignUser", assignUser);

            }
            if (statusCall != null && !statusCall.equals("")) {
                query.setParameter("saleStatus", statusCall);
            }
            if (callTimeFrom != null && !callTimeFrom.equals("")) {
                query.setParameter("callTimeFrom", callTimeFrom);
            }
            if (callTimeTo != null && !callTimeTo.equals("")) {
                query.setParameter("callTimeTo", callTimeTo);
            }

            StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
            GET_DYNAMIC_COLUMN_SQL.append("select * from campaign_resource_template " +
                "where campaign_id=:campaignId " +
                "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId   " +
                "order by ord asc");
            //////////////////////////////
            StringBuilder GET_DYNAMIC_QUESTION_SQL = new StringBuilder();
            GET_DYNAMIC_QUESTION_SQL.append("select (select CONTENT from campaign_script_question q\n" +
                "where q.id=r.campaign_script_question_id) name,enames from (\n" +
                "select \n" +
                "   campaign_script_question_id,\n" +
                "   rtrim (xmlagg (xmlelement (e, value_detail || ',')).extract ('//text()'), ',') enames\n" +
                "from (select campaign_id,campaign_resource_detail_id,campaign_script_id,campaign_script_question_id,value,\n" +
                "case when type=3 then Content ||': '||value\n" +
                "     when type=2 and value='true' then Content\n" +
                "     else value end value_detail\n" +
                "from campaign_calling_result) where campaign_id=:campaignId and campaign_resource_detail_id=:campaignResourceDetailId\n" +
                "group by campaign_script_question_id) r");
            ////////////////////////

            List<List<CampaignResourceTemplateDTO>> result = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    String id = DataUtil.safeToString(object[0]);
                    String firstName = DataUtil.safeToString(object[1]);

                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL), CampaignResourceTemplate.class);
                    queryDynamicColumn.setParameter("campaignId", campaignId);
                    queryDynamicColumn.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                    List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());

                    CampaignResourceTemplateDTO col_not_dynamic_caller = new CampaignResourceTemplateDTO();
                    col_not_dynamic_caller.setCode("caller");
                    col_not_dynamic_caller.setName("Tổng đài viên");
                    col_not_dynamic_caller.setValue(firstName);


                    if (!DataUtil.isNullOrEmpty(templates)) {
                        for (CampaignResourceTemplateDTO template : templates) {
                            if (template.getType() != null && template.getType().equalsIgnoreCase(Constants.CBX_CODE)) {
                                Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
                                queryTempOption.setParameter("tempId", template.getId());
                                List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                                    .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                                template.setOptions(options);
                            }
                        }
                    }
                    //////////////////
                    //Add Question
                    Query queryQuestion = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_QUESTION_SQL));
                    queryQuestion.setParameter("campaignId", campaignId);
                    queryQuestion.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                    List<Object[]> objectsQuestion = queryQuestion.getResultList();
                    if (objectsQuestion.size() > 0) {
                        for (Object[] objectQuestion : objectsQuestion) {
                            String questionName = DataUtil.safeToString(objectQuestion[0]);
                            String answer = DataUtil.safeToString(objectQuestion[1]);
                            CampaignResourceTemplateDTO question = new CampaignResourceTemplateDTO();
                            question.setName(questionName);
                            question.setCode(id+questionName);
                            question.setValue(formatAnswer(answer));
                            templates.add(question);


                        }

                    }
                    templates.add(col_not_dynamic_caller);
                    result.add(templates);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public String formatAnswer(String answer)
    {
        if(answer==null) return "";
        String temp=answer.replace(",,","");
        if(temp.startsWith(","))
            temp=temp.substring(1);
        if(temp.endsWith(","))
            temp=temp.substring(0,temp.length()-1);
        return temp;

    }

    @Override
    public ListExcelDynamicGroupDTO exportExcel(String callTimeFrom, String callTimeTo, Long campaignId, String
        statusCall, Long assignUser) {
        try {
            ListExcelDynamicGroupDTO excelGroup = new ListExcelDynamicGroupDTO();
            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select d.id,u.first_name" +
                " from campaign_resource_detail d\n" +
                " , jhi_user u where d.assign_user_id = u.id and d.campaign_id=:campaignId ");
            if (assignUser != null && !assignUser.equals("")) {
                GET_ID.append(" and upper(u.login) like '%'||upper(:assignUser)||'%'");
            }
            if (statusCall != null && !statusCall.equals("")) {
                GET_ID.append(" and d.sale_status=:saleStatus ");
            }
            if (callTimeFrom != null && !callTimeFrom.equals("")) {
                GET_ID.append(" and d.CALL_TIME>=to_date(:callTimeFrom,'dd/mm/yyyy') ");
            }
            if (callTimeTo != null && !callTimeTo.equals("")) {
                GET_ID.append(" and d.CALL_TIME<to_date(:callTimeTo,'dd/mm/yyyy')+1 ");
            }
            GET_ID.append(" order by u.login, d.call_time");


            StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
            GET_DYNAMIC_COLUMN_SQL.append("select type,code,name,value,id from campaign_resource_template " +
                "where campaign_id=:campaignId " +
                "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId  " +
                "order by ord asc");
            /////////////////////////////////
            //////////////////////////////
            StringBuilder GET_DYNAMIC_QUESTION_SQL = new StringBuilder();
            GET_DYNAMIC_QUESTION_SQL.append("select (select CONTENT from campaign_script_question q\n" +
                "where q.id=r.campaign_script_question_id) name,enames from (\n" +
                "select \n" +
                "   campaign_script_question_id,\n" +
                "   rtrim (xmlagg (xmlelement (e, value_detail || ',')).extract ('//text()'), ',') enames\n" +
                "from (select campaign_id,campaign_resource_detail_id,campaign_script_id,campaign_script_question_id,value,\n" +
                "case when type=3 then Content ||': '||value\n" +
                "     when type=2 and value='true' then Content\n" +
                "     else value end value_detail\n" +
                "from campaign_calling_result) where campaign_id=:campaignId and campaign_resource_detail_id=:campaignResourceDetailId\n" +
                "group by campaign_script_question_id) r");
            ////////////////////////


            Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
            query.setParameter("campaignId", campaignId);
            if (assignUser != null && !assignUser.equals("")) {
                query.setParameter("assignUser", assignUser);

            }
            if (statusCall != null && !statusCall.equals("")) {
                query.setParameter("saleStatus", statusCall);
            }
            if (callTimeFrom != null && !callTimeFrom.equals("")) {
                query.setParameter("callTimeFrom", callTimeFrom);
            }
            if (callTimeTo != null && !callTimeTo.equals("")) {
                query.setParameter("callTimeTo", callTimeTo);
            }

            List<ListExcelDynamicDTO> lsListExcelDynamicDTO = new ArrayList<ListExcelDynamicDTO>();

            ExcelDynamicDTO header = new ExcelDynamicDTO();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    String id = DataUtil.safeToString(object[0]);
                    String firstName = DataUtil.safeToString(object[1]);

                    List<ExcelDynamicDTO> lsData = new ArrayList<>();
                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL));
                    queryDynamicColumn.setParameter("campaignId", campaignId);
                    queryDynamicColumn.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                    List<Object[]> lstObj = queryDynamicColumn.getResultList();
                    if (!lstObj.isEmpty()) {
                        int idx = 0;
                        for (Object[] obj : lstObj) {
                            idx++;
                            ExcelDynamicDTO dto = new ExcelDynamicDTO();
                            int index = -1;
                            dto.setType(DataUtil.safeToString(obj[0]));
                            dto.setCode(DataUtil.safeToString(obj[1]));
                            dto.setName(DataUtil.safeToString(obj[2]));
                            if (obj[0] != null && obj[0].toString().equals(Constants.CBX_CODE)) {
                                Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
                                queryTempOption.setParameter("tempId", obj[4]);
                                List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                                    .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                                for (CampaignTemplateOptionDTO option : options) {
                                    if (option.getCode().equals(obj[3])) {
                                        dto.setValue(option.getName());
                                        break;
                                    }
                                }
                            } else {
                                dto.setValue(DataUtil.safeToString(obj[3]));
                            }
//                        dto.setId(DataUtil.safeToLong(obj[++index]));
                            dto.setOrd(idx);
                            lsData.add(dto);

                        }//End    for (Object[] obj : lstObj)
                        ///////////////////////////////////////
                        //Add Question
                        Query queryQuestion = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_QUESTION_SQL));
                        queryQuestion.setParameter("campaignId", campaignId);
                        queryQuestion.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                        List<Object[]> objectsQuestion = queryQuestion.getResultList();
                        if (objectsQuestion.size() > 0) {
                            for (Object[] objectQuestion : objectsQuestion) {
                                String questionName = DataUtil.safeToString(objectQuestion[0]);
                                String answer = DataUtil.safeToString(objectQuestion[1]);
                                ExcelDynamicDTO question = new ExcelDynamicDTO();
                                idx = idx + 1;
                                question.setName(questionName);
                                question.setValue(formatAnswer(answer));
                                question.setOrd(idx);
                                lsData.add(question);


                            }

                        }
                        //Add them gia tri mac dinh
                        /////////////////////////
                        ExcelDynamicDTO dto = new ExcelDynamicDTO();
                        idx = idx + 1;

                        dto = new ExcelDynamicDTO();
                        idx = idx + 1;
                        dto.setName("Tổng đài viên");
                        dto.setValue(firstName);
                        dto.setOrd(idx);
                        lsData.add(dto);

                    } //end if
                    ListExcelDynamicDTO returns = new ListExcelDynamicDTO();
                    returns.setLsDynamicDTO(lsData);
                    lsListExcelDynamicDTO.add(returns);
                }

            }//end objects.size() > 0
            excelGroup.setLsData(lsListExcelDynamicDTO);
            return excelGroup;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
