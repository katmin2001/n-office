package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.DbUtils;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.commons.Translator;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.domain.User;
import com.fis.crm.repository.RecordCallResultCustomRepository;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.errors.BusinessException;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordCallResultCustomRepositoryImpl implements RecordCallResultCustomRepository {
    private final EntityManager entityManager;
    private final UserService userService;
    private final ActionLogService actionLogService;
    private final CampaignResourceTemplateMapper campaignResourceTemplateMapper;

    private final String GET_CAMPAIGN_ID_SQL = "select id from CAMPAIGN_RESOURCE_DETAIL where ASSIGN_USER_ID=:userId and CALL_STATUS=1 and CAMPAIGN_ID=:campaignId and ROWNUM=1";
    private final String GET_DYNAMIC_COLUMN_SQL = "select * from campaign_resource_template where campaign_id=:campaignId and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId order by ord asc";
    private final String GET_TEMP_OPTION_SQL = "select code,name from CAMPAIGN_RESOURCE_TEMP_OPTION where status=1 and CAMPAIGN_RESOURCE_TEMPLATE_ID=:tempId order by name asc";

    private final String GET_QUESTIONS_SQL = "select cq.ID, cq.CONTENT, cs.START_CONTENT, cs.END_CONTENT\n" +
        "from CAMPAIGN_SCRIPT_QUESTION cq\n" +
        "         left join CAMPAIGN_SCRIPT cs on cq.CAMPAIGN_SCRIPT_ID = cs.ID\n" +
        "where cq.CAMPAIGN_SCRIPT_ID in (select s.CAMPAIGN_SCRIPT_ID from campaign s where s.id = :campaignId)\n" +
        "  and cq.status = 1\n" +
        "order by cq.position asc\n";
    private final String GET_ANSWERS_FOR_QUESTION_SQL = "select id,type,content,min_value,max_value,value from campaign_calling_result where CAMPAIGN_SCRIPT_QUESTION_ID=:questionId and CAMPAIGN_RESOURCE_DETAIL_ID=:campaignResourceDetailId and status=1 order by position asc";
    private final String ADD_OUTER_CALL_SQL = " {? = call pck_util.make_new_call (?,?,?,?,?)}";
    private final String GET_ALL_CAMPAIGN_FOR_TDV_SQL = "select * from campaign where id in (\n" +
        "select CAMPAIGN_ID from campaign_resource_detail where ASSIGN_USER_ID=:userId\n" +
        ") and status=1 and START_DATE<sysdate and (END_DATE is null or END_DATE>=trunc(sysdate))";
    private final String GET_ID_CAMPAIGN_RESOURCE_DETAIL_BY_CID = "select id from CAMPAIGN_RESOURCE_DETAIL where CID=:CID and rownum=1";
    private final String GET_CAMPAIGN_ID_CAMPAIGN_RESOURCE_DETAIL_BY_CID = "select campaign_id from CAMPAIGN_RESOURCE_DETAIL where CID=:CID and rownum=1";

    public RecordCallResultCustomRepositoryImpl(EntityManager entityManager, UserService userService, ActionLogService actionLogService, CampaignResourceTemplateMapper campaignResourceTemplateMapper) {
        this.entityManager = entityManager;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.campaignResourceTemplateMapper = campaignResourceTemplateMapper;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId) {

        Query queryDynamicColumn = entityManager.createNativeQuery(GET_DYNAMIC_COLUMN_SQL, CampaignResourceTemplate.class);
        Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
        Long userId = userService.getUserIdLogin();

        //PhuongND sua goi vao thu tuc, tinh truong hop uu tien load len ca cac cuoc goi loi
        long campaignResourceDetailId = getNextNumber(userId,campaignId);
        List<List<CampaignResourceTemplateDTO>> ls=getCallData(campaignResourceDetailId);
        queryDynamicColumn.setParameter("campaignId", campaignId);
        queryDynamicColumn.setParameter("campaignResourceDetailId", campaignResourceDetailId);
        List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
        if (!DataUtil.isNullOrEmpty(templates)) {
            templates.forEach(template -> {
                if (Constants.CBX_CODE.equalsIgnoreCase(template.getType())) {
                    queryTempOption.setParameter("tempId", template.getId());
                    List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                        .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                    template.setOptions(options);
                }
            });
        }
        return templates;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNewPhoneNumber(Long campaignResourceDetailId,Long campaignId) {

        Query queryDynamicColumn = entityManager.createNativeQuery(GET_DYNAMIC_COLUMN_SQL, CampaignResourceTemplate.class);
        Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
        Long userId = userService.getUserIdLogin();
        List<List<CampaignResourceTemplateDTO>> ls=getCallData(campaignResourceDetailId);
        queryDynamicColumn.setParameter("campaignId", campaignId);
        queryDynamicColumn.setParameter("campaignResourceDetailId", campaignResourceDetailId);
        List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
        if (!DataUtil.isNullOrEmpty(templates)) {
            templates.forEach(template -> {
                if (Constants.CBX_CODE.equalsIgnoreCase(template.getType())) {
                    queryTempOption.setParameter("tempId", template.getId());
                    List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                        .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                    template.setOptions(options);
                }
            });
        }
        return templates;
    }



    public List<List<CampaignResourceTemplateDTO>> getCallData(long campaignResourceDetailId) {
        try {

            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select id,to_char(call_time,'dd/mm/yyyy HH24:mi:ss') from campaign_resource_detail_his \n" +
                "where campaign_resource_detail_id=:campaignResourceDetailId order by id");

            Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
            query.setParameter("campaignResourceDetailId", campaignResourceDetailId);


            StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
            GET_DYNAMIC_COLUMN_SQL.append("select campaign_resource_template_id as id,campaign_id," +
                "campaign_resource_id,campaign_resource_detail_id,type,code,name," +
                "ord,value,editable,default_value from campaign_resource_template_his " +
                "where batch_id=:batchId " +
                "order by ord asc");
            List<List<CampaignResourceTemplateDTO>> result = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    String id = DataUtil.safeToString(object[0]);

                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL), CampaignResourceTemplate.class);
                    queryDynamicColumn.setParameter("batchId", id);
                    List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
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
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;


    }

    @Override
    public List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        Query queryQuestion = entityManager.createNativeQuery(GET_QUESTIONS_SQL);
        Query queryAnswers = entityManager.createNativeQuery(GET_ANSWERS_FOR_QUESTION_SQL);
        queryQuestion.setParameter("campaignId", campaignId);
        queryAnswers.setParameter("campaignResourceDetailId", campaignResourceDetailId);
        List<Object[]> objects = queryQuestion.getResultList();
        List<CampaignScriptQuestionResponseDTO> questionDTO = new MaperUtils(objects).add("id").add("content").add("startContent").add("endContent").transform(CampaignScriptQuestionResponseDTO.class);
        List<QuestionsAndAnswersDTO> questionsAndAnswersDTOS = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(questionDTO)) {
            questionDTO.forEach(question -> {
                QuestionsAndAnswersDTO questionsAndAnswersDTO = new QuestionsAndAnswersDTO();
                questionsAndAnswersDTO.setQuestion(question.getContent());
                questionsAndAnswersDTO.setStartContent(question.getStartContent());
                questionsAndAnswersDTO.setEndContent(question.getEndContent());
                queryAnswers.setParameter("questionId", question.getId());
                List<AnswersDTO> answersDTOS = new MaperUtils(queryAnswers.getResultList()).add("id").add("type").add("content").add("minValue").add("maxValue").add("value").transform(AnswersDTO.class);
                if (!DataUtil.isNullOrEmpty(answersDTOS)) {
                    AnswersDTO radioAnswer = new AnswersDTO();
                    if (Constants.RADIO_TYPE.equalsIgnoreCase(answersDTOS.get(0).getType())) {
                        radioAnswer.setType(Constants.RADIO_TYPE);
                        radioAnswer.setRadioValues(answersDTOS);
                        answersDTOS = Arrays.asList(radioAnswer);
                    } else if (Constants.RANKING_TYPE.equalsIgnoreCase(answersDTOS.get(0).getType())) {
                        List<AnswersDTO> rankingOption = new ArrayList<>();
                        Integer min = answersDTOS.get(0).getMinValue();
                        Integer max = answersDTOS.get(0).getMaxValue();
                        if (!DataUtil.isNullOrEmpty(min) && !DataUtil.isNullOrEmpty(max)) {
                            for (int i = min; i <= max; i++) {
                                AnswersDTO option = new AnswersDTO();
                                option.setId(answersDTOS.get(0).getId());
                                option.setValue(answersDTOS.get(0).getValue());
                                option.setContent(String.valueOf(i));
                                rankingOption.add(option);
                            }
                            radioAnswer.setType(Constants.RANKING_TYPE);
                            radioAnswer.setRadioValues(rankingOption);
                        }
                        answersDTOS = Arrays.asList(radioAnswer);
                    }
                }
                questionsAndAnswersDTO.setAnswers(answersDTOS);
                questionsAndAnswersDTOS.add(questionsAndAnswersDTO);
            });
        }
        return questionsAndAnswersDTOS;
    }

    @Override
    public OuterCallDTO saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) throws SQLException {
        OuterCallDTO errMessages = new OuterCallDTO();
        Connection connection = null;
        CallableStatement callableStatement = null;
        PreparedStatement pstmt = null;
        String strSQL = "";
        try {
            EntityManagerFactoryInfo infor = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
            DataSource dataSource = infor.getDataSource();
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            //1. Update bang campaign_resource_template
            List<AnswersDTO> lsTemplate = recordCallResultDTO.getLstResourceTemplate();
            pstmt = connection.prepareStatement("update campaign_resource_template set value=? where id=?");
            for (AnswersDTO o : lsTemplate) {
                //Kiem tra bat buoc phai nhap trang thai cuoc goi
                if (!checkStatus(connection, o)) {
                    errMessages.setErrorCode("001");
                    return errMessages;
                }
                // return "Bạn phải nhập trạng thái cuộc goi";

                //Kiem tra bat buoc phai nhap trang thai cuoc goi
                if (!checkSaleStatus(connection, o)) {
                    errMessages.setErrorCode("002");
                    return errMessages;
                }
//                    return "Bạn phải nhập trạng thái khảo sát";

                pstmt.setString(1, o.getValue());
                pstmt.setLong(2, o.getId());
                pstmt.executeUpdate();
            }
            closeCnn(pstmt);
            ////////////////////////////////////////////////
            //2. Update bang campaign_calling_result
            //2.1 : Clear dua lieu truoc
            pstmt = connection.prepareStatement("update campaign_calling_result set value=null where campaign_id=? and campaign_resource_detail_id=?");
            pstmt.setLong(1, campaignResourceTemplateDTO.getCampaignId());
            pstmt.setLong(2, campaignResourceTemplateDTO.getCampaignResourceDetailId());
            pstmt.executeUpdate();
            //2.2 : Update du lieu moi
            List<AnswersDTO> lsAnser = recordCallResultDTO.getLstCampaignCallResult();
            pstmt = connection.prepareStatement("update campaign_calling_result set value=? where id=?");
            for (AnswersDTO o : lsAnser) {
                if(o.getValue()!=null&&!o.getValue().equals("false")) {
                    pstmt.setString(1, o.getValue());
                    pstmt.setLong(2, o.getId());
                    pstmt.executeUpdate();
                }
            }
            closeCnn(pstmt);
            //////////////////////////////////////////////
            //2. Update cac bang lich su va bang campaign_resource_detail
            strSQL = "{call pck_util.update_callout(?,?,?,?) }";
            callableStatement = connection.prepareCall(strSQL);
            callableStatement.setLong(1, campaignResourceTemplateDTO.getCampaignId());
            callableStatement.setLong(2, campaignResourceTemplateDTO.getCampaignResourceDetailId());
            callableStatement.setLong(3, -1L);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.execute();
            String strError = callableStatement.getString(4);
            if (strError != null && !"".equals(strError)) {
                return errMessages;
            }
            connection.commit();
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, "Lưu thông tin kịch bản gọi ra",
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.record_call_results, "CONFIG_MENU_ITEM"));
            return errMessages;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            closeCnn(pstmt);
            closeCnn(callableStatement);
            closeCnn(connection);
        }

    }
    public boolean checkStatus(Connection cn,AnswersDTO o)
    {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try
        {
            String strSQL="select * from campaign_resource_template where id=?";
            pstmt=cn.prepareStatement(strSQL);
            pstmt.setLong(1,o.getId().longValue());
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                String code=rs.getString("code");
                if("Status".equals(code)&&(null==o.getValue()||"".equals(o.getValue())||"null".equals(o.getValue())))
                    return false;
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            DbUtils.close(rs);
            DbUtils.close(pstmt);
        }
        return true;
    }
    public boolean checkSaleStatus(Connection cn,AnswersDTO o)
    {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try
        {
            String strSQL="select * from campaign_resource_template where id=?";
            pstmt=cn.prepareStatement(strSQL);
            pstmt.setLong(1,o.getId().longValue());
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                String code=rs.getString("code");
                String value=o.getValue();
                if("SalesStatus".equals(code)&&(null==o.getValue()||"".equals(o.getValue())||"null".equals(o.getValue())))
                    return false;
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            DbUtils.close(rs);
            DbUtils.close(pstmt);
        }
        return true;
    }


    public void closeCnn(Connection cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeCnn(PreparedStatement cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeCnn(CallableStatement cn) {
        try {
            if (cn != null)
                cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public OuterCallDTO addOuterCall(Long campaignId, String phoneNumber, String cid) {
        Session session = entityManager.unwrap(Session.class);
        Long userId = userService.getUserIdLogin();
        OuterCallDTO outerCallDTO = new OuterCallDTO();
        try {
            session.doWork(connection -> {
                CallableStatement statement = connection.prepareCall(ADD_OUTER_CALL_SQL);
                statement.registerOutParameter(1, Types.INTEGER);
                statement.setLong(2, campaignId);
                statement.setString(3, phoneNumber);
                statement.setString(4, cid);
                statement.setLong(5, userId);
                statement.registerOutParameter(6, Types.VARCHAR);
                statement.executeUpdate();
                Long campaignResourceDetailId = !DataUtil.isNullOrEmpty(statement.getInt(1))
                    ? DataUtil.safeToLong(statement.getInt(1)) : 0L;
                String errCode = !DataUtil.isNullOrEmpty(statement.getString(6)) ? DataUtil.safeToString(statement.getString(6)) : null;
                outerCallDTO.setCampaignResourceDetailId(campaignResourceDetailId);
                outerCallDTO.setErrorCode(errCode);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("error");
        }
        return outerCallDTO;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getInformationOfOuterCall(Long campaignResourceDetailId, Long campaignId) {
        Query queryDynamicColumn = entityManager.createNativeQuery(GET_DYNAMIC_COLUMN_SQL, CampaignResourceTemplate.class);
        Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
        queryDynamicColumn.setParameter("campaignId", campaignId);
        queryDynamicColumn.setParameter("campaignResourceDetailId", campaignResourceDetailId);
        List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
        if (!DataUtil.isNullOrEmpty(templates)) {
            templates.forEach(template -> {
                if (Constants.CBX_CODE.equalsIgnoreCase(template.getType())) {
                    queryTempOption.setParameter("tempId", template.getId());
                    List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                        .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                    template.setOptions(options);
                }
            });
        }
        return templates;
    }

    @Override
    public List<Campaign> getAllCampaignsForTDV() {
        Long userId = userService.getUserIdLogin();
        Query query = entityManager.createNativeQuery(GET_ALL_CAMPAIGN_FOR_TDV_SQL, Campaign.class);
        query.setParameter("userId", userId);
        List<Campaign> campaigns = query.getResultList();
        return campaigns;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getCallInformation(String CID, Long campaignId) {
        Query queryId = entityManager.createNativeQuery(GET_ID_CAMPAIGN_RESOURCE_DETAIL_BY_CID);
        Query queryDynamicColumn = entityManager.createNativeQuery(GET_DYNAMIC_COLUMN_SQL, CampaignResourceTemplate.class);
        Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);

        queryId.setParameter("CID", CID);
        List<Object[]> objects = queryId.getResultList();
        Long campaignResourceDetailId = DataUtil.safeToLong(objects.size() > 0 ? objects.get(0) : 0L);

        queryDynamicColumn.setParameter("campaignId", campaignId);
        queryDynamicColumn.setParameter("campaignResourceDetailId", campaignResourceDetailId);
        List<CampaignResourceTemplateDTO> templates = campaignResourceTemplateMapper.toDto(queryDynamicColumn.getResultList());
        if (!DataUtil.isNullOrEmpty(templates)) {
            templates.forEach(template -> {
                if (Constants.CBX_CODE.equalsIgnoreCase(template.getType())) {
                    queryTempOption.setParameter("tempId", template.getId());
                    List<CampaignTemplateOptionDTO> options = new MaperUtils(queryTempOption.getResultList())
                        .add("code").add("name").transform(CampaignTemplateOptionDTO.class);
                    template.setOptions(options);
                }
            });
        }
        return templates;
    }

    /**
     * Build dữ liệu cuộc gọi
     * @param userId
     * @param campaignId

     */
    public void buildCallingData(Long userId, Long campaignId,long  campaignResourceId,long  campaignScriptId) {
        Connection connection = null;
        String sql = "{call PCK_UTIL.BUILD_CALLING_DATA(?, ?, ?, ?) }";
        CallableStatement statement = null;
        try {
            EntityManagerFactoryInfo infor = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
            DataSource dataSource = infor.getDataSource();
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.setLong(1, userId);
            statement.setLong(2, campaignId);
            statement.setLong(3, campaignResourceId);
            statement.setLong(4, campaignScriptId);
            statement.execute();

        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
        }
        finally {
            closeCnn(statement);
            closeCnn(connection);
        }
    }
    public long getNextNumber(Long userId, Long campaignId) {
        Connection connection = null;
        String sql = "{? =call PCK_UTIL.callout_get_nextnumber(?, ?) }";
        CallableStatement statement = null;
        long resourceDetailId=-1;
        try {
            EntityManagerFactoryInfo infor = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
            DataSource dataSource = infor.getDataSource();
            connection = dataSource.getConnection();
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, OracleTypes.BIGINT);
            statement.setLong(2, userId);
            statement.setLong(3, campaignId);
            statement.execute();
            resourceDetailId=(long)statement.getObject(1);
        } catch (Exception e) {
            throw new BadRequestAlertException(Translator.toLocale("campaign-resource.import.error"), "", "");
        }
        finally {
            closeCnn(statement);
            closeCnn(connection);
        }
        return resourceDetailId;
    }

	public List<List<CampaignResourceTemplateDTO>> getRecordCallsHistory(Long campaignResourceDetailId) {
        try {
            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select a.id,to_char(a.call_time,'dd/mm/yyyy HH24:mi:ss'), "+
                " (select first_name from jhi_user j where  j.id=a.assign_user_id),a.call_file from campaign_resource_detail_his a \n" +
                "where campaign_resource_detail_id=:campaignResourceDetailId order by id");
            Query query = entityManager.createNativeQuery(String.valueOf(GET_ID));
            query.setParameter("campaignResourceDetailId", campaignResourceDetailId);
            StringBuilder GET_DYNAMIC_COLUMN_SQL = new StringBuilder();
            GET_DYNAMIC_COLUMN_SQL.append("select CAMPAIGN_RESOURCE_TEMPLATE_ID id,campaign_id," +
                "campaign_resource_id,campaign_resource_detail_id,type,code,name," +
                "ord,value,editable from campaign_resource_template_his " +
                "where batch_id=:batchId " +
                "order by ord asc");
            List<List<CampaignResourceTemplateDTO>> result = new ArrayList<>();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    long id=DataUtil.safeToLong(object[0]);
                    String createDate=DataUtil.safeToString(object[1]);
                    String createUser=DataUtil.safeToString(object[2]);
                    String callFile=DataUtil.safeToString(object[3]);
                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL));
                    queryDynamicColumn.setParameter("batchId", id);
                    List<CampaignResourceTemplateDTO> templates = new MaperUtils(queryDynamicColumn.getResultList()).add("id").add("campaignId").add("campaignResourceId")
                        .add("campaignResourceDetailId").add("type").add("code").add("name").add("ord").add("value").add("editable").transform(CampaignResourceTemplateDTO.class);
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
                    CampaignResourceTemplateDTO extend=new CampaignResourceTemplateDTO();
                    extend.setCode("create_user");
                    extend.setName("Tổng đài viên");
                    extend.setValue(createUser);
                    extend.setOrd(templates.size()+1);
                    templates.add(extend);
                    ///////////
                    extend=new CampaignResourceTemplateDTO();
                    extend.setCode("create_date");
                    extend.setName("Ngày gọi");
                    extend.setValue(createDate);
                    extend.setOrd(templates.size()+1);
                    templates.add(extend);
                    ///////////
                    ///////////
                    extend=new CampaignResourceTemplateDTO();
                    extend.setCode("action");
                    extend.setName("Play");
                    extend.setValue(callFile);
                    extend.setOrd(templates.size()+1);
                    templates.add(extend);
                    result.add(templates);
                }
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public ListExcelDynamicGroupDTO exportExcel(long campaignResourceId) {
        try {
            ListExcelDynamicGroupDTO excelGroup = new ListExcelDynamicGroupDTO();
            StringBuilder GET_ID = new StringBuilder();
            GET_ID.append("select d.id,u.first_name" +
                " from campaign_resource_detail d\n" +
                " , jhi_user u where d.assign_user_id = u.id and d.campaign_id=:campaignId ");

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


            List<ListExcelDynamicDTO> lsListExcelDynamicDTO = new ArrayList<ListExcelDynamicDTO>();

            ExcelDynamicDTO header = new ExcelDynamicDTO();
            List<Object[]> objects = query.getResultList();
            if (objects.size() > 0) {
                for (Object[] object : objects) {
                    String id = DataUtil.safeToString(object[0]);
                    String firstName = DataUtil.safeToString(object[1]);

                    List<ExcelDynamicDTO> lsData = new ArrayList<>();
                    Query queryDynamicColumn = entityManager.createNativeQuery(String.valueOf(GET_DYNAMIC_COLUMN_SQL));
                    queryDynamicColumn.setParameter("campaignId", 1);
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
                        queryQuestion.setParameter("campaignId", 1);
                        queryQuestion.setParameter("campaignResourceDetailId", DataUtil.safeToLong(id));
                        List<Object[]> objectsQuestion = queryQuestion.getResultList();
                        if (objectsQuestion.size() > 0) {
                            for (Object[] objectQuestion : objectsQuestion) {
                                String questionName = DataUtil.safeToString(objectQuestion[0]);
                                String answer = DataUtil.safeToString(objectQuestion[1]);
                                ExcelDynamicDTO question = new ExcelDynamicDTO();
                                idx = idx + 1;
                                question.setName(questionName);

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
