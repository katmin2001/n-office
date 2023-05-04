package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.repository.ReportRenderCallRepository;
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
public class ReportRenderCallRepositoryImpl implements ReportRenderCallRepository {

    @PersistenceContext
    final
    EntityManager entityManager;

    final
    UserRepository userRepository;

    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;


    private final String GET_TEMP_OPTION_SQL = "select code,name from CAMPAIGN_RESOURCE_TEMP_OPTION where status=1 and CAMPAIGN_RESOURCE_TEMPLATE_ID=:tempId order by name asc";


    public ReportRenderCallRepositoryImpl(EntityManager entityManager, UserRepository userRepository, CampaignResourceTemplateMapper campaignResourceTemplateMapper) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
    }


    @Override
    public List<List<CampaignResourceTemplateDTO>> getCallData(String callTimeFrom,
                                                               String callTimeTo,
                                                               Long campaignId,
                                                               String saleStatus,
                                                               String assignUser) {
        try {
            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select d.id,u.first_name," +
                " to_char(d.call_time_1,'DD/MM/YYYY HH24:mi:ss') calltime1,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_1) CALLING_STATUS_1, (select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_1) SALE_STATUS_1,\n" +
                " to_char(d.call_time_2,'DD/MM/YYYY HH24:mi:ss') calltime2,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_2) CALLING_STATUS_2,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_2) SALE_STATUS_2,\n" +
                " to_char(d.call_time_3,'DD/MM/YYYY HH24:mi:ss') calltime3,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_3) CALLING_STATUS_3,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_3) SALE_STATUS_3\n" +
                " from campaign_resource_detail d\n" +
                " , jhi_user u where d.assign_user_id = u.id and d.campaign_id=:campaignId ");
            if (assignUser != null && !assignUser.equals("")) {
                GET_ID.append(" and upper(u.login) like '%'||upper(:assignUser)||'%'");
            }
            if (saleStatus != null && !saleStatus.equals("")) {
                GET_ID.append(" and d.sale_status=:saleStatus ");
            }
            if (callTimeFrom != null && !callTimeFrom.equals("")) {
                GET_ID.append(" and d.CALL_TIME>=to_date(:callTimeFrom,'yyyy-mm-dd') ");
            }
            if (callTimeTo != null && !callTimeTo.equals("")) {
                GET_ID.append(" and d.CALL_TIME<to_date(:callTimeTo,'yyyy-mm-dd')+1 ");
            }
            GET_ID.append(" order by u.login, d.call_time");

            Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
            query.setParameter("campaignId", campaignId);
            if (assignUser != null && !assignUser.equals("")) {
                query.setParameter("assignUser", assignUser);

            }
            if (saleStatus != null && !saleStatus.equals("")) {
                query.setParameter("saleStatus", saleStatus);
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
                "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId  and code not in ('Status','SalesStatus') " +
                "order by ord asc");
            List<List<CampaignResourceTemplateDTO>> result = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    String id = DataUtil.safeToString(object[0]);
                    String firstName = DataUtil.safeToString(object[1]);
                    String callTime1 = DataUtil.safeToString(object[2]);
                    String callStatus1 = DataUtil.safeToString(object[3]);
                    String saleStatus1 = DataUtil.safeToString(object[4]);
                    String callTime2 = DataUtil.safeToString(object[5]);
                    String callStatus2 = DataUtil.safeToString(object[6]);
                    String saleStatus2 = DataUtil.safeToString(object[7]);
                    String callTime3 = DataUtil.safeToString(object[8]);
                    String callStatus3 = DataUtil.safeToString(object[9]);
                    String saleStatus3 = DataUtil.safeToString(object[10]);
                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL), CampaignResourceTemplate.class);
                    queryDynamicColumn.setParameter("campaignId", campaignId);
                    queryDynamicColumn.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                    List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());

                    CampaignResourceTemplateDTO col_not_dynamic_caller = new CampaignResourceTemplateDTO();
                    col_not_dynamic_caller.setCode("caller");
                    col_not_dynamic_caller.setName("Tổng đài viên");
                    col_not_dynamic_caller.setValue(firstName);

                    CampaignResourceTemplateDTO colNgay1 = new CampaignResourceTemplateDTO();
                    colNgay1.setCode("Ngaygoi1");
                    colNgay1.setName("Ngày gọi lần 1");
                    colNgay1.setValue(callTime1);
                    CampaignResourceTemplateDTO colCalstatus1 = new CampaignResourceTemplateDTO();
                    colCalstatus1.setCode("Trangthaigoi1");
                    colCalstatus1.setName("Trạng thái kết nối lần 1");
                    colCalstatus1.setValue(callStatus1);
                    CampaignResourceTemplateDTO colSaleStatus1 = new CampaignResourceTemplateDTO();
                    colSaleStatus1.setCode("SaleStatus1");
                    colSaleStatus1.setName("Trạng thái cuộc gọi lần 1");
                    colSaleStatus1.setValue(saleStatus1);
                    /////////////////////////////////////
                    CampaignResourceTemplateDTO colNgay2 = new CampaignResourceTemplateDTO();
                    colNgay2.setCode("Calltime2");
                    colNgay2.setName("Ngày gọi lần 2");
                    colNgay2.setValue(callTime2);
                    CampaignResourceTemplateDTO colCalstatus2 = new CampaignResourceTemplateDTO();
                    colCalstatus2.setCode("CallStatus2");
                    colCalstatus2.setName("Trạng thái kết nối lần 2");
                    colCalstatus2.setValue(callStatus2);
                    CampaignResourceTemplateDTO colSaleStatus2 = new CampaignResourceTemplateDTO();
                    colSaleStatus2.setCode("SaleStatus2");
                    colSaleStatus2.setName("Trạng thái cuộc gọi lần 2");
                    colSaleStatus2.setValue(saleStatus2);
                    /////////////////////////////////////
                    CampaignResourceTemplateDTO colNgay3 = new CampaignResourceTemplateDTO();
                    colNgay3.setCode("Calltime3");
                    colNgay3.setName("Ngày gọi lần 3");
                    colNgay3.setValue(callTime3);
                    CampaignResourceTemplateDTO colCalstatus3 = new CampaignResourceTemplateDTO();
                    colCalstatus3.setCode("CallStatus3");
                    colCalstatus3.setName("Trạng thái kết nối lần 3");
                    colCalstatus3.setValue(callStatus3);
                    CampaignResourceTemplateDTO colSaleStatus3 = new CampaignResourceTemplateDTO();
                    colSaleStatus3.setCode("SaleStatus3");
                    colSaleStatus3.setName("Trạng thái cuộc gọi lần 3");
                    colSaleStatus3.setValue(saleStatus3);

                    if (!DataUtil.isNullOrEmpty(templates)) {
                        templates.add(colNgay1);
                        templates.add(colCalstatus1);
                        templates.add(colSaleStatus1);
                        templates.add(colNgay2);
                        templates.add(colCalstatus2);
                        templates.add(colSaleStatus2);
                        templates.add(colNgay3);
                        templates.add(colCalstatus3);
                        templates.add(colSaleStatus3);
                        templates.add(col_not_dynamic_caller);

                        for (CampaignResourceTemplateDTO template : templates) {
                            if (template.getType() != null && template.getType().equalsIgnoreCase(Constants.CBX_CODE)) {
                                Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
                                queryTempOption.setParameter("tempId", template.getId());
                                List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                                    .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                                template.setOptions(options);
                            }
                        }
                        result.add(templates);
                    }

                }

            }
            return result;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ListExcelDynamicGroupDTO exportExcel(String callTimeFrom,
                                                String callTimeTo,
                                                Long campaignId,
                                                String statusCall,
                                                String assignUser) {
        ListExcelDynamicGroupDTO excelGroup = new ListExcelDynamicGroupDTO();
        StringBuilder GET_ID = new StringBuilder();
        GET_ID.append("select d.id,u.first_name," +
            " to_char(d.call_time_1,'DD/MM/YYYY HH24:mi:ss') calltime1,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_1) CALLING_STATUS_1, (select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_1) SALE_STATUS_1,\n" +
            " to_char(d.call_time_2,'DD/MM/YYYY HH24:mi:ss') calltime2,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_2) CALLING_STATUS_2,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_2) SALE_STATUS_2,\n" +
            " to_char(d.call_time_3,'DD/MM/YYYY HH24:mi:ss') calltime3,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_STATUS' and code=CALLING_STATUS_3) CALLING_STATUS_3,(select name from ap_domain where type='CAMPAIN_TEMPLATE_TYPE_SURVEY' and code=SALE_STATUS_3) SALE_STATUS_3\n" +
            " from campaign_resource_detail d\n" +
            " , jhi_user u where d.assign_user_id = u.id and d.campaign_id=:campaignId ");
        if (assignUser != null && !assignUser.equals("")) {
            GET_ID.append(" and upper(u.login) like '%'||upper(:assignUser)||'%'");
        }
        if (statusCall != null && !statusCall.equals("")) {
            GET_ID.append(" and d.sale_status=:saleStatus ");
        }
        if (callTimeFrom != null && !callTimeFrom.equals("")) {
            GET_ID.append(" and d.CALL_TIME>=to_date(:callTimeFrom,'yyyy-mm-dd') ");
        }
        if (callTimeTo != null && !callTimeTo.equals("")) {
            GET_ID.append(" and d.CALL_TIME<to_date(:callTimeTo,'yyyy-mm-dd')+1 ");
        }
        GET_ID.append(" order by u.login, d.call_time");


        StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
        GET_DYNAMIC_COLUMN_SQL.append("select type,code,name,value,id from campaign_resource_template " +
            "where campaign_id=:campaignId " +
            "and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId and code not in ('Status','SalesStatus') " +
            "order by ord asc");

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
                String callTime1 = DataUtil.safeToString(object[2]);
                String callStatus1 = DataUtil.safeToString(object[3]);
                String saleStatus1 = DataUtil.safeToString(object[4]);
                String callTime2 = DataUtil.safeToString(object[5]);
                String callStatus2 = DataUtil.safeToString(object[6]);
                String saleStatus2 = DataUtil.safeToString(object[7]);
                String callTime3 = DataUtil.safeToString(object[8]);
                String callStatus3 = DataUtil.safeToString(object[9]);
                String saleStatus3 = DataUtil.safeToString(object[10]);
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
                    //Add them gia tri mac dinh
                    /////////////////////////
                    ExcelDynamicDTO dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Ngày gọi lần 1");
                    dto.setValue(callTime1);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái kết nối lần 1");
                    dto.setValue(callStatus1);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái cuộc gọi lần 1");
                    dto.setValue(callStatus1);
                    dto.setOrd(idx);
                    lsData.add(dto);
                    ////////////////////////////
                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Ngày gọi lần 2");
                    dto.setValue(callTime2);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái kết nối lần 2");
                    dto.setValue(callStatus2);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái cuộc gọi lần 2");
                    dto.setValue(callStatus2);
                    dto.setOrd(idx);
                    lsData.add(dto);
                    ////////////////////////////
                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Ngày gọi lần 3");
                    dto.setValue(callTime3);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái kết nối lần 3");
                    dto.setValue(callStatus3);
                    dto.setOrd(idx);
                    lsData.add(dto);

                    dto = new ExcelDynamicDTO();
                    idx = idx + 1;
                    dto.setName("Trạng thái cuộc gọi lần 3");
                    dto.setValue(callStatus3);
                    dto.setOrd(idx);
                    lsData.add(dto);
                    ////////////////
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
    }
}
