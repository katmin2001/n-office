package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.repository.CampaignPerformInformationCustomRepository;
import com.fis.crm.repository.UserRepository;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CampaignPerformInformationCustomRepositoryImpl implements CampaignPerformInformationCustomRepository {

    @PersistenceContext
    final
    EntityManager entityManager;

    final
    UserRepository userRepository;

    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;

    public CampaignPerformInformationCustomRepositoryImpl(EntityManager entityManager, UserRepository userRepository, CampaignResourceTemplateMapper campaignResourceTemplateMapper) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
    }

    private final String GET_TEMP_OPTION_SQL = "select code,name from CAMPAIGN_RESOURCE_TEMP_OPTION where status=1 and CAMPAIGN_RESOURCE_TEMPLATE_ID=:tempId order by name asc";


    public List<CampaignPerformDTO> getDataInformation(String call_time_from,
                                                       String call_time_to,
                                                       Long campaign_id,
                                                       Long campaign_resource_id,
                                                       Long assign_user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select sum(total) total,sum(called), sum(non_called) from (\n" +
            "select 1 total,\n" +
            "       case when CALL_STATUS=2 and CALL_TIME>= :call_time_from and CALL_TIME < trunc(:call_time_to)+1 then 1 else 0 end called,\n" +
            "       case when CALL_STATUS!=2 then 1 else 0 end non_called \n" +
            "from campaign_resource_detail where 1=1 \n" +
            "and CAMPAIGN_ID= :campaign_id ");
        if (campaign_resource_id != null) {
            sql.append("and campaign_resource_id= :campaign_resource_id ");
        }
        if (assign_user_id != null) {
            sql.append("and ASSIGN_USER_ID= :assign_user_id ");
        }
        sql.append(")");
        Query query = entityManager.createNativeQuery(String.valueOf(sql));
        query.setParameter("call_time_from", Date.valueOf(call_time_from));
        query.setParameter("call_time_to", Date.valueOf(call_time_to));
        query.setParameter("campaign_id", campaign_id.toString());
        if (campaign_resource_id != null) {
            query.setParameter("campaign_resource_id", campaign_resource_id.toString());
        }
        if (assign_user_id != null) {
            query.setParameter("assign_user_id", assign_user_id.toString());
        }
        return new MaperUtils(query.getResultList()).add("total").add("called").add("nonCalled").transform(CampaignPerformDTO.class);
    }

    @Override
    public List<CampaignPerformDTO> getCallStatus(Long campaign_id, String code) {
        String sql = "select code,name from campaign_template_option where status=1 \n" +
            "and CAMPAIGN_TEMPLATE_DETAIL_ID=(select id from campaign_template_detail \n" +
            "where campaign_id in (select c.CAMPAIGN_TEMPLATE_ID from campaign c where c.id= :campaign_id) and code='" + code + "')";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("campaign_id", campaign_id.toString());
        return new MaperUtils(query.getResultList()).add("code").add("name").transform(CampaignPerformDTO.class);
    }

    @Override
    public List<List<CampaignResourceTemplateDTO>> getCallData(String call_time_from,
                                                               String call_time_to,
                                                               Long campaign_id,
                                                               Long campaign_resource_id,
                                                               Long assign_user_id,
                                                               String type, //Click vào 3 ô đầu, có giá trị lần luot là 1,2,3
                                                               String status, //Giá tri khi lick vao cot Trang thai cuoc goi
                                                               String saleStatus) { //Gia tri khi lick vao cot trang thai khao sat) {
        StringBuilder GET_ID = new StringBuilder();
        GET_ID.append("select d.id,u.first_name,to_char(d.call_time,'DD/MM/YYYY HH24:mi:ss') calltime,\n" +
            "(select resource_name from campaign_resource r where r.id=d.campaign_resource_id )resourcename from campaign_resource_detail d\n" +
            " left join jhi_user u on d.assign_user_id = u.id " +
            "where d.campaign_id= :campaign_id " +
            "and d.status=1 ");
        if (campaign_resource_id != null) {
            GET_ID.append("and d.campaign_resource_id= :campaign_resource_id ");
        }
        if (assign_user_id != null) {
            GET_ID.append("and d.assign_user_id= :assign_user_id ");
        }
        if (type != null && type.equals("2")) //Da goi
        {
            GET_ID.append("and d.call_status=2 and d.CALL_TIME >= :call_time_from and d.CALL_TIME < trunc(:call_time_to)+1 ");

        }
        if (type != null && type.equals("3")) //Chua goi
        {
            GET_ID.append("and d.call_status=1 ");

        }
        if (status != null) {
            GET_ID.append("and d.calling_status= :calling_status");
        }
        if (saleStatus != null) {
            GET_ID.append("and d.sale_status= :sale_status");
        }
        Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
        query.setParameter("campaign_id", campaign_id.toString());
        if (campaign_resource_id != null) {
            query.setParameter("campaign_resource_id", campaign_resource_id.toString());
        }
        if (assign_user_id != null) {
            query.setParameter("assign_user_id", assign_user_id.toString());

        }
        if (type != null && type.equals("2")) {
            query.setParameter("call_time_from", Date.valueOf(call_time_from));
            query.setParameter("call_time_to", Date.valueOf(call_time_to));

        }
        if (status != null) {
            query.setParameter("calling_status", status);
        }
        if (saleStatus != null) {
            query.setParameter("sale_status", saleStatus);
        }

        StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
        GET_DYNAMIC_COLUMN_SQL.append("select * from campaign_resource_template " +
            "where campaign_id=:campaignId " +
            "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId " +
            "order by ord asc");
        List<List<CampaignResourceTemplateDTO>> result = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        if (objects.size() > 0) {
            for (Object[] object : objects) {
                String id = DataUtil.safeToString(object[0]);
                String firstName = DataUtil.safeToString(object[1]);
                String callTime = DataUtil.safeToString(object[2]);
                String resourceName = DataUtil.safeToString(object[3]);
                Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL), CampaignResourceTemplate.class);
                queryDynamicColumn.setParameter("campaignId", campaign_id);
                queryDynamicColumn.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
                CampaignResourceTemplateDTO col_not_dynamic_caller = new CampaignResourceTemplateDTO();
                col_not_dynamic_caller.setCode("caller");
                col_not_dynamic_caller.setName("caller");
                col_not_dynamic_caller.setValue(firstName);
                CampaignResourceTemplateDTO col_not_dynamic_call_time = new CampaignResourceTemplateDTO();
                col_not_dynamic_call_time.setCode("callTime");
                col_not_dynamic_call_time.setName("callTime");
                col_not_dynamic_call_time.setValue(callTime);
                CampaignResourceTemplateDTO col_not_dynamic_resourceName = new CampaignResourceTemplateDTO();
                col_not_dynamic_resourceName.setCode("resourceName");
                col_not_dynamic_resourceName.setName("resourceName");
                col_not_dynamic_resourceName.setValue(resourceName);
                templates.add(col_not_dynamic_caller);
                templates.add(col_not_dynamic_call_time);
                templates.add(col_not_dynamic_resourceName);
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
                result.add(templates);
            }

        }
        return result;
    }

    @Override
    public ListExcelDynamicGroupDTO exportExcel(String call_time_from, String call_time_to, Long campaign_id, Long campaign_resource_id, Long assign_user_id, String type, String status, String saleStatus) {
        ListExcelDynamicGroupDTO excelGroup = new ListExcelDynamicGroupDTO();
        StringBuilder GET_ID = new StringBuilder();
        GET_ID.append("select d.id,u.first_name,to_char(d.call_time,'DD/MM/YYYY HH24:mi:ss') calltime,\n" +
            "(select resource_name from campaign_resource r where r.id=d.campaign_resource_id )resourcename from campaign_resource_detail d\n" +
            " left join jhi_user u on d.assign_user_id = u.id " +
            "where d.campaign_id= :campaign_id " +
            "and d.status=1 ");
        if (campaign_resource_id != null) {
            GET_ID.append("and d.campaign_resource_id= :campaign_resource_id ");
        }
        if (assign_user_id != null) {
            GET_ID.append("and d.assign_user_id= :assign_user_id ");
        }
        if (type != null && type.equals("2")) //Da goi
        {
            GET_ID.append("and d.call_status=2 and d.CALL_TIME >= :call_time_from and d.CALL_TIME <= trunc(:call_time_to)+1 ");

        }
        if (type != null && type.equals("3")) //Chua goi
        {
            GET_ID.append("and d.call_status=1 ");

        }

        StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
        GET_DYNAMIC_COLUMN_SQL.append("select type,code,name,value,id from campaign_resource_template " +
            "where campaign_id=:campaignId " +
            "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId " +
            "order by ord asc");

        Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
        query.setParameter("campaign_id", campaign_id.toString());
        if (campaign_resource_id != null) {
            query.setParameter("campaign_resource_id", campaign_resource_id.toString());
        }
        if (assign_user_id != null) {
            query.setParameter("assign_user_id", assign_user_id.toString());

        }
        if (type != null && type.equals("2")) {
            query.setParameter("call_time_from", Date.valueOf(call_time_from));
            query.setParameter("call_time_to", Date.valueOf(call_time_to));

        }
        List<ListExcelDynamicDTO> lsListExcelDynamicDTO = new ArrayList<ListExcelDynamicDTO>();

        ExcelDynamicDTO header = new ExcelDynamicDTO();
        List<Object[]> objects = query.getResultList();
        if (objects.size() > 0) {
            for (Object[] object : objects) {
                String id = DataUtil.safeToString(object[0]);
                String firstName = DataUtil.safeToString(object[1]);
                String callTime = DataUtil.safeToString(object[2]);
                String resourceName = DataUtil.safeToString(object[3]);
                List<ExcelDynamicDTO> lsData = new ArrayList<>();
                Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL));
                queryDynamicColumn.setParameter("campaignId", campaign_id);
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
                    //Add them gia tri mac dinh
                    ExcelDynamicDTO dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Người gọi");
                    dto.setValue(firstName);
                    dto.setOrd(idx);
                    lsData.add(dto);
                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Ngày gọi");
                    dto.setValue(callTime);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Tên nguồn dữ liệu");
                    dto.setValue(resourceName);
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
    }

}
