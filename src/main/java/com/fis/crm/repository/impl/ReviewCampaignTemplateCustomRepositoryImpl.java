package com.fis.crm.repository.impl;

import com.fis.crm.commons.DataUtil;
import com.fis.crm.commons.MaperUtils;
import com.fis.crm.config.Constants;
import com.fis.crm.domain.Campaign;
import com.fis.crm.domain.CampaignResourceTemplate;
import com.fis.crm.domain.CampaignTemplateDetail;
import com.fis.crm.repository.RecordCallResultCustomRepository;
import com.fis.crm.repository.ReviewCampaignTemplateCustomRepository;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.service.mapper.CampaignResourceTemplateMapper;
import com.fis.crm.service.mapper.CampaignTemplateDetailMapper;
import com.fis.crm.web.rest.errors.BusinessException;
import org.hibernate.Session;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ReviewCampaignTemplateCustomRepositoryImpl implements ReviewCampaignTemplateCustomRepository {
    private final EntityManager entityManager;
    private final UserService userService;
    private final CampaignTemplateDetailMapper campaignTemplateDetailMapper;

    private final String GET_CAMPAIGN_ID_SQL = "select id from CAMPAIGN_RESOURCE_DETAIL where ASSIGN_USER_ID=:userId and CALL_STATUS=1 and CAMPAIGN_ID=:campaignId and ROWNUM=1";
    private final String GET_DYNAMIC_COLUMN_SQL = "select * from campaign_template_detail t where t.campaign_id =(select CAMPAIGN_TEMPLATE_ID from campaign c where  c.id=:campaignId and rownum=1) order by ord asc";
    private final String GET_TEMP_OPTION_SQL = "select code,name from campaign_template_option where status=1 and CAMPAIGN_TEMPLATE_DETAIL_ID=:tempId order by name asc";

    private final String GET_QUESTIONS_SQL = "select cq.ID, cq.CONTENT, cs.START_CONTENT, cs.END_CONTENT\n" +
        "from CAMPAIGN_SCRIPT_QUESTION cq\n" +
        "         left join CAMPAIGN_SCRIPT cs on cq.CAMPAIGN_SCRIPT_ID = cs.ID\n" +
        "where cq.CAMPAIGN_SCRIPT_ID in (select s.CAMPAIGN_SCRIPT_ID from campaign s where s.id = :campaignId)\n" +
        "  and cq.status = 1\n" +
        "order by cq.position asc\n";
    private final String GET_ANSWERS_FOR_QUESTION_SQL = "select id,type,content,min_value,max_value,'' from campaign_script_anwser where CAMPAIGN_SCRIPT_QUESTION_ID=:questionId  and status=1 order by position asc";
    private final String ADD_OUTER_CALL_SQL = " {? = call pck_util.make_new_call (?,?,?,?,?)}";
    private final String GET_ALL_CAMPAIGN_FOR_TDV_SQL = "select * from campaign where id in (\n" +
        "select CAMPAIGN_ID from campaign_resource_detail where ASSIGN_USER_ID=:userId\n" +
        ") and status=1 and START_DATE<sysdate and (END_DATE is null or END_DATE>=trunc(sysdate))";
    private final String GET_ID_CAMPAIGN_RESOURCE_DETAIL_BY_CID = "select id from CAMPAIGN_RESOURCE_DETAIL where CID=:CID and rownum=1";
    private final String GET_CAMPAIGN_ID_CAMPAIGN_RESOURCE_DETAIL_BY_CID = "select campaign_id from CAMPAIGN_RESOURCE_DETAIL where CID=:CID and rownum=1";

    public ReviewCampaignTemplateCustomRepositoryImpl(EntityManager entityManager, UserService userService, CampaignTemplateDetailMapper campaignTemplateDetailMapper) {
        this.entityManager = entityManager;
        this.userService = userService;
        this.campaignTemplateDetailMapper = campaignTemplateDetailMapper;
    }

    @Override
    public List<CampaignResourceTemplateDTO> getTheNextPhoneNumber(Long campaignId) {
        try {
            Query queryDynamicColumn = entityManager.createNativeQuery(GET_DYNAMIC_COLUMN_SQL, CampaignTemplateDetail.class);
            Query queryTempOption = entityManager.createNativeQuery(GET_TEMP_OPTION_SQL);
            Long userId = userService.getUserIdLogin();
            queryDynamicColumn.setParameter("campaignId", campaignId);

            List<CampaignTemplateDetailDTO> lsTemplate = campaignTemplateDetailMapper.toDto(queryDynamicColumn.getResultList());
            List<CampaignResourceTemplateDTO> templates = convertToTemplate(lsTemplate);
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
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public List<CampaignResourceTemplateDTO> convertToTemplate(List<CampaignTemplateDetailDTO> lsTemplate)
    {
        List<CampaignResourceTemplateDTO> template=new ArrayList<CampaignResourceTemplateDTO>();
        for(CampaignTemplateDetailDTO o:lsTemplate)
        {
            CampaignResourceTemplateDTO d=new CampaignResourceTemplateDTO();
            d.setId(o.getId());
            d.setCode(o.getCode());
            d.setName(o.getName());
            d.setValue("");
            d.setCampaignId(-1L);
            d.setCampaignResourceDetailId(-1L);
            d.setCampaignResourceId(-1L);
            d.setEditable(o.getEditable());
            d.setOrd(o.getOrd());
            d.setType(o.getType());
            template.add(d);
        }
        return template;
    }

    @Override
    public List<QuestionsAndAnswersDTO> getQuestionsAndAnswers(Long campaignId, Long campaignResourceDetailId) {
        Query queryQuestion = entityManager.createNativeQuery(GET_QUESTIONS_SQL);
        Query queryAnswers = entityManager.createNativeQuery(GET_ANSWERS_FOR_QUESTION_SQL);
        queryQuestion.setParameter("campaignId", campaignId);
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
    public String saveResult(RecordCallResultDTO recordCallResultDTO, CampaignResourceTemplateDTO campaignResourceTemplateDTO) {
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
            strSQL = "{call pck_util.update_callout(?,?,?) }";
            callableStatement = connection.prepareCall(strSQL);
            callableStatement.setLong(1, campaignResourceTemplateDTO.getCampaignId());
            callableStatement.setLong(2, campaignResourceTemplateDTO.getCampaignResourceDetailId());
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();
            String strError = callableStatement.getString(3);
            if (strError != null && !"".equals(strError)) {
                return strError;
            }
            connection.commit();
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        } finally {
            closeCnn(pstmt);
            closeCnn(callableStatement);
            closeCnn(connection);
        }

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

        return null;
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

        return null;
    }
}
